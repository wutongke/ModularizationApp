package cn.easydone.componentizationapp.model;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by erfli on 11/11/16.
 */

public class User  {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
}
