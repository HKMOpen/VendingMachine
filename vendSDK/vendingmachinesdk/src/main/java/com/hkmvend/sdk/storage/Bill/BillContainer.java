package com.hkmvend.sdk.storage.Bill;

import android.app.Application;
import android.content.Context;

import com.hkmvend.sdk.storage.ApplicationBase;
import com.hkmvend.sdk.storage.RealmPolicy;

import java.util.List;

import io.realm.RealmConfiguration;

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

    }

    @Override
    public int getBillCount() {
        return 0;
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
    public boolean newBill(int headcount, String table_id) {
        return false;
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
