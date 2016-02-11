package com.hkm.staffvend.compon.usage;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkm.staffvend.R;
import com.hkm.staffvend.compon.mosaic.MosAdapter;

/**
 * Created by hesk on 26/1/16.
 */
public class DisplayB1 extends MosAdapter<MainMenuItem, DisplayB1.Frame> {
    public class Frame {
        private ImageButton image;
        private TextView text;

        public Frame(View view) {
            image = (ImageButton) view.findViewById(R.id.item_button_bg);
            text = (TextView) view.findViewById(R.id.item_button_tex1);
        }
    }

    public DisplayB1(Context context) {
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


    @Override
    protected int layoutId() {
        return R.layout.item_sub_v1;
    }

    @Override
    protected void bindData(final MainMenuItem menuEntry, int position, DisplayB1.Frame binder) {
        binder.text.setText(menuEntry.getName());
        //binder.image.setImageResource(R.mipmap.ic_launcher_jj);
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
