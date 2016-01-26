package com.hkm.staffvend;

import android.app.Fragment;
import android.os.Bundle;

import com.hkm.staffvend.usage.ImageDisMos;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.EntryContainer;
import com.hkmvend.sdk.storage.MenuEntry;

import java.util.List;

/**
 * Created by hesk on 26/1/16.
 */
public class submenu extends content_base {
    @Override
    protected void initGDATA() {
        @MenuEntry.EntryTypes int cateid = MenuEntry.orderOf(getArguments().getInt(cate_id_name, 0));
        RestaurantPOS c = RestaurantPOS.getInstance(getActivity().getApplication());
        EntryContainer entry = c.getContainer();
        continoueAfterSync(entry.getFromCateId(cateid));
        hideLoad();
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

    public final static String cate_id_name = "CATE";

    public static submenu newInstanceCate(int cate_id) {
        submenu sub = new submenu();
        Bundle b = new Bundle();
        b.putInt(cate_id_name, cate_id);
        sub.setArguments(b);
        return sub;
    }
}
