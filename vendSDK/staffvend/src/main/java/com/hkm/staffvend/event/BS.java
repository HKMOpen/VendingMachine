package com.hkm.staffvend.event;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.hkm.staffvend.content.staff;
import com.hkm.staffvend.content.submenu;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import static com.hkm.staffvend.event.ApplicationConstant.*;

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

    public static void setCurrentBillEngage(Bill mBill) {
        BillFnc fn = new BS.BillFnc(BS_SET_CURRENT, mBill);
        getInstance().post(fn);
    }

    public static void jump_main_2_sub(@MenuEntry.EntryTypes int typeId) {
        submenu sub = submenu.newInstanceCate(typeId);
        getInstance().post(sub);
    }

    public static void select_menu_item_from_submenu(MenuEntry entry) {
        EntryFnc sub = new BS.EntryFnc(entry.getEntry_id(), entry);
        getInstance().post(sub);
    }

    public static void onResultFromPrevious(int requestCode, int resultCode, Intent data, Fragment current) {
        if (requestCode == NEW_TABLE && resultCode == Activity.RESULT_OK) {
            if (current instanceof staff) {
                staff home = (staff) current;
                home.refreshEngagedTable();
            }
        }

        if (requestCode == VIEW_UNPAID_TABLES && resultCode == Activity.RESULT_OK) {
            if (current instanceof staff) {
                staff home = (staff) current;
                home.refreshEngagedTable();
            }
        }

        if (requestCode == VIEW_PAID_TABLES && resultCode == Activity.RESULT_OK) {
            if (current instanceof staff) {
                staff home = (staff) current;
                home.refreshEngagedTable();
            }
        }


    }
}
