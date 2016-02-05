package com.hkm.staffvend.event.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.hkm.staffvend.R;
import com.hkm.staffvend.event.Utils;
import com.hkm.staffvend.usage.SimpleTextWatcher;
import com.hkmvend.sdk.client.RestaurantPOS;

/**
 * Created by hesk on 28/1/16.
 */
public abstract class DialogTextInput extends DialogFragment {
    public static final String TAG = DialogTextInput.class.getSimpleName();

    public interface OnEditItemListener {
        void onFieldModified(long position, String newTitle);
    }

    private boolean data_is_existed = false;
    private long mPosition = 0;


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

    private OnEditItemListener getListener() {
        OnEditItemListener listener = (OnEditItemListener) getTargetFragment();
        if (listener == null) {
            listener = (OnEditItemListener) getActivity();
        }
        return listener;
    }


    @Override
    public void onStart() {
        super.onStart();
        // getDialog().getWindow().setWindowAnimations(R.style.animation_slide_from_right);
    }

    @LayoutRes
    protected abstract int getLayoutField();

    protected abstract String getDefaultFieldDisplay(Bundle fragmentintent);

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
        final String field_display = getDefaultFieldDisplay(bundle);
        //Inflate custom view
        View dialogView = LayoutInflater.from(getActivity()).inflate(getLayoutField(), null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_text_edit);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//, R.style.AppTheme_AlertDialog);
        builder.setTitle(R.string.dialog_edit_title)
                .setView(dialogView)
                .setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.hideSoftInputFrom(getActivity(), editText);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getListener().onFieldModified(mPosition, editText.getText().toString().trim());
                        Utils.hideSoftInputFrom(getActivity(), editText);
                        dialog.dismiss();
                    }
                });

        final AlertDialog editDialog = builder.create();

        editDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateOkButtonState(editDialog, null);
            }
        });

        if (field_display != null) {
            editText.setText(field_display);
            editText.selectAll();
        }
        editText.requestFocus();
        editText.addTextChangedListener(new SimpleTextWatcher() {
            private final static long DELAY = 400L;
            private final static int TRIGGER = 1;

            private Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == TRIGGER) {
                        updateOkButtonState(editDialog, editText);
                    }
                }
            };

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateOkButtonState(editDialog, null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeMessages(TRIGGER);
                mHandler.sendEmptyMessageDelayed(TRIGGER, DELAY);
            }
        });

        editDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return editDialog;
    }

    private void updateOkButtonState(AlertDialog dialog, EditText editText) {
        Button buttonOK = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (editText == null || (editText.getText().toString().trim()).length() == 0) {
            data_is_existed = false;
            if (editText != null) {
                editText.setError(getActivity().getString(R.string.err_no_edit));
            }
            buttonOK.setEnabled(false);
            return;
        } else {
            data_is_existed = true;
            buttonOK.setEnabled(true);
        }


        return;
    }


}
