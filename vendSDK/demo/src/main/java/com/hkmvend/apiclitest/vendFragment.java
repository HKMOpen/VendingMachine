package com.hkmvend.apiclitest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hkm.layout.Dialog.ErrorMessage;
import com.hkmvend.apiclitest.mosaic.MosaicLayout;
import com.hkmvend.apiclitest.usage.ImageDisMos;
import com.hkmvend.sdk.Constant;
import com.hkmvend.sdk.client.RestaurantPOS;
import com.hkmvend.sdk.storage.MenuEntry;

import java.util.List;

import static com.hkmvend.apiclitest.mosaic.BlockPattern.BLOCK_PATTERN;

/**
 * A placeholder fragment containing a simple view.
 */
public class vendFragment extends Fragment {

    public vendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMosaicLayout = (MosaicLayout) view.findViewById(R.id.vend_mosaic);
        mProgress = (ProgressBar) view.findViewById(R.id.lylib_ui_loading_circle);
        initGDATA();
    }

    private ProgressBar mProgress;
    private MosaicLayout mMosaicLayout;

    BLOCK_PATTERN pattern1[] = {BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
            BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL};
    BLOCK_PATTERN pattern2[] = {BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG,
            BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG};


    RestaurantPOS.DataConfigCB callback = new RestaurantPOS.DataConfigCB() {
        @Override
        public void success(final List<MenuEntry> list) {
            ErrorMessage.alert("successfully found items " + list.size(), getChildFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    continoueAfterSync(list);
                }
            });
        }

        @Override
        public void failure(String error_cause) {
            ErrorMessage.alert(error_cause, getChildFragmentManager(), new Runnable() {
                @Override
                public void run() {
                    initGDATA();
                }
            });
        }
    };

    private void continoueAfterSync(List<MenuEntry> list) {
        ImageDisMos mAdapater = new ImageDisMos(getActivity());
        mAdapater.setData(list);
        mMosaicLayout.addPattern(pattern1);
        mMosaicLayout.addPattern(pattern2);
        mMosaicLayout.chooseRandomPattern(true);
        mMosaicLayout.setAdapter(mAdapater);
        mProgress.animate().alpha(0f);
    }

    protected void initGDATA() {
        RestaurantPOS c = RestaurantPOS.getInstance(getActivity().getApplication());
        try {
            c.setCB(callback);
            c.setDatabaseId(Constant.SHEETSU_DOC_ID);
            c.runType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
