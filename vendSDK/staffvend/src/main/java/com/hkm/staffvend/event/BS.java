package com.hkm.staffvend.event;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.hkm.staffvend.ui.MainOffice;
import com.hkm.staffvend.R;
import com.hkm.staffvend.content.mainmenu;
import com.hkm.staffvend.content.settings;
import com.hkm.staffvend.content.staffmenu;
import com.hkm.staffvend.content.submenu;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;

import static com.hkm.staffvend.event.ApplicationConstant.BS_REFRESH_LIST;
import static com.hkm.staffvend.event.ApplicationConstant.BS_SET_CURRENT;
import static com.hkm.staffvend.event.ApplicationConstant.IMPORT_RESTUARANT_MENU;
import static com.hkm.staffvend.event.ApplicationConstant.MAKE_PAYMENT;
import static com.hkm.staffvend.event.ApplicationConstant.NEW_TABLE;
import static com.hkm.staffvend.event.ApplicationConstant.RESULT_NEW_ORDER;
import static com.hkm.staffvend.event.ApplicationConstant.RESULT_PAYMENT_DONE;
import static com.hkm.staffvend.event.ApplicationConstant.VIEW_PAID_TABLES;
import static com.hkm.staffvend.event.ApplicationConstant.VIEW_UNPAID_TABLES;

/**
 * Created by hesk on 26/1/16.
 */
public class BS {

    private static final Bus BUS = new Bus();

    public static class BillFnc {
        public int function_bs;
        public List<Bill> list = new ArrayList<>();
        public Bill embed;

        public BillFnc(int func, Bill point) {
            function_bs = func;
            embed = point;
        }

        public BillFnc(int fctn) {
            function_bs = fctn;
        }

    }

    public static class EntryFnc {
        final public int entry;
        final public MenuEntry item;

        public EntryFnc(int entryID, MenuEntry entryItem) {
            entry = entryID;
            item = entryItem;
        }

    }


    public static Bus getInstance() {
        return BUS;
    }

    private BS() {

    }

    private static void setCurrentBillEngage(Bill mBill) {
        BillFnc fn = new BS.BillFnc(BS_SET_CURRENT, mBill);
        getInstance().post(fn);
    }

    private static void actionRefreshList() {
        BillFnc fn = new BS.BillFnc(BS_REFRESH_LIST);
        getInstance().post(fn);
    }

    public static void start_new_order_menu(Bill bill_ticket) {

    }

    public static void jump_sub_2_main() {
        mainmenu sub = new mainmenu();
        getInstance().post(sub);
    }

    public static void toSettings() {
        settings sub = new settings();
        getInstance().post(sub);
    }

    public static void jump_main_2_sub(@MenuEntry.EntryTypes int typeId) {
        submenu sub = submenu.newInstanceCate(typeId);
        getInstance().post(sub);
    }

    public static void select_menu_item_from_submenu(MenuEntry entry) {
        EntryFnc sub = new BS.EntryFnc(entry.getEntry_id(), entry);
        getInstance().post(sub);
    }

    public static void onResultFromPrevious(int requestCode, int resultCode, Intent data, @Nullable Fragment current, @Nullable MainOffice direct) {
        if (requestCode == MAKE_PAYMENT && resultCode == RESULT_PAYMENT_DONE) {

        }
        if (current == null || direct == null) return;
        if (requestCode == NEW_TABLE && resultCode == RESULT_NEW_ORDER) {
            if (current instanceof staffmenu) {
                staffmenu home = (staffmenu) current;
                home.refreshEngagedTable();
                direct.backHome();
            }
        }
        if (requestCode == VIEW_UNPAID_TABLES) {
            if (resultCode == RESULT_NEW_ORDER) {
                if (current instanceof staffmenu) {
                    staffmenu home = (staffmenu) current;
                    home.refreshEngagedTable();
                    direct.backHome();
                }
            }
            if (resultCode == Activity.RESULT_OK) {
                if (current instanceof staffmenu) {
                    staffmenu home = (staffmenu) current;
                    home.refreshEngagedTable();
                }
            }
        }

        if (requestCode == VIEW_PAID_TABLES && resultCode == Activity.RESULT_OK) {
            if (current instanceof staffmenu) {
                staffmenu home = (staffmenu) current;
                home.refreshEngagedTable();
            }
        }
        if (requestCode == IMPORT_RESTUARANT_MENU && resultCode == Activity.RESULT_OK) {
            //   if (current instanceof staffmenu) {
            //     staffmenu home = (staffmenu) current;
            //     home.refreshEngagedTable();
            //  }
            // String scanned_text = data.getExtras().getString(SecScanTbe.FIELD);
            String scanned_text = "";
            Snackbar
                    .make(current.getView(), scanned_text, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

    }

    public static void start_scanning(Activity here) {

        ZxingOrient integrator = new ZxingOrient(here);
        integrator.setIcon(R.drawable.ic_crop_free_24dp)   // Sets the custom icon
                .setToolbarColor("#AAc51162")       // Sets Tool bar Color
                .setInfoBoxColor("#AAc51162")       // Sets Info box color
                .setInfo("Scan a QR code Image.")   // Sets info message in the info box
                .initiateScan(Barcode.QR_CODE);


    }
}
