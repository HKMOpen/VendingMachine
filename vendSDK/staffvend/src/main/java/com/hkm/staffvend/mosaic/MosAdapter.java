package com.hkm.staffvend.mosaic;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hkm.staffvend.R;

import java.util.List;

/**
 * Created by zJJ on 1/25/2016.
 */
public abstract class MosAdapter<DATA, BIND> extends ArrayAdapter<Object> {
    private Context context;
    private List<DATA> values;

    public MosAdapter(Context context) {
        super(context, R.layout.item_mos_row);
        this.context = context;
    }

    public void setData(List<DATA> values) {
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layoutId(), parent, false);
        bindData(values.get(position), position, getBinder(rowView));
        return rowView;
    }

    @LayoutRes
    protected abstract int layoutId();

    protected abstract void bindData(DATA data, int position, BIND binder);

    protected abstract BIND getBinder(View view);
}