package com.hkm.gogosushi.modules;

import com.hkm.gogosushi.R;
import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 5/31/2015.
 */
public class MultiViewTypesRecyclerViewAdapter extends UltimateDifferentViewTypeAdapter {
    private final List<String> mDataset;

    private enum dataViewType {
        PROMOTION, SWAP, CLOCK
    }

    private enum SwipedState {
        SHOWING_PRIMARY_CONTENT,
        SHOWING_SECONDARY_CONTENT
    }

    private List<SwipedState> mItemSwipedStates;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    final Context ctxpointer;

    public Context getContext() {
        return ctxpointer;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MultiViewTypesRecyclerViewAdapter(List<String> dataSet, Context tc) {
        mDataset = dataSet;
        mItemSwipedStates = new ArrayList<>();
        for (int i = 0; i < dataSet.size(); i++) {
            mItemSwipedStates.add(i, SwipedState.SHOWING_PRIMARY_CONTENT);
        }
        ctxpointer = tc;
        putBinder(dataViewType.PROMOTION, new MultiPromtonBinder(this, dataSet));
        putBinder(dataViewType.CLOCK, new MultiClockBinder(this, dataSet));
        putBinder(dataViewType.SWAP, new MultiSweepBinder(this, dataSet));

        //  ((Sample2Binder) getDataBinder(dataViewType.SAMPLE2)).addAll(dataSet);
    }

    public void insert(String string, int position) {
        insert(mDataset, string, position);
    }

    public void remove(int position) {
        remove(mDataset, position);
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_big_main, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public Enum getEnumFromPosition(int position) {
        if (position % 2 == 1) {
            return dataViewType.PROMOTION;
        } else {
            return dataViewType.SWAP;
        }
    }

    @Override
    public Enum getEnumFromOrdinal(int ordinal) {
        return dataViewType.values()[ordinal];
    }


}
