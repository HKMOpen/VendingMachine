package com.hkmvend.sdk.model.gdata;

import com.google.gson.annotations.SerializedName;
import com.hkmvend.sdk.storage.MenuEntry;

/**
 * Created by zJJ on 1/23/2016.
 */
public class EntryCell {
    @SerializedName("v")
    private Object value;
    @SerializedName("f")
    private String label;

    public EntryCell() {

    }

    public boolean hasFloat() {
        return value instanceof Float;
    }

    public float getAsFloat() {
        return (float) value;
    }

    public int getAsInt() {
        return (int) value;
    }

    @MenuEntry.EntryTypes
    public int getAsEntryTypes() {
        String v = (String) value;
        return MenuEntry.valueOf(v);
    }

    public boolean hasLabel() {
        return label != null;
    }

    public String getLabel() {
        return label;
    }
}
