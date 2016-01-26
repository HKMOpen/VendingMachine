package com.hkmvend.apiclitest.usage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkmvend.apiclitest.R;
import com.hkmvend.apiclitest.mosaic.MosAdapter;
import com.hkmvend.sdk.storage.Menu.MenuEntry;

/**
 * Created by zJJ on 1/25/2016.
 */
public class ImageDisMos extends MosAdapter<MenuEntry, ImageDisMos.Frame> {
    public ImageDisMos(Context context) {
        super(context);
    }

    public class Frame {
        private ImageView image;
        private TextView text;

        public Frame(View view) {
            image = (ImageView) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.lylib_main_bottom_tab_text);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.item_mos_row;
    }

    @Override
    protected void bindData(MenuEntry menuEntry, int position, ImageDisMos.Frame binder) {
        binder.text.setText(MenuEntry.getDisplayCateName(menuEntry.getCategory()));
        binder.image.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    protected Frame getBinder(View view) {
        return new Frame(view);
    }
}
