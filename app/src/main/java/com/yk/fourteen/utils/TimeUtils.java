package com.yk.fourteen.utils;

import java.util.Date;

/**
 * Created by YK on 2017/5/8.
 */

public class TimeUtils {
    public static String getTime(){
        return String.valueOf(new Date().getTime());
    }
}
