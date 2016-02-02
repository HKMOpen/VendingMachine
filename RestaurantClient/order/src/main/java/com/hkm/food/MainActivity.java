package com.hkm.food;


import com.hkm.longmenu.Bind;
import com.hkm.longmenu.menuitem;


public class MainActivity extends setup {
    @Override
    protected void detailMenu(Bind menu_bind) {
        menu_bind.setAddListMenu(new menuitem(R.drawable.home128, "Home"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.helpquestionmark128, "Help"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.greenstar, "Paper"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.settings128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.gift128, "Gift"));
    }

    protected int getLayout() {
        return R.layout.profile;
    }
}
