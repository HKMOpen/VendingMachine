package com.hkm.staffvend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hkm.layout.Dialog.ErrorMessage;
import com.hkm.staffvend.R;
import com.hkmvend.sdk.Constant;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Menu.MenuEntry;

import java.util.List;

/**
 * Created by hesk on 5/2/16.
 */
public class SetupDash extends AppCompatActivity {

    RestaurantPOS pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_dash);
      /*  TextView appv = (TextView) findViewById(R.id.app_version);
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.VERSION_NAME);
        sb.append(" (");
        sb.append(BuildConfig.VERSION_CODE);
        sb.append(")");
        appv.setText(sb.toString());*/


        pos = RestaurantPOS.getInstance(getApplication());

        try {
            if (pos.getContainer().getItemsCount() > 0) {
                nextSetup();
            } else {
                pos.setCB(callback);
                pos.setDatabaseId(Constant.SHEETSU_DOC_ID);
                pos.runType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    RestaurantPOS.DataConfigCB callback = new RestaurantPOS.DataConfigCB() {
        @Override
        public void success(final List<MenuEntry> list) {
            nextSetup();
        }

        @Override
        public void failure(String error_cause) {
            ErrorMessage.alert(error_cause, getFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    try {
                        pos.runType();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void nextSetup() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent in = new Intent(SetupDash.this, MainOffice.class);
                //   Bundle b = new Bundle();
                //    b.putLong(INTENT_BILL_ID, item.getBill_number_code());
                //   b.putInt(INTENT_TABLE_FUNCTION, MAKE_PAYMENT);
                //   in.putExtras(b);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SetupDash.this.startActivity(in);
                SetupDash.this.finish();
            }
        }, 500);
    }
}
