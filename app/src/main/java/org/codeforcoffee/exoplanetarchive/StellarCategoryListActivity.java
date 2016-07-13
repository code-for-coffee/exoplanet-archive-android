package org.codeforcoffee.exoplanetarchive;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * An activity representing a list of Stellar Categories. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StellarCategoryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StellarCategoryListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<StellarCategory> mCategoryList;
    private boolean mFromSearch = false;
    private String mSearchQuery;
    private RecyclerView recyclerView;
    private SimpleItemRecyclerViewAdapter mRecyclerAdapter;

    private void handleIntent(Intent intent) {
        PlanetsDatabaseHelper db = PlanetsDatabaseHelper.getInstance(this);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mSearchQuery = intent.getStringExtra(SearchManager.QUERY);
            mFromSearch = true;
            mCategoryList = db.searchAllStellarCategories(mSearchQuery);
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mCategoryList));
        } else {
            mFromSearch = false;
            mCategoryList = db.getAllStellarCategories();
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mCategoryList));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PlanetsDatabaseHelper db = PlanetsDatabaseHelper.getInstance(this);

        if (mFromSearch == true) {
            mCategoryList = db.searchAllStellarCategories(mSearchQuery);
        } else {
            mCategoryList = db.getAllStellarCategories();
        }

        setContentView(R.layout.activity_stellarcategory_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = (RecyclerView) findViewById(R.id.stellarcategory_list);
        assert recyclerView != null;
        mRecyclerAdapter = new SimpleItemRecyclerViewAdapter(mCategoryList);
        recyclerView.setAdapter(mRecyclerAdapter);


        if (findViewById(R.id.stellarcategory_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanetsDatabaseHelper db = PlanetsDatabaseHelper.getInstance(getApplicationContext());
                mCategoryList = db.getAllStellarCategories();
                recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mCategoryList));
                Snackbar.make(view, R.string.snackbar_msg_reloaded_sc, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                IntentFactory factory = new IntentFactory();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.stellarcategories_list_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.sc_search);

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                handleIntent(new Intent());
                return true;
            }
        });

        SearchView searchView = (SearchView) menu.findItem(R.id.sc_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<StellarCategory> mValues;

        public SimpleItemRecyclerViewAdapter(List<StellarCategory> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stellarcategory_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getSpectralClass());
            holder.mContentView.setText(mValues.get(position).getName());
            final int pos = position;

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(StellarCategoryDetailFragment.ARG_ITEM_ID, String.valueOf(mValues.get(pos).getId()));
                        StellarCategoryDetailFragment fragment = new StellarCategoryDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.stellarcategory_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StellarCategoryDetailActivity.class);
                        intent.putExtra(StellarCategoryDetailFragment.ARG_ITEM_ID, String.valueOf(mValues.get(pos).getId()));

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public StellarCategory mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
