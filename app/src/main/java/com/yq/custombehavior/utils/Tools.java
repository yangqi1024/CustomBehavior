package com.yq.custombehavior.utils;

import com.yq.custombehavior.MyApplication;

/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
public class Tools {
    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getmContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
