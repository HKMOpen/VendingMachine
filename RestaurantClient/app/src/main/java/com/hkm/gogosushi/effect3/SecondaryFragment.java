package com.hkm.gogosushi.effect3;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hkm.gogosushi.R;

import java.util.ArrayList;
import java.util.List;

import fr.rolandl.carousel.Carousel;
import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselBaseAdapter;

/**
 * Created by hesk on 4/12/2015.
 */
public class SecondaryFragment    extends Fragment
        implements CarouselBaseAdapter.OnItemClickListener, CarouselBaseAdapter.OnItemLongClickListener
{

    private CarouselAdapter adapter;

    private Carousel carousel;

    private final List<Photo> photos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.carousel_gava_item, null);

        carousel = (Carousel) view.findViewById(R.id.carousel);

        photos.add(new Photo("Photo1", "diamond28"));
        photos.add(new Photo("Photo2", "diamond28"));
        photos.add(new Photo("Photo3", "diamond28"));
        photos.add(new Photo("Photo4", "diamond28"));
        photos.add(new Photo("Photo5", "diamond28"));

        adapter = new MyAdapter(getActivity(), photos);
        carousel.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        carousel.setOnItemClickListener(new CarouselBaseAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(CarouselBaseAdapter<?> carouselBaseAdapter, View view, int position, long l)
            {
                Toast.makeText(getActivity().getApplicationContext(), "The item '" + position + "' has been clicked", Toast.LENGTH_SHORT).show();
                carousel.scrollToChild(position);
            }
        });

        carousel.setOnItemLongClickListener(new CarouselBaseAdapter.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(CarouselBaseAdapter<?> carouselBaseAdapter, View view, int position, long id)
            {
                Toast.makeText(getActivity().getApplicationContext(), "The item '" + position + "' has been long clicked", Toast.LENGTH_SHORT).show();
                carousel.scrollToChild(position);
                return false;
            }

        });

        return view;
    }

    @Override
    public void onItemClick(CarouselBaseAdapter<?> parent, View view, int position, long id)
    {
        Toast.makeText(getActivity().getApplicationContext(), "The item '" + position + "' has been clicked", Toast.LENGTH_SHORT).show();
        carousel.scrollToChild(position);
    }

    @Override
    public boolean onItemLongClick(CarouselBaseAdapter<?> parent, View view, int position, long id)
    {
        Toast.makeText(getActivity().getApplicationContext(), "The item '" + position + "' has been long clicked", Toast.LENGTH_SHORT).show();
        carousel.scrollToChild(position);
        return false;
    }
}