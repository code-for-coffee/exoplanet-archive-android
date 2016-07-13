package org.codeforcoffee.exoplanetarchive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NotesScrollingActivity extends AppCompatActivity {

    private ListView mListView;
    private Notes mNotes;
    private ArrayAdapter<Notes> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes_scrolling);

        mListView = (ListView) findViewById(R.id.notes_list_view);
        mNotes = Notes.getInstance();
        mAdapter = new ArrayAdapter<Notes>(this, android.R.layout.simple_list_item_1);
        mAdapter.addAll(mNotes);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotes.clear();
                mAdapter.notifyDataSetChanged();
                Snackbar.make(view, R.string.snackbar_notes_delete, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
}
