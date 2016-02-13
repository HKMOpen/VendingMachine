package com.hkm.staffvend.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.hkm.staffvend.R;
import com.hkm.staffvend.event.ApplicationConstant;
import com.hkm.staffvend.ui.SecBillCollection;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;

import static com.hkm.staffvend.event.ApplicationConstant.FUNC_VIEW_ONLY;
import static com.hkm.staffvend.event.ApplicationConstant.INTENT_TABLE_FILTER;
import static com.hkm.staffvend.event.ApplicationConstant.INTENT_TABLE_FUNCTION;
import static com.hkm.staffvend.event.ApplicationConstant.VIEW_UNPAID_TABLES;

/**
 * Created by zJJ on 2/13/2016.
 */
public class tableDialog extends DialogFragment {

    public static tableDialog newInstance(Fragment onactivityresultfragment) {
        tableDialog dialog = new tableDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        dialog.setTargetFragment(onactivityresultfragment, 0);
        return dialog;
    }


    public static final String TAG = DialogTextInput.class.getSimpleName();

    private boolean data_is_existed = false;
    private String table_name;
    private long mPosition = 0;

    public interface OnEditItemListener {
        void onFieldModified(long position, String newTitle);
    }


    protected void setPosition(long e) {
        mPosition = e;
    }

    protected long getPosition() {
        return mPosition;
    }

    protected void setFoundData(boolean b) {
        data_is_existed = b;
    }


    protected boolean isDataFound() {
        return data_is_existed;
    }

    @LayoutRes
    protected int getLayoutField() {
        return R.layout.dialog_tables;
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setWindowAnimations(R.style.animation_slide_from_right);
    }

    private void onfigure(TagContainerLayout table_layout) {
        final BillContainer cont = RestaurantPOS.getInstance(getActivity().getApplication()).getBillContainer();
        final int[] selectionScope = ApplicationConstant.generatePreselection(cont);


        table_layout.setMode(LayouMode.SINGLE_CHOICE);
        table_layout.setThemeOnActive(R.style.tagactive);
        table_layout.setTheme(R.style.tagnormal_table);
        table_layout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                if (ApplicationConstant.inPreselectionScope(position, selectionScope)) {
                    table_name = text;
                } else {
                    table_name = null;
                }
                updateOkButtonState((AlertDialog) getDialog());
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }
        });

        table_layout.setPreselectedTags(selectionScope, R.style.tagactive_pre);
        table_layout.setTags(ApplicationConstant.getTables());
    }

    @SuppressLint({"InflateParams", "HandlerLeak"})
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        //Pick up bundle parameters
        Bundle bundle;
        if (savedInstanceState == null) {
            bundle = getArguments();
        } else {
            bundle = savedInstanceState;
        }

        //Inflate custom view
        View dialogView = LayoutInflater.from(getActivity()).inflate(getLayoutField(), null);
        onfigure((TagContainerLayout) dialogView.findViewById(R.id.item_ul_table_container));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//, R.style.AppTheme_AlertDialog);
        builder.setTitle(R.string.dialog_select_table)
                .setView(dialogView)
                .setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTable();
                        dialog.dismiss();
                    }
                });

        final AlertDialog tableDialog = builder.create();
        tableDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateOkButtonState(tableDialog);
            }
        });
        return tableDialog;
    }


    private void updateOkButtonState(AlertDialog dialog) {
        Button buttonOK = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (table_name == null) {
            buttonOK.setEnabled(false);
        } else {
            buttonOK.setEnabled(true);
        }
    }

    private void selectTable() {
        Bundle b = new Bundle();
        b.putInt(INTENT_TABLE_FUNCTION, FUNC_VIEW_ONLY);
        b.putStringArray(INTENT_TABLE_FILTER, new String[]{
                Bill.Field_pay_time, "unpaid",
                Bill.Field_table_id, table_name,
        });
        Intent i = new Intent(getActivity(), SecBillCollection.class);
        i.putExtras(b);
        getActivity().startActivityForResult(i, VIEW_UNPAID_TABLES);
    }
}
