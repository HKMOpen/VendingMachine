package com.hkm.gogosushi.effect4;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hkm.gogosushi.R;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;

import java.util.ArrayList;

/**
 * Created by hesk on 4/12/2015.
 */
public class SDis extends Fragment {

    private HorizontalCarouselStyle mStyle;
    private HorizontalCarouselLayout mCarousel;
    private CarouselAdapter mAdapter;
    private ArrayList<Integer> mData = new ArrayList<Integer>(0);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.carousel_it, null);
        mData.add(R.drawable.diamond28);
        mData.add(R.drawable.diamond28);
        mData.add(R.drawable.diamond28);
        mData.add(R.drawable.diamond28);
        mAdapter = new CarouselAdapter(getActivity());
        mAdapter.setData(mData);
        mCarousel = (HorizontalCarouselLayout) view.findViewById(R.id.carousel_layout);
        mStyle = new HorizontalCarouselStyle(getActivity(), HorizontalCarouselStyle.STYLE_COVERFLOW);
        mCarousel.setStyle(mStyle);
        mCarousel.setAdapter(mAdapter);

        mCarousel.setOnCarouselViewChangedListener(new HorizontalCarouselLayout.CarouselInterface() {
            @Override
            public void onItemChangedListener(View v, int position) {
                Toast.makeText(getActivity(), "Position " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
