package com.hkm.longmenu;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by hesk on 4/11/2015.
 */
public class menuitem<activityClass extends Activity> {

    private final String menuDisplayName;
    private final int ResIdCompanyLogo;
    private Class<activityClass> classIntent;
    private Bundle theExtraData;

    public menuitem(int logoId, String menuName) {
        ResIdCompanyLogo = logoId;
        menuDisplayName = menuName;
    }

    public menuitem(int logoId, String menuName, Class<activityClass> intentClass) {
        this(logoId, menuName);
        classIntent = intentClass;
    }

    public menuitem(int logoId, String menuName, Class<activityClass> intentClass, Bundle extras) {
        this(logoId, menuName, intentClass);
        theExtraData = extras;
    }

    public int getResIdCompanyLogo() {
        return ResIdCompanyLogo;
    }

    public Class<activityClass> getClassIntent() {
        return classIntent;
    }

    public Bundle getTheExtraData() {
        return theExtraData;
    }

    public String getMenuDisplayName() {
        return menuDisplayName;
    }
}
