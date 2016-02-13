package com.hkm.staffvend.event;

import com.hkmvend.sdk.storage.Bill.BillContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 28/1/16.
 */
public class ApplicationConstant {
    public static final String DATE_TIME = "dd MMM yyyy HH:mm:ss z";

    public static final String INTENT_TABLE_FUNCTION = "ifunction";
    public static final String INTENT_BILL_ID = "transaction_id";
    public static final String INTENT_TABLE_FILTER = "extrefilter";

    //new intent code
    public static final int
            NEW_TABLE = 102,
            NEW_STAFF = 103,
            NEW_SWITCH_STAFF = 104,
            VIEW_PAID_TABLES = 105,
            VIEW_UNPAID_TABLES = 106,
            IMPORT_RESTUARANT_MENU = 107,
            MAKE_PAYMENT = 108,
            NEW_ORDER = 109;

    //View table function code
    public static final int
            FUNC_VIEW_ONLY = 0,
            FUNC_EDIT = 1,
            RESULT_NEW_ORDER = 300,
            RESULT_PAYMENT_DONE = 301,
            FUNC_SELECT_TABLE = 2,
            FUNC_CLOSE_BILL = 3;

    public static final int
            BS_REFRESH_LIST = 11,
            BS_SET_CURRENT = 9,
            BS_SET_REMOVE = 10;


    public static String[] getTables() {
        return generateDefaultTables();
    }

    private static String[] generateDefaultTables() {
        return new String[]{
                "T-01",
                "T-02",
                "T-03",
                "T-04",
                "T-05",
                "T-06",
                "T-07",
                "T-08",
                "T-09",
                "T-10",
                "T-11",
                "T-12",
                "T-13",
                "T-14",
                "T-15",
                "T-16"
        };
    }

    public static boolean inPreselectionScope(int h, int[] scope) {
        for (int i = 0; i < scope.length; i++) {
            int qu = scope[i];
            if (h == qu) return true;
        }
        return false;
    }

    public static int[] generatePreselection(BillContainer bc) {
        String[] l = ApplicationConstant.getTables();
        List<Integer> h = new ArrayList<>();
        for (int i = 0; i < l.length; i++) {
            String qu = l[i];
            if (bc.findBillByTableUnpaid(qu) != null) {
                h.add(i);
            }
        }
        int[] y = new int[h.size()];
        for (int i = 0; i < h.size(); i++) {
            y[i] = h.get(i);
        }
        return y;
    }
}
