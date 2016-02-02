package com.hkm.gogosushi.pages;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hkm.gogosushi.R;
import com.hkm.gogosushi.modules.MultiViewTypesRecyclerViewAdapter;
import com.hkm.longmenu.Bind;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 4/11/2015.
 */
public class introductions extends Fragment {
    MultiViewTypesRecyclerViewAdapter typedRecyclerViewAdapter = null;
    UltimateRecyclerView recyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Right", "onCreateView()");
        return inflater.inflate(R.layout.list_big_main, container, false);
    }

    final List<String> stringList = new ArrayList<>();

    protected void gendata() {
        stringList.add("111df");
        stringList.add("adaad");
        stringList.add("222s");
        stringList.add("33df");
        stringList.add("44s");
        stringList.add("55f");
        stringList.add("222s");
        stringList.add("33df");
        stringList.add("44s");
        stringList.add("55f");
        stringList.add("222s");
        stringList.add("33df");
        stringList.add("44s");
        stringList.add("55f");
    }

    protected void buildBorder() {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            recyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).paint(paint).build());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("TAG", "onViewCreated");
        gendata();
        recyclerView = (UltimateRecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(false);
        typedRecyclerViewAdapter = new MultiViewTypesRecyclerViewAdapter(stringList, getActivity());
        typedRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(getActivity())
                .inflate(R.layout.custom_bottom_progressbar, null));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        typedRecyclerViewAdapter.insert("Refresh things", 0);
                        recyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                    }
                }, 1000);
            }
        });
        buildBorder();
        recyclerView.setAdapter(typedRecyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });
    }

}
