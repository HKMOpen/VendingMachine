package com.hkm.staffvend.content;

import android.support.annotation.Nullable;
import android.view.View;

import com.hkm.staffvend.event.BS;
import com.hkm.staffvend.usage.MainMenu;
import com.hkm.staffvend.usage.MainMenuItem;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Menu.MenuEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class mainmenu extends content_base {

    public mainmenu() {
    }


    private void startMainMenu(@Nullable List<MenuEntry> allentries) {
        if (allentries == null) return;
        Iterator<MenuEntry> e = allentries.iterator();
        List<Integer> names = new ArrayList<>();
        List<MainMenuItem> main = new ArrayList<>();
        while (e.hasNext()) {
            MenuEntry entry = e.next();
            if (names.indexOf(entry.getCategory()) == -1) {
                names.add(entry.getCategory());
                MainMenuItem m = new MainMenuItem(MenuEntry.getDisplayCateName(entry.getCategory()));
                m.setCateId(entry.getCategory());
                main.add(m);
            }
        }

        MainMenu mAdapater = new MainMenu(getActivity());
        mAdapater.setCB(new MainMenu.clickButton() {
            @Override
            public void lclipko(int id) {
                BS.jump_main_2_sub(id);
            }
        });
        mAdapater.setData(main);
        mTopButton.setVisibility(View.GONE);
        mMosaicLayout.addPattern(pattern2);
        // mMosaicLayout.addPattern(pattern2);
        //  mMosaicLayout.chooseRandomPattern(true);
        mMosaicLayout.setAdapter(mAdapater);
        hideLoad();
    }

    @Override
    protected void initGDATA() {
        RestaurantPOS c = RestaurantPOS.getInstance(getActivity().getApplication());
        startMainMenu(c.getContainer().getAllRecords());
    }


}
