package com.hkmvend.sdk.storage.Bill;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.hkmvend.sdk.storage.ApplicationBase;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.hkmvend.sdk.storage.RealmPolicy;

import java.sql.Timestamp;
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
            CTABLELONGID = "focusbillid";

    private final RealmConfiguration conf;
    private Bill engaged;
    private static BillContainer instance;
    private Context context;
    private AtomicInteger atomicInteger = new AtomicInteger(100000);


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
        engaged = bill;
    }

    protected void init() {
        current_engage_table_id = loadRef(CTABLEID);
        current_bill_id = loadRefL(CTABLELONGID);
        lastest_bill_id = loadRefL(CT_LAST_BILL_SERIAL_NUMBER, -1);
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
        return getQuery().findAll();
    }

    @Override
    public List<Bill> getPaidBills() {
        return getQuery().findAll();
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
    public Bill newBill(int headcount, String table_id, @Nullable String remark) {
        Realm realm = Realm.getInstance(conf);
        realm.beginTransaction();
        Bill target = realm.createObject(Bill.class);
        target.setHeadcount(headcount);
        target.setBill_number_code(atomicInteger.longValue());
        target.setTable_id(table_id);
        if (remark != null)
            target.setTable_remark(remark);
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        // begintransaction.setDate(now.toString());
        target.setStart_time(now.toString());
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
        return getQuery().findFirst();
    }

    @Override
    public Bill findBillByHeadCount(int count) {
        return getQuery().findFirst();
    }


    @Override
    public Bill findBillByTable(String table_id) {
        Bill copies = getQuery().equalTo("table_id", table_id).findFirst();
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
        atomicInteger = new AtomicInteger(id);
        lastest_bill_id = id;
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
}
