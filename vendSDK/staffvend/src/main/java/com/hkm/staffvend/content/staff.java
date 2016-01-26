package com.hkm.staffvend.content;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.SectionNewTable;
import com.hkm.staffvend.event.BS;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.BillContainer;


/**
 * Created by hesk on 26/1/16.
 */
public class staff extends content_base {

    private Button signInStaff, checkTablesPaid, checkTablesUnpaid, newtable;
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
                Intent i = new Intent(getActivity(), SectionNewTable.class);
                getActivity().startActivityForResult(i, BS.NEW_TABLE);
            }
        });

        checkTablesPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SectionNewTable.class);
                getActivity().startActivityForResult(i, BS.VIEW_PAID_TABLES);
            }
        });

        checkTablesUnpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SectionNewTable.class);
                getActivity().startActivityForResult(i, BS.VIEW_UNPAID_TABLES);
            }
        });

        signInStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
        signInStaff = (Button) view.findViewById(R.id.signoutin);
        checkTablesPaid = (Button) view.findViewById(R.id.checkcompletetable);
        checkTablesUnpaid = (Button) view.findViewById(R.id.checkTables);
        newtable = (Button) view.findViewById(R.id.newtable);
        current_table = (TextView) view.findViewById(R.id.current_table);
        current_status = (TextView) view.findViewById(R.id.current_status);
    }

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.content_main_office;
    }

}
