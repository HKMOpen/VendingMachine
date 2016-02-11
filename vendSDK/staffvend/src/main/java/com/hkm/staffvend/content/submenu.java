package com.hkm.staffvend.content;

import android.os.Bundle;
import android.view.View;

import com.hkm.staffvend.compon.usage.DisplayB2;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Menu.EntryContainer;
import com.hkmvend.sdk.storage.Menu.MenuEntry;

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
        DisplayB2 mAdapater = new DisplayB2(getActivity());
        mAdapater.setData(list);
        mMosaicLayout.addPattern(pattern1);
        mMosaicLayout.addPattern(pattern2);
        mMosaicLayout.chooseRandomPattern(true);
        mMosaicLayout.setAdapter(mAdapater);
        mTopButton.setVisibility(View.VISIBLE);
        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  BS.jump_sub_2_main();
                getActivity().onBackPressed();
            }
        });
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
