package com.hkm.staffvend.usage;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.mosaic.MosAdapter;

/**
 * Created by hesk on 26/1/16.
 */
public class MainMenu extends MosAdapter<MainMenuItem, MainMenu.Frame> {

    public MainMenu(Context context) {
        super(context);
    }

    public interface clickButton {
        void lclipko(int cate_id);
    }

    private clickButton lip = new clickButton() {
        @Override
        public void lclipko(int cate_id) {

        }
    };

    public void setCB(clickButton button) {
        lip = button;
    }

    public class Frame {
        private ImageButton image;
        private TextView text;

        public Frame(View view) {
            image = (ImageButton) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.lylib_main_bottom_tab_text);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.item_main_menu;
    }

    @Override
    protected void bindData(final MainMenuItem menuEntry, int position, MainMenu.Frame binder) {
        binder.text.setText(menuEntry.getName());
        //binder.image.setImageResource(R.mipmap.ic_launcher);
        binder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lip.lclipko(menuEntry.getCateId());
            }
        });
    }


    @Override
    protected Frame getBinder(View view) {
        return new Frame(view);
    }
}
