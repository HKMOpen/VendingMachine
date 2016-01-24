package com.hkmvend.sdk.storage;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by hesk on 19/1/16.
 */
public class MenuEntry extends RealmObject {


    public static final int
            UNCATE = 0,
            DRINKS = 1,
            ALCOHOL = 2,
            MAIN_ENTRIES = 3,
            SEAFOOD_ENTRIES = 4,
            NOODLES_ENTRIES = 5,
            SOUP_ENTRIES = 6,
            SIGNATURE_ENTRIES = 7,
            AFTERNOON_ENTRIES = 8,
            MORNING_ENTRIES = 9,
            HOLIDAY_SPECIAL_ENTRIES = 10,
            MIDDLENIGHT_ENTRIES = 11;

    @IntDef({
            UNCATE,
            DRINKS,
            ALCOHOL,
            MAIN_ENTRIES,
            SEAFOOD_ENTRIES,
            NOODLES_ENTRIES,
            SOUP_ENTRIES,
            SIGNATURE_ENTRIES,
            AFTERNOON_ENTRIES,
            MORNING_ENTRIES,
            HOLIDAY_SPECIAL_ENTRIES,
            MIDDLENIGHT_ENTRIES})

    @Retention(RetentionPolicy.SOURCE)
    public @interface EntryTypes {
    }


    public
    @EntryTypes
    static int orderOf(int val) {
        switch (val) {
            case 1:
                return MenuEntry.DRINKS;
            case 2:
                return MenuEntry.ALCOHOL;
            case 3:
                return MenuEntry.MAIN_ENTRIES;
            case 4:
                return MenuEntry.SEAFOOD_ENTRIES;
            case 5:
                return MenuEntry.NOODLES_ENTRIES;
            case 6:
                return MenuEntry.SOUP_ENTRIES;
            case 7:
                return MenuEntry.SIGNATURE_ENTRIES;
            case 8:
                return MenuEntry.AFTERNOON_ENTRIES;
            case 9:
                return MenuEntry.MORNING_ENTRIES;
            case 10:
                return MenuEntry.HOLIDAY_SPECIAL_ENTRIES;
            case 11:
                return MenuEntry.MIDDLENIGHT_ENTRIES;
        }
        return MenuEntry.UNCATE;
    }

    public
    @EntryTypes
    static int valueOf(String tx) {
        if (tx.equalsIgnoreCase("DRINK")) return MenuEntry.DRINKS;
        if (tx.equalsIgnoreCase("ALCOHOL")) return MenuEntry.ALCOHOL;
        if (tx.equalsIgnoreCase("MAIN_ENTRIES")) return MenuEntry.MAIN_ENTRIES;
        if (tx.equalsIgnoreCase("SEAFOOD_ENTRIES")) return MenuEntry.SEAFOOD_ENTRIES;
        if (tx.equalsIgnoreCase("NOODLES_ENTRIES")) return MenuEntry.NOODLES_ENTRIES;
        if (tx.equalsIgnoreCase("SOUP_ENTRIES")) return MenuEntry.SOUP_ENTRIES;
        if (tx.equalsIgnoreCase("SIGNATURE_ENTRIES")) return MenuEntry.SIGNATURE_ENTRIES;
        if (tx.equalsIgnoreCase("AFTERNOON_ENTRIES")) return MenuEntry.AFTERNOON_ENTRIES;
        if (tx.equalsIgnoreCase("MORNING_ENTRIES")) return MenuEntry.MORNING_ENTRIES;
        if (tx.equalsIgnoreCase("HOLIDAY_SPECIAL_ENTRIES"))
            return MenuEntry.HOLIDAY_SPECIAL_ENTRIES;
        if (tx.equalsIgnoreCase("MIDDLENIGHT_ENTRIES")) return MenuEntry.MIDDLENIGHT_ENTRIES;
        return MenuEntry.UNCATE;
    }

    public static String getDisplayCateName(@EntryTypes int from) {
        switch (from) {
            case DRINKS:
                return "Drinks";
            case ALCOHOL:
                return "Alcohol";
            case MAIN_ENTRIES:
                return "main";
            case SEAFOOD_ENTRIES:
                return "seafood";
            case NOODLES_ENTRIES:
                return "noodle";
            case SOUP_ENTRIES:
                return "soup";
            case SIGNATURE_ENTRIES:
                return "signature";
            case AFTERNOON_ENTRIES:
                return "afternoon";
            case MORNING_ENTRIES:
                return "morning";
            case HOLIDAY_SPECIAL_ENTRIES:
                return "holiday";
            case MIDDLENIGHT_ENTRIES:
                return "mid-night";
            default:
                return "";
        }
    }

    @PrimaryKey
    private int entry_id;

    private String entry_name_chinese;
    private String entry_name_english;
    private String entry_short_form;


    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @EntryTypes
    private int category;

    @Required
    private String date;
    private boolean healthy;
    private int media_type;

    public int getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public String getEntry_name_chinese() {
        return entry_name_chinese;
    }

    public void setEntry_name_chinese(String entry_name_chinese) {
        this.entry_name_chinese = entry_name_chinese;
    }

    public String getEntry_name_english() {
        return entry_name_english;
    }

    public void setEntry_name_english(String entry_name_english) {
        this.entry_name_english = entry_name_english;
    }

    public String getEntry_short_form() {
        return entry_short_form;
    }

    public void setEntry_short_form(String entry_short_form) {
        this.entry_short_form = entry_short_form;
    }

    @EntryTypes
    public int getCategory() {
        return category;
    }

    public void setCategory(@EntryTypes int category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }
}
