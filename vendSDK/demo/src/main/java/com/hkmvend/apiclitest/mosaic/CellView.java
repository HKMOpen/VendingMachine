package com.hkmvend.apiclitest.mosaic;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by zJJ on 1/23/2016.
 */
public class CellView extends LinearLayout {
    public CellView(Context context) {
        super(context);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}
