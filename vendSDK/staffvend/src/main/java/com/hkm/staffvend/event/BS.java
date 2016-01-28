package com.hkm.staffvend.event;

import com.hkm.staffvend.content.submenu;
import com.hkmvend.sdk.storage.Menu.MenuEntry;
import com.squareup.otto.Bus;

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


}
