package com.hkm.staffvend.event;

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
            MAKE_PAYMENT = 108;

    //View table function code
    public static final int
            FUNC_VIEW_ONLY = 0,
            FUNC_EDIT = 1,
            FUNC_SELECT_TABLE = 2,
            FUNC_CLOSE_BILL = 3;

    public static final int
            BS_SET_CURRENT = 9,
            BS_SET_REMOVE = 10;
}
