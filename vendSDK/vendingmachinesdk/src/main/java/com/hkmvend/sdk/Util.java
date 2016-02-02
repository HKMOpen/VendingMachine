package com.hkmvend.sdk;

import android.support.annotation.Nullable;

import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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


    public String runCipher(final int mode, final String key, String content) {
        String out = "";
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            if (mode == Cipher.ENCRYPT_MODE) {
                //  String text = "Hello World";
                //    String key = "Bar12345Bar12345";
                // 128 bit key
                // Create key and cipher

                // encrypt the text
                cipher.init(mode, aesKey);
                byte[] encrypted = cipher.doFinal(content.getBytes());

                StringBuilder sb = new StringBuilder();
                for (byte b : encrypted) {
                    sb.append((char) b);
                }

                // the encrypted String
                String enc = sb.toString();
                System.out.println("encrypted:" + enc);
                out = sb.toString();
            } else if (mode == Cipher.DECRYPT_MODE) {
                // now convert the string to byte array
                // for decryption
                byte[] bb = new byte[content.length()];
                for (int i = 0; i < content.length(); i++) {
                    bb[i] = (byte) content.charAt(i);
                }

                // decrypt the text
                cipher.init(mode, aesKey);
                String decrypted = new String(cipher.doFinal(bb));
                System.err.println("decrypted:" + decrypted);
                out = decrypted;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;

    }


}
