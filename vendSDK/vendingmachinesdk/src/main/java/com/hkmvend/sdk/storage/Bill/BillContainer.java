package com.hkmvend.sdk.storage.Bill;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hkmvend.sdk.Constant;
import com.hkmvend.sdk.storage.ApplicationBase;
import com.hkmvend.sdk.storage.Menu.EntryContainer;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.hkmvend.sdk.storage.RealmPolicy;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmError;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by hesk on 26/1/16.
 */
public class BillContainer extends ApplicationBase implements ibillcontainer {

    private static String current_engage_table_id;
    private static long current_bill_id, lastest_bill_id;
    public static final String
            CT_LAST_BILL_SERIAL_NUMBER = "lastnumx",
            CTABLEID = "currentengagedtableid",
            CTABLELONGID = "focusbillid",
            INTENT_TABLE_FILTER = "extrefilter";
    private final RealmConfiguration conf;
    private Bill engaged;
    private static BillContainer instance;
    private Context context;
    private Application application_context;
    // private AtomicInteger atomicInteger;


    public static BillContainer getInstnce(Application c) {
        if (instance == null) {
            instance = new BillContainer(c);
        }
        return instance;
    }

    public BillContainer(Application cc) {
        super(cc);
        context = cc;
        application_context = cc;
        conf = RealmPolicy.realmCfg(cc);
        init();
    }

    public boolean hasTableFocused() {
        if (current_engage_table_id.equalsIgnoreCase(EMPTY_FIELD)) return false;
        return true;
    }

    public void setFocusOnBill(Bill bill) {
        current_engage_table_id = bill.getTable_id();
        current_bill_id = bill.getBill_number_code();
        saveInfo(CTABLEID, current_engage_table_id);
        saveInfo(CTABLELONGID, current_bill_id);
        engaged = bill;
    }

    protected void init() {
        current_engage_table_id = loadRef(CTABLEID);
        current_bill_id = loadRefL(CTABLELONGID);
        lastest_bill_id = loadRefL(CT_LAST_BILL_SERIAL_NUMBER, -10);
        // atomicInteger = new AtomicInteger((int) lastest_bill_id);
        if (hasTableFocused()) {
            engaged = findBillByTable(current_engage_table_id);
        }
    }

    public boolean isDefaultLastestBillNumberDefined() {
        return lastest_bill_id > -1;
    }

    public long getLastestBillNumber() {
        return lastest_bill_id;
    }

    public void destroy() {
        saveInfo(CT_LAST_BILL_SERIAL_NUMBER, lastest_bill_id);
        instance = null;
    }

    private RealmQuery<Bill> getQuery() {
        Realm realm = Realm.getInstance(conf);
        RealmQuery<Bill> query = realm.where(Bill.class);
        return query;
    }

    public void removeTableFocus() {
        saveInfo(CTABLEID, "");
        saveInfo(CTABLELONGID, 0);
    }

    @Override
    protected void removeAllData() {

    }

    @Override
    public void consolidateEndDay() {

    }

