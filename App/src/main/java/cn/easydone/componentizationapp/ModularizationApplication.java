package cn.easydone.componentizationapp;

import android.app.Application;
import android.util.Log;

import com.github.mzule.activityrouter.annotation.Modules;
import com.linked.erfli.library.utils.EventPool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by erfli on 11/1/16.
 */
@Modules({"app", "moduleA", "moduleB"})
public class ModularizationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logActivityCreate(EventPool.ActivityNotify activityNotify) {
        Log.d("ActivityCreate", activityNotify.activityName);
    }
}
