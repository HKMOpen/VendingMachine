package com.hkm.staffvend;

import com.hkm.layout.Dialog.ErrorMessage;
import com.hkm.staffvend.event.BS;
import com.hkm.staffvend.usage.ImageDisMos;
import com.hkm.staffvend.usage.MainMenu;
import com.hkm.staffvend.usage.MainMenuItem;
import com.hkmvend.sdk.Constant;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.MenuEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class mainmenu extends content_base {

    public mainmenu() {
    }


    RestaurantPOS.DataConfigCB callback = new RestaurantPOS.DataConfigCB() {
        @Override
        public void success(final List<MenuEntry> list) {
            ErrorMessage.alert("successfully found items " + list.size(), getChildFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    startMainMenu(list);
                }
            });
        }

        @Override
        public void failure(String error_cause) {
            ErrorMessage.alert(error_cause, getChildFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    initGDATA();
                }
            });
        }
    };

    private void startMainMenu(List<MenuEntry> allentries) {
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
        mMosaicLayout.addPattern(pattern1);
        mMosaicLayout.addPattern(pattern2);
        mMosaicLayout.chooseRandomPattern(true);
        mMosaicLayout.setAdapter(mAdapater);
        mProgress.animate().alpha(0f);
        // List<MainMenuItem> it = new MainMenuItem(allentries);
    }

    private void continoueAfterSync(List<MenuEntry> list) {
        ImageDisMos mAdapater = new ImageDisMos(getActivity());
        mAdapater.setData(list);
        mMosaicLayout.addPattern(pattern1);
        mMosaicLayout.addPattern(pattern2);
        mMosaicLayout.chooseRandomPattern(true);
        mMosaicLayout.setAdapter(mAdapater);
        hideLoad();
    }

    @Override
    protected void initGDATA() {
        RestaurantPOS c = RestaurantPOS.getInstance(getActivity().getApplication());
        try {
            c.setCB(callback);
            c.setDatabaseId(Constant.SHEETSU_DOC_ID);
            c.runType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
