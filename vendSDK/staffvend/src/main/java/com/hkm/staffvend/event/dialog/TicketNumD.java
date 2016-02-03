package com.hkm.staffvend.event.dialog;

import android.app.Fragment;
import android.os.Bundle;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;

/**
 * Created by hesk on 27/1/16.
 */
public class TicketNumD extends DialogTextInput {

    public static final String ARG_ITEM = "item";
    public static final String ARG_ITEM_POSITION = "position";

    public TicketNumD() {
    }

    @Override
    protected int getLayoutField() {
        return 0;
    }

    @Override
    protected String getDefaultFieldDisplay(Bundle bundle) {
        long mPosition = bundle.getLong(ARG_ITEM_POSITION);
        final Bill mItem = RestaurantPOS.getInstance(getActivity().getApplication()).getBillContainer().findBillById(mPosition);
        setPosition(mPosition);
        setFoundData(mItem == null ? false : true);
        return mItem.getBill_number_code() + "";
    }

    public static TicketNumD newInstance(long position) {
        return newInstance(position, null);
    }

    public static TicketNumD newInstance(long position, Fragment onactivityresultfragment) {
        TicketNumD dialog = new TicketNumD();
        Bundle args = new Bundle();
        // args.putSerializable(ARG_ITEM, item);
        args.putLong(ARG_ITEM_POSITION, position);
        dialog.setArguments(args);
        dialog.setTargetFragment(onactivityresultfragment, 0);
        return dialog;
    }

    private OnEditItemListener getListener() {
        OnEditItemListener listener = (OnEditItemListener) getTargetFragment();
        if (listener == null) {
            listener = (OnEditItemListener) getActivity();
        }
        return listener;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //  outState.putSerializable(ARG_ITEM, mItem);
        outState.putLong(ARG_ITEM_POSITION, getPosition());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        // getDialog().getWindow().setWindowAnimations(R.style.animation_slide_from_right);
    }

}
