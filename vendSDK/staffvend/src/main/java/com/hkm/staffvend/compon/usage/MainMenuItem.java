package com.hkm.staffvend.compon.usage;

import com.hkmvend.sdk.storage.Menu.MenuEntry;

/**
 * Created by hesk on 26/1/16.
 */
public class MainMenuItem {
    public String name;
    @MenuEntry.EntryTypes
    public int cate_id;

    public MainMenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCateId(@MenuEntry.EntryTypes int id) {
        cate_id = id;
    }

    @MenuEntry.EntryTypes
    public int getCateId() {
        return cate_id;
    }
}
