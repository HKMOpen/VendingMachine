package com.hkmvend.sdk.storage.Bill;

import android.app.Application;
import android.content.Context;

import com.hkmvend.sdk.storage.ApplicationBase;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.hkmvend.sdk.storage.RealmPolicy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmError;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by hesk on 26/1/16.
 */
public class BillContainer extends ApplicationBase implements ibillcontainer {

    private static String current_engage_table_id;
    private static long current_bill_id;
    public static final String
            CTABLEID = "currentengagedtableid",
            CTABLELONGID = "focusbillid";

    private final RealmConfiguration conf;

    private static BillContainer instance;
    private Context context;

    public static BillContainer getInstnce(Application c) {
        if (instance == null) {
            instance = new BillContainer(c);
        }
        return instance;
    }

    public BillContainer(Application cc) {
        super(cc);
        context = cc;
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
    }

    protected void init() {
        current_engage_table_id = loadRef(CTABLEID);
        current_bill_id = loadRefL(CTABLELONGID);
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
            Realm realm = Realm.getInstance(conf);
            return realm.where(Bill.class).findAll().size();
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
    public Bill getUnpaidBills() {
        return null;
    }

    @Override
    public Bill getPaidBills() {
        return null;
    }

    @Override
    public Bill newBill(int headcount, String table_id) {
        Realm realm = Realm.getInstance(conf);
        Bill target = realm.createObject(Bill.class);
        realm.beginTransaction();
        target.setHeadcount(headcount);
        target.setTable_id(table_id);
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        // begintransaction.setDate(now.toString());
        target.setStart_time(now.toString());
        realm.commitTransaction();
        setFocusOnBill(target);
        return target;
    }

    @Override
    public Bill findBillById(int code) {
        return null;
    }

    @Override
    public Bill findBillByTable(String table_id) {
        return null;
    }

    @Override
    public List<Bill> findBillByTimeRange(long timebefore, long timeafter) {
        return null;
    }

    @Override
    public boolean getTransactionComplete(long bill_id) {
        return false;
    }

    @Override
    public Bill getCurrentEngagedTable() {
        return null;
    }
}
