package com.hkm.staffvend.content;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hkm.staffvend.R;
import com.hkm.staffvend.mosaic.MosaicLayout;

import static com.hkm.staffvend.mosaic.BlockPattern.BLOCK_PATTERN;

/**
 * Created by hesk on 26/1/16.
 */
public abstract class content_base extends Fragment {
    @LayoutRes
    protected int getLayoutId() {
        return R.layout.content_menu_puzzle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind(view);
        initGDATA();
    }

    protected void hideLoad() {
        mProgress.animate().alpha(0f);
    }

    protected void bind(View view) {
        mMosaicLayout = (MosaicLayout) view.findViewById(R.id.vend_mosaic);
        mTopButton = (Button) view.findViewById(R.id.main_button);
        bindProgressBar(view);
    }

    protected void bindProgressBar(View view) {
        mProgress = (ProgressBar) view.findViewById(R.id.lylib_ui_loading_circle);
    }

    protected abstract void initGDATA();

    protected ProgressBar mProgress;
    protected MosaicLayout mMosaicLayout;
    protected Button mTopButton;

    public static final BLOCK_PATTERN pattern1[] = {
            BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
            BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL
    };
    public static final BLOCK_PATTERN pattern2[] = {
            BLOCK_PATTERN.VERTICAL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL,
            BLOCK_PATTERN.VERTICAL, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.SMALL
    };
    public static final BLOCK_PATTERN pattern3[] = {
            BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
            BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL
    };
}
