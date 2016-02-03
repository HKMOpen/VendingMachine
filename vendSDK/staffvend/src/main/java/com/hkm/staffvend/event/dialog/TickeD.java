package com.hkm.staffvend.event.dialog;

import android.app.Fragment;
import android.os.Bundle;

import com.hkm.staffvend.R;
import com.hkmvend.sdk.client.RestaurantPOS;

/**
 * Created by hesk on 28/1/16.
 */
public class TickeD extends DialogTextInput {
    public static final String TAG = TickeD.class.getSimpleName();
    public static final String ARG_ITEM = "item";
    public static final String ARG_ITEM_POSITION = "position";
    //private Bill mItem;
    private long mPosition;
    private boolean itemFound = false;


    public TickeD() {
    }

    public static TickeD newInstance(Fragment onactivityresultfragment) {
        TickeD dialog = new TickeD();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        dialog.setTargetFragment(onactivityresultfragment, 0);
        return dialog;
    }


    @Override
    protected String getDefaultFieldDisplay(Bundle bundle) {
        //mItem = (Bill) bundle.getSerializable(ARG_ITEM);
        final long mLBN = RestaurantPOS.getInstance(getActivity().getApplication()).getBillContainer().getLastestBillNumber();
        setFoundData(mLBN == -1);
        final String field_display = isDataFound() ? "" : mLBN + "";
        return field_display;
    }

    @Override
    protected int getLayoutField() {
        return R.layout.dialog_edit_number;
    }
}
