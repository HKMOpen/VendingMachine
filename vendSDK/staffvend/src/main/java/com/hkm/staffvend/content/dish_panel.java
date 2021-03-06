package com.hkm.staffvend.content;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.staffvend.ui.MainOffice;
import com.hkm.staffvend.R;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.BillContainer;
import com.hkmvend.sdk.storage.Menu.EntryContainer;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;


/**
 * Created by hesk on 29/1/16.
 */
public class dish_panel extends content_base implements View.OnClickListener {
    public final static String dish_id = "dish_id";
    public final static String dish_name = "dish_name";

    public static dish_panel newInstance(int dish_d_id, String dish_d_name) {
        dish_panel sub = new dish_panel();
        Bundle b = new Bundle();
        b.putInt(dish_id, dish_d_id);
        b.putString(dish_name, dish_d_name);
        sub.setArguments(b);
        return sub;
    }

    Button bOrder, bCancel, bSearchmore;
    TextView display;
    BillContainer instanceBillContainer;
    EntryContainer instanceMenuEntry;
    ImageView single_imagle;
    String entry_name;
    TagContainerLayout counts;
    int entry_id;
    int dish_count;

    @Override
    protected void initGDATA() {
        RestaurantPOS instance = RestaurantPOS.getInstance(getActivity().getApplication());
        instanceBillContainer = instance.getBillContainer();
        instanceMenuEntry = instance.getContainer();
        bOrder.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bSearchmore.setOnClickListener(this);
        entry_name = getArguments().getString(dish_name);
        entry_id = getArguments().getInt(dish_id);
        if (!entry_name.equalsIgnoreCase("")) {
            display.setText(entry_name);
        }
        dish_count = 1;
        counts.setMode(LayouMode.SINGLE_CHOICE);
        counts.setThemeOnActive(R.style.tagactive);
        counts.setTheme(R.style.tagnormal);
        counts.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                dish_count = position + 1;
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }
        });
        counts.setTags(new String[]{"1", "2", "3", "4", "5", "6", "7"});
        hideLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_dish;
    }

    @Override
    protected void bind(View view) {
        bindProgressBar(view);
        bOrder = (Button) view.findViewById(R.id.new_order);
        bSearchmore = (Button) view.findViewById(R.id.find_other_dish);
        bCancel = (Button) view.findViewById(R.id.cancelthis);
        display = (TextView) view.findViewById(R.id.dish_name_display);
        single_imagle = (ImageView) view.findViewById(R.id.display_dish_image);
        counts = (TagContainerLayout) view.findViewById(R.id.item_ul_tag_container);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == bOrder) {
            boolean status = false;
            if (dish_count > 0) {
                for (int h = 0; h < dish_count; h++) {
                    status = instanceBillContainer.makeNewOrderEntry(entry_id);
                }
            }
            if (status) {
                StringBuilder sb = new StringBuilder();
                sb.append(entry_name);
                sb.append("has been added to the order list successfully");

                Snackbar
                        .make(v, sb.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();

                if (getActivity() instanceof MainOffice) {
                    ((MainOffice) getActivity()).backHome();
                }
            }else{
                StringBuilder sb = new StringBuilder();
                sb.append(entry_name);
                sb.append(" not success.");
                Snackbar
                        .make(v, sb.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        } else if (v == bSearchmore) {

        } else if (v == bCancel) {
            if (getActivity() instanceof MainOffice) {
                ((MainOffice) getActivity()).backHome();
            }
        }
    }


}
