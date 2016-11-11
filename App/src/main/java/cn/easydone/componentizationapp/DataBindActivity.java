package cn.easydone.componentizationapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mzule.activityrouter.annotation.Router;

import cn.easydone.componentizationapp.databinding.ActivityDataBindBinding;
import cn.easydone.componentizationapp.model.User;
@Router("databinding")
public class DataBindActivity extends AppCompatActivity {
    String[] Names = {"jack", "hhh", "jack", "hhh", "jack", "hhh", "jack", "hhh", "jack", "hhh"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindBinding bindBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind);
        final User user = new User();
        bindBinding.setUser(user);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Names.length; i++) {
                    user.name.set(Names[i]);
                    user.age.set(i * 2 + 16);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
