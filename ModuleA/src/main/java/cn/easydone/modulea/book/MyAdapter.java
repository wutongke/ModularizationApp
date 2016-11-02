package cn.easydone.modulea.book;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.easydone.modulea.R;
import cn.easydone.modulea.module.Book;

/**
 * Created by erfli on 6/15/16.
 */
public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Book> mBooks = new ArrayList<Book>();
    private final TypedValue mTypedValue = new TypedValue();

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(BooksFragment booksFragment, Context context) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
    }

    public void updateItems(List<Book> books, boolean animated) {
        mBooks.addAll(books);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mBooks.clear();
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module1_book_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mBooks.get(position);
        holder.tvTitle.setText(book.getTitle());
        String desc = "作者: " + (book.getAuthor().length > 0 ? book.getAuthor()[0] : "") + "\n副标题: " + book.getSubtitle()
                + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
        holder.tvDesc.setText(desc);
        Glide.with(holder.ivBook.getContext())
                .load(book.getImage())
                .fitCenter()
                .into(holder.ivBook);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    public Book getBook(int pos) {
        return mBooks.get(pos);
    }
}
