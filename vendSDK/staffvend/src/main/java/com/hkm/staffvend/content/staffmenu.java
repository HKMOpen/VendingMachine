package com.hkm.staffvend.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.SecAbout;
import com.hkm.staffvend.SecBillCollection;
import com.hkm.staffvend.SecNewTable;
import com.hkm.staffvend.event.BS;
import com.hkm.staffvend.event.dialog.DialogTextInput;
import com.hkm.staffvend.event.dialog.TickeD;
import com.hkm.staffvend.event.dialog.TicketNumD;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.Bill;
import com.hkmvend.sdk.storage.Bill.BillContainer;

import static com.hkm.staffvend.event.ApplicationConstant.FUNC_VIEW_ONLY;
import static com.hkm.staffvend.event.ApplicationConstant.INTENT_TABLE_FILTER;
import static com.hkm.staffvend.event.ApplicationConstant.INTENT_TABLE_FUNCTION;
import static com.hkm.staffvend.event.ApplicationConstant.NEW_TABLE;
import static com.hkm.staffvend.event.ApplicationConstant.VIEW_PAID_TABLES;
import static com.hkm.staffvend.event.ApplicationConstant.VIEW_UNPAID_TABLES;


/**
 * Created by hesk on 26/1/16.
 */
public class staffmenu extends content_base {

    private Button signInStaff, aboutButton, mSettings, checkTablesPaid, checkTablesUnpaid, newtable, setButton, importButton;
    private TextView current_table, current_status;
    private BillContainer container;
    private boolean order_ready;

    @Override
    protected void initGDATA() {
        container = RestaurantPOS
                .getInstance(getActivity().getApplication())
                .getBillContainer();
        newtable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecNewTable.class);
                getActivity().startActivityForResult(i, NEW_TABLE);
            }
        });
        checkTablesPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecBillCollection.class);
                Bundle b = new Bundle();
                b.putInt(INTENT_TABLE_FUNCTION, FUNC_VIEW_ONLY);
                b.putStringArray(INTENT_TABLE_FILTER, new String[]{Bill.Field_pay_time, "paid"});
                i.putExtras(b);
                getActivity().startActivityForResult(i, VIEW_PAID_TABLES);
            }
        });
        checkTablesUnpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt(INTENT_TABLE_FUNCTION, FUNC_VIEW_ONLY);
                b.putStringArray(INTENT_TABLE_FILTER, new String[]{Bill.Field_pay_time, "unpaid"});
                Intent i = new Intent(getActivity(), SecBillCollection.class);
                i.putExtras(b);
                getActivity().startActivityForResult(i, VIEW_UNPAID_TABLES);
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BS.toSettings();
            }
        });
     /*
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TickeD.newInstance(staffmenu.this).show(getFragmentManager(), TicketNumD.TAG);
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Intent i = new Intent(getActivity(), SecScanTbe.class);
                getActivity().startActivityForResult(i, IMPORT_RESTUARANT_MENU);*//*
                BS.start_scanning(getActivity());
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecAbout.class);
                getActivity().startActivity(i);
            }
        });
*/
        refreshEngagedTable();
    }

    public void refreshEngagedTable() {
        if (container.hasTableFocused()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Order ready");
            sb.append("\n");
            sb.append(container.getCurrentEngagedTable().getTable_remark());
            current_status.setText(sb.toString());
            current_table.setText(container.getCurrentEngagedTable().getTable_id());
            order_ready = true;
        } else {
            current_status.setText("cannot make orders");
            current_table.setText("There is no engaging Table");
            order_ready = false;
        }
    }

    public boolean getOrderReady() {
        return order_ready;
    }

    protected void bind(View view) {

        checkTablesPaid = (Button) view.findViewById(R.id.checkcompletetable);
        checkTablesUnpaid = (Button) view.findViewById(R.id.checkTables);
        newtable = (Button) view.findViewById(R.id.newtable);
    //    aboutButton = (Button) view.findViewById(R.id.about);
        mSettings = (Button)view.findViewById(R.id.settings);
      /*  setButton = (Button) view.findViewById(R.id.setlastesttioc);
        signInStaff = (Button) view.findViewById(R.id.signoutin);
        importButton = (Button) view.findViewById(R.id.importmenu);
        aboutButton = (Button) view.findViewById(R.id.about);
        */
        current_table = (TextView) view.findViewById(R.id.current_table);
        current_status = (TextView) view.findViewById(R.id.current_status);
    }

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.content_main_office;
    }

  

}
