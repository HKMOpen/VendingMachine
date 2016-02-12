package com.hkm.staffvend.compon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.staffvend.R;

import java.util.List;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;

/**
 * Created by zJJ on 2/8/2016.
 */
public class ItemBeforePaid extends RecyclerView.ViewHolder {

    TextView mSubtitle, mTitle, mTime;
    ImageButton mDismissIcon, mSetCurrent, mNewOrder, mPayment;
    TagContainerLayout mMenuContainer;
    RelativeLayout mArea;
    TableAdapter mAdapter;

    public ItemBeforePaid(View view) {
        super(view);
    }


    public ItemBeforePaid(View view, TableAdapter adapter) {
        super(view);
        mAdapter = adapter;
        mTitle = (TextView) view.findViewById(R.id.item_ul_bill_no);
        mSubtitle = (TextView) view.findViewById(R.id.item_ul_bottom_display);
        mTime = (TextView) view.findViewById(R.id.item_ul_time_stamp);
        mMenuContainer = (TagContainerLayout) view.findViewById(R.id.item_ul_tag_container);
        mArea = (RelativeLayout) view.findViewById(R.id.item_ul_table);
        mNewOrder = (ImageButton) view.findViewById(R.id.item_ul_make_new_order);
        mPayment = (ImageButton) view.findViewById(R.id.item_ul_collection);
    }

    public void newOrderFn(final Runnable run) {
        mNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run.run();
            }
        });
    }

    public void setTagClick(TagView.OnTagClickListener lis) {
        if (mMenuContainer == null) return;
        mMenuContainer.setOnTagClickListener(lis);
    }

    public void displayEntries(List<String> list) {
        if (mMenuContainer == null) return;
        mMenuContainer.removeAllTags();
        mMenuContainer.setThemeOnActive(R.style.tagactive);
        mMenuContainer.setTheme(R.style.tagnormal);
        mMenuContainer.setMode(LayouMode.SINGLE_CHOICE);
        mMenuContainer.setTags(list);
    }

}