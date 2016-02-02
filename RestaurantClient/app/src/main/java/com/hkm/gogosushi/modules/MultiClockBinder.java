package com.hkm.gogosushi.modules;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.gogosushi.R;
import com.hkm.gogosushi.product.product;
import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.multiViewTypes.DataBinder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hesk on 5/31/2015.
 */
public class MultiClockBinder extends DataBinder<MultiClockBinder.ViewHolder> implements View.OnClickListener {
    List<String> dataSet;
    final Picasso pica;
    final Context cc;

    public MultiClockBinder(MultiViewTypesRecyclerViewAdapter dataBindAdapter, List<String> dataSet) {
        super(dataBindAdapter);
        this.dataSet = dataSet;
        cc = dataBindAdapter.getContext();
        pica = Picasso.with(cc);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.m_promotion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        try {
            String title = cc.getResources().getString(R.string.sample_title);
            String url = cc.getResources().getString(R.string.sample_coupon_url);
            holder.mTitleText.setText(title);
            pica.load(url).into(holder.mImageView);
            holder.mImageView.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(cc, product.class);
        cc.startActivity(intent);
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView mTitleText;
        ImageView mImageView;
        TextView mContent;

        public ViewHolder(View view) {
            super(view);
            mTitleText = (TextView) view.findViewById(R.id.title_type1);
            mImageView = (ImageView) view.findViewById(R.id.image_type1);
            mContent = (TextView) view.findViewById(R.id.content_type1);
        }
    }
}
