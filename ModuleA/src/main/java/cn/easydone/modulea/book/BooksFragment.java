package cn.easydone.modulea.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.linked.erfli.library.utils.Utils;
import com.linked.erfli.library.utils.operators.AppObservable;
import com.linked.erfli.library.widget.RecyclerItemClickListener;

import java.util.HashMap;
import java.util.Map;

import cn.easydone.modulea.R;
import cn.easydone.modulea.Utils.BookApiHttp;
import cn.easydone.modulea.module.Book;
import cn.easydone.modulea.module.BookResponse;
import rx.functions.Action1;

/**
 * Created by Chenyc on 15/7/1.
 */
public class BooksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ProgressBar mProgressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.module1_fragment_books, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mAdapter = new MyAdapter(this, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
        return view;
    }

    private void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "战争");
        params.put("start", "0");
        params.put("end", "50");
        AppObservable.bindActivity(getActivity(), BookApiHttp.http.getBooks(params)).subscribe(new Action1<BookResponse>() {
            @Override
            public void call(BookResponse bookResponse) {
                mProgressBar.setVisibility(View.GONE);
                mAdapter.updateItems(bookResponse.getBooks(), false);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mProgressBar.setVisibility(View.GONE);
                Utils.showShortToast(getContext(), "network error");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Book book = mAdapter.getBook(position);
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra("book", book);
            startActivity(intent);
        }
    };
}
