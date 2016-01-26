package com.hkm.staffvend.usage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.mosaic.MosAdapter;
import com.hkmvend.sdk.storage.MenuEntry;

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
        StringBuilder sb = new StringBuilder();
        sb.append(menuEntry.getEntry_name_chinese());
        sb.append("\n");
        sb.append(menuEntry.getEntry_name_english());

        sb.append("(");
        sb.append(menuEntry.getEntry_short_form());
        sb.append(")");


        binder.text.setText(sb.toString());
        //  binder.image.setImageResource(R.mipmap.ic_launcher);

        binder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected Frame getBinder(View view) {
        return new Frame(view);
    }
}
