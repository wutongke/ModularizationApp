package cn.easydone.modulea.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.easydone.modulea.R;

/**
 * Created by Chenyc on 2015/6/29.
 */
public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(String info) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.module1_fragment_detail, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        tvInfo.setText(getArguments().getString("info"));
        return view;
    }
}
