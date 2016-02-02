package com.hkm.gogosushi.main;


import com.hkm.gogosushi.R;
import com.hkm.gogosushi.product.entriespage;
import com.hkm.gogosushi.scan.scanpage;
import com.hkm.longmenu.Bind;
import com.hkm.longmenu.menuitem;

public class MainActivity extends setup {
    @Override
    protected void detailMenu(Bind menu_bind) {
        menu_bind.setAddListMenu(new menuitem(R.drawable.home128, "Home"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.helpquestionmark128, "Help"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.greenstar, "Paper", entriespage.class));
        menu_bind.setAddListMenu(new menuitem(R.drawable.settings128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.gift128, "Gift"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.roundbubbleheart128, "My Wallet"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.wifi128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.user128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.diamond28, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.controllerconsole128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.paperplane128, "Paper"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.settings128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.gift128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.roundbubbleheart128, "Settings"));
        menu_bind.setAddListMenu(new menuitem(R.drawable.wifi128, "Scan", scanpage.class));
    }

    protected int getLayout() {
        return R.layout.act_main;
    }
}
