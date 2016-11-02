package com.linked.erfli.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by erfli on 11/1/16.
 */

public class Utils {
    private static Toast toast;

    public static void showShortToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static String encodeUrlParam(String param){
        return param.replace("/","%%");
    }

    public static String decodeUrlParam(String param){
        return param.replace("%%","/");
    }
}
