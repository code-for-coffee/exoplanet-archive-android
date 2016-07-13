package org.codeforcoffee.exoplanetarchive;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
    private StellarCategory mItem;
    private FloatingActionButton mFab;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    private int mDarkColour;
    private int mDarkMutedColour;
    private int mAccentColour;
    private int mLightColour;
    private int mLightMutedColor;

    private Bitmap mBackgroundImg;
    private Palette.Swatch mSwatch;

    Map<Integer, Integer> imagesMap = new HashMap<Integer, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlanetsDatabaseHelper db = PlanetsDatabaseHelper.getInstance(this);
        setContentView(R.layout.activity_stellarcategory_detail);

        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);

        mParentListIntent = getIntent();

        mCategoryIndex = Integer.parseInt(
                mParentListIntent.getStringExtra(StellarCategoryDetailFragment.ARG_ITEM_ID)) - 1;
        mItem = db.getStellarCategory(mCategoryIndex);

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
                Snackbar.make(view, R.string.snackbar_msg_shared_item, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                IntentFactory factory = new IntentFactory();
                startActivity(factory.ShareIntent(mItem.toString()));

            }
        });

        mImageView = (ImageView) findViewById(R.id.stellarcategory_detail_imageview);

        mImageView.setImageResource(imagesMap.get(mItem.getId()));

        mBackgroundImg = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();

        Palette.generateAsync(mBackgroundImg, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mLightColour = palette.getLightVibrantColor(0x00000);
                mAccentColour = palette.getVibrantColor(0x000000);
                mDarkColour = palette.getDarkVibrantColor(0x000000);
                mDarkMutedColour = palette.getDarkMutedColor(0x00000);
                mLightMutedColor = palette.getLightMutedColor(0x00000);
                mSwatch = palette.getVibrantSwatch();

                if (mSwatch != null) {
                    //titleView.setBackgroundColor(swatch.getRgb());
                    //titleView.setTextColor(swatch.getTitleTextColor());
                    mFab.setRippleColor(mAccentColour);
                    mFab.setBackgroundTintList(ColorStateList.valueOf(mAccentColour));
                    mToolbar.setTitleTextColor(mLightColour);
                    getWindow().setStatusBarColor(mDarkColour);
                    //mToolbar.setBackgroundColor(mLightMutedColor);
                    //  mutedColor = palette.getMutedColor(R.attr.colorPrimary);
//                    mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getVibrantColor(mDarkMutedColour));
//                    mCollapsingToolbarLayout.setContentScrimColor(palette.getVibrantColor(mDarkMutedColour));
                    mCollapsingToolbarLayout.setBackgroundColor(palette.getVibrantColor(mDarkMutedColour));
                    mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getVibrantColor(mDarkMutedColour));
                    mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getVibrantColor(mDarkMutedColour));
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
            arguments.putString("CLASS", mItem.getSpectralClass());
            arguments.putString("NAME", mItem.getName());
            arguments.putDouble("MIN_TEMP", mItem.getMinTemp());
            arguments.putDouble("MAX_TEMP", mItem.getMaxTemp());
            arguments.putString("DESC", mItem.getDescription());
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
