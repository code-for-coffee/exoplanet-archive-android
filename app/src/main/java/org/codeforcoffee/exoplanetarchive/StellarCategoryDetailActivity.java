package org.codeforcoffee.exoplanetarchive;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity representing a single StellarCategory detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StellarCategoryListActivity}.
 */
public class StellarCategoryDetailActivity extends AppCompatActivity {

    private Intent mParentListIntent;
    private int mCategoryIndex;
    private ImageView mImageView;
    private FloatingActionButton mFab;

    private Bitmap mBackgroundImg;
    private Palette.Swatch mSwatch;

    Map<Integer, Integer> imagesMap = new HashMap<Integer, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stellarcategory_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        mParentListIntent = getIntent();
        mCategoryIndex = Integer.parseInt(
                mParentListIntent.getStringExtra(StellarCategoryDetailFragment.ARG_ITEM_ID)) + 1;


        imagesMap.put(1, R.drawable.stellar_cat_1);
        imagesMap.put(2, R.drawable.stellar_cat_2);
        imagesMap.put(3, R.drawable.stellar_cat_3);
        imagesMap.put(4, R.drawable.stellar_cat_4);
        imagesMap.put(5, R.drawable.stellar_cat_5);
        imagesMap.put(6, R.drawable.stellar_cat_6);
        imagesMap.put(7, R.drawable.stellar_cat_7);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mImageView = (ImageView) findViewById(R.id.stellarcategory_detail_imageview);

        mImageView.setImageResource(imagesMap.get(mCategoryIndex));

        mBackgroundImg = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();

        Palette.generateAsync(mBackgroundImg, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int vibrant = palette.getVibrantColor(0x000000);
                mSwatch = palette.getVibrantSwatch();

                if (mSwatch != null) {
                    //titleView.setBackgroundColor(swatch.getRgb());
                    //titleView.setTextColor(swatch.getTitleTextColor());
                    mFab.setRippleColor(vibrant);
                    mFab.setBackgroundTintList(ColorStateList.valueOf(vibrant));
                }
            }
        });




        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(StellarCategoryDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StellarCategoryDetailFragment.ARG_ITEM_ID));
            StellarCategoryDetailFragment fragment = new StellarCategoryDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stellarcategory_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, StellarCategoryListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
