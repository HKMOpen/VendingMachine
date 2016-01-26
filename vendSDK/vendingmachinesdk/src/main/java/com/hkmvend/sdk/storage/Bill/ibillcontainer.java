package com.hkmvend.sdk.storage.Bill;

import java.util.List;

/**
 * Created by hesk on 26/1/16.
 */
public interface ibillcontainer {

    void consolidateEndDay();

    void flushEndDayData();

    int getBillCount();

    float getTotalRevenueSoFar();

    Bill getUnpaidBills();

    Bill getPaidBills();

    Bill newBill(int headcount, String table_id);

    Bill findBillById(int code);

    Bill findBillByTable(String table_id);

    List<Bill> findBillByTimeRange(long timebefore, long timeafter);

    boolean getTransactionComplete(long bill_id);


    Bill getCurrentEngagedTable();

}
