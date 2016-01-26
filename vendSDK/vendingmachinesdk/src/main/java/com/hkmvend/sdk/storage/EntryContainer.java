package com.hkmvend.sdk.storage;

import android.app.Application;
import android.content.Context;
import android.support.annotation.IntDef;
import android.view.Menu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmError;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by hesk on 10/12/15.
 */
public class EntryContainer extends ApplicationBase {
    private static final int LIMIT = 10;
    private int totalpages = 1;
    private int current_page = 1;
    private int skipped_upstream_items = 0;
    private String error_message;
    private syncResult message_channel;
    private ArrayList<Long> remove_list = new ArrayList<>();
    private List<MenuEntry> local_list = new ArrayList<MenuEntry>();
    private Context context;
    private int worker_status;
    private final RealmConfiguration conf;
    private static EntryContainer instance;
    private static String SAVE_TAG_RESTAURANT_MENU_ID = "resIdTagGoogleFile";
    public static final int
            STATUS_IDEAL = 1,
            STATUS_DOWN_STREAM = 2,
            STATUS_UP_STREAM = 3;

    @Override
    protected void removeAllData() {

    }

    @IntDef({STATUS_IDEAL, STATUS_DOWN_STREAM, STATUS_UP_STREAM})
    public @interface SyncStatus {

    }

    public static EntryContainer getInstnce(Application c) {
        if (instance == null) {
            instance = new EntryContainer(c);
        }

        return instance;
    }

    public EntryContainer(Application cc) {
        super(cc);
        init();
        context = cc;
        worker_status = STATUS_IDEAL;
        conf = RealmPolicy.realmCfg(cc);

    }

    @SyncStatus
    public int getStatus() {
        return worker_status;
    }


    public void flushRecords() {
        Realm realm = Realm.getInstance(conf);
        RealmResults<MenuEntry> copies = realm.where(MenuEntry.class).findAll();
        realm.beginTransaction();
        copies.clear();
        realm.commitTransaction();
    }

    public void removeItem(long product_id) {
        Realm realm = Realm.getInstance(conf);
        RealmResults<MenuEntry> copies = realm.where(MenuEntry.class).equalTo("id", product_id).findAll();
        realm.beginTransaction();
        copies.clear();
        realm.commitTransaction();
    }

    public boolean isConfigurationStored() {
        return !loadRef(APPLICATION_CONFIG_FILE).equalsIgnoreCase(EMPTY_FIELD);
    }

    public void saveConfigFile(String plain) {
        saveInfo(APPLICATION_CONFIG_FILE, plain);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        saveInfo(CONFIG_FILE_SAVE_TIME, timestamp.toString());
    }

    public String loadConfigFile() {
        return loadRef(APPLICATION_CONFIG_FILE);
    }

    public void saveRestaurantMenuId(String menuId) {
        saveInfo(SAVE_TAG_RESTAURANT_MENU_ID, menuId);
    }

    public boolean isMenuExisted() {
        return !loadRef(SAVE_TAG_RESTAURANT_MENU_ID).equalsIgnoreCase(EMPTY_FIELD);
    }

    public String loadRestaurantMenuId() {
        return loadRef(SAVE_TAG_RESTAURANT_MENU_ID);
    }

    public List<MenuEntry> getAllRecords() {
        Realm realm = Realm.getInstance(conf);
        RealmResults<MenuEntry> copies = realm.where(MenuEntry.class).findAll();
        copies.sort("date", Sort.DESCENDING);

        return convertToEntryOut(copies);
    }


    public List<MenuEntry> getFromCateId(int cateId) {
        Realm realm = Realm.getInstance(conf);

        RealmQuery<MenuEntry> query = realm.where(MenuEntry.class);

        RealmResults<MenuEntry> copies = query.equalTo("category", cateId).findAll();

        copies.sort("entry_id", Sort.ASCENDING);

        return convertToEntryOut(copies);
    }


    public boolean check_duplicated(Realm realm, int entry_id) {
        RealmQuery<MenuEntry> query = realm.where(MenuEntry.class);
        return query.equalTo("entry_id", entry_id).findFirst() != null;
    }


    private void errorTrigger() {
        if (message_channel != null) message_channel.failure(error_message);
    }

    public final int getItemsCount() {
        try {
            Realm realm = Realm.getInstance(conf);
            return realm.where(MenuEntry.class).findAll().size();
        } catch (RealmMigrationNeededException e) {
            e.fillInStackTrace();
            return 0;
        } catch (RealmError e) {
            e.fillInStackTrace();
            return 0;
        } catch (Exception e) {
            e.fillInStackTrace();
            return 0;
        }
    }

    private List<MenuEntry> convertToEntryOut(RealmResults<MenuEntry> copies) {

        Iterator<MenuEntry> is = copies.iterator();
        List<MenuEntry> list = new ArrayList<>();
        while (is.hasNext()) {
            MenuEntry cap = is.next();
            list.add(cap);
        }
        return list;
    }

    /**
     * the callback interface for the wishlist sync
     */
    public interface syncResult {
        void showListAll(final List<MenuEntry> wistlist);

        void failure(String error_message_out);
    }


    private static MenuEntry addToEntryContainer(
            MenuEntry target,
            @MenuEntry.EntryTypes int type,
            int entryId,
            String nameCN,
            String nameEng,
            float price
    ) {

        target.setCategory(type);
        target.setEntry_id(entryId);
        target.setEntry_name_chinese(nameCN);
        target.setEntry_name_english(nameEng);
        target.setPrice(price);
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        // begintransaction.setDate(now.toString());


        return target;
    }


    public boolean addNewRecord(
            @MenuEntry.EntryTypes int type,
            int entryId,
            String nameCN,
            String nameEng,
            float price

    ) {
        Realm realm = Realm.getInstance(conf);
        if (check_duplicated(realm, entryId)) return false;
        realm.beginTransaction();
        addToEntryContainer(realm.createObject(MenuEntry.class),
                type,
                entryId,
                nameCN,
                nameEng,
                price);
        realm.commitTransaction();
        return true;
    }


}
