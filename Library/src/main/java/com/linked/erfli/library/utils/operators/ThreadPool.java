package com.linked.erfli.library.utils.operators;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by erfli on 6/14/16.
 */
public class ThreadPool {
    public static ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(6);
    public static void clearExecut(){
        executor.getQueue().clear();
    }
}
