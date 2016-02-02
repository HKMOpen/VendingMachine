package com.hkm.gogosushi.effect3;

/**
 * Created by hesk on 4/12/2015.
 */

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.gogosushi.R;

import java.util.List;

import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselItem;

/**
 * @author Ludovic ROLAND
 * @since 2014.12.20
 */
public final class MyAdapter
        extends CarouselAdapter<Photo>
{

    public static final class PhotoItem
            extends CarouselItem<Photo>
    {

        private ImageView image;

        private TextView name;

        private Context context;

        public PhotoItem(Context context)
        {
            super(context, R.layout.carouselitem);
            this.context = context;
        }

        @Override
        public void extractView(View view)
        {
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public void update(Photo photo)
        {
            image.setImageResource(getResources().getIdentifier(photo.image, "drawable", context.getPackageName()));
            name.setText(photo.name);
        }

    }

    public MyAdapter(Context context, List<Photo> photos)
    {
        super(context, photos);
    }

    @Override
    public CarouselItem<Photo> getCarouselItem(Context context)
    {
        return new PhotoItem(context);
    }

}