    public void removeBill(Bill item) {
        Realm realm = Realm.getInstance(conf);
        realm.beginTransaction();
        item.removeFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void flushEndDayData() {
        Realm realm = Realm.getInstance(conf);
        RealmResults<Bill> copies = realm.where(Bill.class).findAll();
        realm.beginTransaction();
        copies.clear();
        realm.commitTransaction();
    }

    @Override
    public int getBillCount() {
        try {
            return getQuery().findAll().size();
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

    @Override
    public float getTotalRevenueSoFar() {
        return 0;
    }

    @Override
    public List<Bill> getUnpaidBills() {
        return getQuery().equalTo(Bill.Field_payment_made, false).findAll();
    }

    @Override
    public List<Bill> getPaidBills() {
        return getQuery().equalTo(Bill.Field_payment_made, true).findAll();
    }

    @Override
    public List<Bill> getAll() {
        if (getBillCount() == 0) {
            return new ArrayList<Bill>();
        } else {
            return getQuery().findAll();
        }
    }

    @Override
    public List<Bill> getByBundle(@Nullable Bundle extras) {
        if (extras != null) {
            String[] config = extras.getStringArray(INTENT_TABLE_FILTER);
            if (config[1].equalsIgnoreCase("paid")) {
                return getPaidBills();
            } else if (config[1].equalsIgnoreCase("unpaid")) {
                return getUnpaidBills();
            } else {
                return getAll();
            }
        } else {
            return getAll();
        }
    }

    @Override
    public Bill newBill(int headcount, String table_id, @Nullable String remark) {
        Realm realm = Realm.getInstance(conf);
        realm.beginTransaction();
        Bill target = realm.createObject(Bill.class);
        target.setHeadcount(headcount);
        lastest_bill_id = lastest_bill_id + 1L;
        target.setBill_number_code(lastest_bill_id);
        saveInfo(CT_LAST_BILL_SERIAL_NUMBER, lastest_bill_id);
        target.setTable_id(table_id);
        target.setPayment_collected(false);
        if (remark != null)
            target.setTable_remark(remark);

        Date t = new Date();
        Timestamp timestamp_now = new Timestamp(t.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_v3);
        String currentTimeStamp = dateFormat.format(timestamp_now); // Find todays date
        target.setStart_time(currentTimeStamp);

        realm.commitTransaction();
        setFocusOnBill(target);
        return target;
    }


    /**
     * by the bill ID
     *
     * @param code the code number
     * @return the bill object
     */
    @Override
    public Bill findBillById(long code) {
        return getQuery().equalTo(Bill.Field_bill_number_code_long, code).findFirst();
    }

    @Override
    public Bill findBillByHeadCount(int count) {
        return getQuery().findFirst();
    }


    @Override
    public Bill findBillByTable(String table_id) {
        Bill copies = getQuery().equalTo(Bill.Field_table_id, table_id).findFirst();
        return copies;
    }

    public Bill findBillByTableUnpaid(String table_id) {
        Bill copies = getQuery()

                .equalTo(Bill.Field_table_id, table_id)
                .equalTo(Bill.Field_payment_made, false)

                .findFirst();
        if (copies != null)
            Log.d("rQ", copies.toString());

        return copies;
    }


    @Override
    public List<Bill> findBillByTimeRange(long timebefore, long timeafter) {
        return getQuery().findAll();
    }

    @Override
    public boolean getTransactionComplete(long bill_id) {
        return false;
    }

    @Override
    public Bill getCurrentEngagedTable() {
        return engaged;
    }

    public void setMaunalLastestBillNumber(int id) {
        //   atomicInteger = new AtomicInteger(id);
        lastest_bill_id = (long) id;
        saveInfo(CT_LAST_BILL_SERIAL_NUMBER, lastest_bill_id);
    }

    public static float getProjectedTotal(final Bill bill) {
        Iterator<MenuEntry> it = bill.getOrders().iterator();
        float total = 0f;
        while (it.hasNext()) {
            MenuEntry entry = it.next();
            total += entry.getPrice();
        }
        return total;
    }

    public float getConsolidatedTotal() {
        List<Bill> items = getPaidBills();
        Iterator<Bill> it = items.iterator();
        float sum = 0f;
        while (it.hasNext()) {
            Bill b = it.next();
            sum += b.getConsolidated_payment();
        }
        //  getQuery().equalTo(Bill.Field_payment_made, true).findAll();
        return sum;
    }

    public boolean makeNewOrderEntry(int entry_id) {
        try {
            Realm realm = Realm.getInstance(conf);
            EntryContainer instance = EntryContainer.getInstnce(application_context);
            MenuEntry it = instance.getFirstItemById(entry_id);
            BillContainer.addNewDish(it, realm, getCurrentEngagedTable());
            return true;
        } catch (Exception allkind) {
            Log.d("TAGnow", allkind.getMessage());
            return false;
        }
    }

    public static void addNewDish(MenuEntry item, Realm realm, Bill target) {
        realm.beginTransaction();
        target.getOrders().add(item);
        realm.commitTransaction();
    }

    public static List<String> getOrderedItemsChinese(Bill target) {
        Iterator<MenuEntry> it = target.getOrders().iterator();
        List<String> hh = new ArrayList<>();
        while (it.hasNext()) {
            MenuEntry en = it.next();
            hh.add(en.getEntry_name_chinese());
        }
        return hh;
    }


    public static List<String> getOrderedItemsEnglish(Bill target) {
        Iterator<MenuEntry> it = target.getOrders().iterator();
        List<String> hh = new ArrayList<>();
        while (it.hasNext()) {
            MenuEntry en = it.next();
            hh.add(en.getEntry_name_english());
        }
        return hh;
    }


    public void updateHeadCount(Bill target, int people) {
        Realm realm = Realm.getInstance(conf);
        realm.beginTransaction();
        target.setHeadcount(people);
        realm.commitTransaction();
    }

    public void settlePayment(Bill target, float collected_amount) {
        Realm realm = Realm.getInstance(conf);
        realm.beginTransaction();
        target.setPayment_collected(true);
        Date t = new Date();
        Timestamp timestamp_now = new Timestamp(t.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_v3);
        String currentTimeStamp = dateFormat.format(timestamp_now); // Find todays date
        target.setPay_time(currentTimeStamp);
        target.setConsolidated_payment(collected_amount);
        realm.commitTransaction();
    }
}
