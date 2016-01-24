package com.hkmvend.sdk;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zJJ on 1/23/2016.
 */
public class Util {

    public static String LinkConfirmer(@Nullable String text) {
        if (text == null || text.isEmpty()) return "";
        final String get_link = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
        Pattern patternc = Pattern.compile(get_link);
        Matcher fm = patternc.matcher(text);
        if (fm.find()) {
            // Log.d("hackResult", fm.group(0));
            String k_start = fm.group(0);
            if (!k_start.isEmpty()) {
                return k_start;
            }
        }
        return "";
    }
}
