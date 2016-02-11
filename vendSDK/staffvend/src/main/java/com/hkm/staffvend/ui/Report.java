package com.hkm.staffvend.ui;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.hkm.staffvend.R;
import com.hkm.staffvend.compon.usage.SimpleDividerItemDecoration;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.Bill.BillContainer;
import com.marshalchen.ultimaterecyclerview.animators.SlideInRightAnimator;
/**
 *
 * Created by zJJ on 2/11/2016.
 */
public class Report extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BillContainer instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_report);

        mRecyclerView = (RecyclerView) findViewById(R.id.axj_ul_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true); //Size of views will not change as the data changes
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null)));

        TextView display = (TextView) findViewById(R.id.report_display);
        getReport(display);
    }

    private void getReport(TextView txtotal) {
        instance = RestaurantPOS.getInstance(getApplication()).getBillContainer();
        float totalsum = instance.getConsolidatedTotal();
        float total_service = totalsum * 0.1f;

        StringBuilder sb = new StringBuilder();
        sb.append("Service charge: $");
        sb.append(total_service);
        sb.append("\n");
        sb.append("Total Revenue: $");
        sb.append(totalsum);

        txtotal.setText(sb.toString());
    }

}
