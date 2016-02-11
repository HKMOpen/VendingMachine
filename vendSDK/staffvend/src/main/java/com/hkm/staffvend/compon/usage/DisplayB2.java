package com.hkm.staffvend.compon.usage;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.event.BS;
import com.hkm.staffvend.compon.mosaic.MosAdapter;
import com.hkmvend.sdk.storage.Menu.MenuEntry;

/**
 * Created by zJJ on 1/25/2016.
 */
public class DisplayB2 extends MosAdapter<MenuEntry, DisplayB2.Frame> {
    public DisplayB2(Context context) {
        super(context);
    }

    public class Frame {
        private ImageButton image;
        private TextView text;

        public Frame(View view) {
            image = (ImageButton) view.findViewById(R.id.item_button_bg);
            text = (TextView) view.findViewById(R.id.item_button_tex1);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.item_sub_v1;
    }

    @Override
    protected void bindData(final MenuEntry menuEntry, int position, DisplayB2.Frame binder) {
        StringBuilder sb = new StringBuilder();
        sb.append(menuEntry.getEntry_name_chinese());
        sb.append("\n");
        sb.append(menuEntry.getEntry_name_english());

        sb.append("(");
        sb.append(menuEntry.getEntry_short_form());
        sb.append(")");


        binder.text.setText(sb.toString());
        //  binder.image.setImageResource(R.mipmap.ic_launcher_jj);

        binder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BS.select_menu_item_from_submenu(menuEntry);
            }
        });
    }

    @Override
    protected Frame getBinder(View view) {
        return new Frame(view);
    }
}
