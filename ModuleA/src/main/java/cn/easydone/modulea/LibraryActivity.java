package cn.easydone.modulea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.easydone.modulea.book.BooksFragment;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BooksFragment fragment = new BooksFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
    }
}
