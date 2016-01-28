package com.hkm.staffvend.event;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.hkm.staffvend.content.staff;
import com.hkm.staffvend.content.submenu;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.squareup.otto.Bus;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hesk on 26/1/16.
 */
public class BS {

    private static final Bus BUS = new Bus();


    public static Bus getInstance() {
        return BUS;
    }

    private BS() {

    }

    public static void jump_main_2_sub(@MenuEntry.EntryTypes int typeId) {
        submenu sub = submenu.newInstanceCate(typeId);
        getInstance().post(sub);
    }

    public static void onResultFromPrevious(int requestCode, int resultCode, Intent data, Fragment current) {
        if (requestCode == ApplicationConstant.NEW_TABLE && resultCode == Activity.RESULT_OK) {
            if (current instanceof staff) {
                staff home = (staff) current;
                home.refreshEngagedTable();
            }
        }

    }
}
