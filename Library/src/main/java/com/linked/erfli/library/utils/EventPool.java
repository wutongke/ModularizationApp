package com.linked.erfli.library.utils;

/**
 * Created by erfli on 11/7/16.
 */

public class EventPool {
    public static abstract class BaseEvent {

    }

    public static class ActivityNotify extends BaseEvent {
        public String activityName;

        public ActivityNotify(String activityName) {
            this.activityName = activityName;
        }
    }
}
