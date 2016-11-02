package cn.easydone.componentizationapp;

import android.app.Application;

import com.github.mzule.activityrouter.annotation.Modules;

/**
 * Created by erfli on 11/1/16.
 */
@Modules({"app", "moduleA", "moduleB"})
public class ModularizationApplication extends Application {
}
