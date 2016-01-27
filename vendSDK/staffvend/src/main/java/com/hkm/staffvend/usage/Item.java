package com.hkm.staffvend.usage;

import java.io.Serializable;

/**
 * Created by hesk on 27/1/16.
 */


public class Item implements Serializable {

    private static final long serialVersionUID = -6882745111884490060L;

    private int id;
    private String title;
    private String subtitle;

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof Item) {
            Item inItem = (Item) inObject;
            return this.id == inItem.id;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String toString() {
        return title;
    }
}