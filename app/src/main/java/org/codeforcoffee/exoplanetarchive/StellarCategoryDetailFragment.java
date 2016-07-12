package org.codeforcoffee.exoplanetarchive;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A fragment representing a single StellarCategory detail screen.
 * This fragment is either contained in a {@link StellarCategoryListActivity}
 * in two-pane mode (on tablets) or a {@link StellarCategoryDetailActivity}
 * on handsets.
 */
public class StellarCategoryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "stellar_category_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private StellarCategory mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StellarCategoryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PlanetsDatabaseHelper db = PlanetsDatabaseHelper.getInstance(getContext());

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            int arrayPosToTableId = Integer.valueOf(getArguments().getString(ARG_ITEM_ID)) + 1;
            mItem = db.getStellarCategory(arrayPosToTableId);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
                //appBarLayout.setBackgroundColor(getArguments().getInt("TEXT_COLOUR"));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ImageView img = (ImageView) container.findViewById(R.id.stellarcategory_detail_imageview);

        //Bitmap mBackgroundImg = ((BitmapDrawable)img.getDrawable()).getBitmap();

        final View rootView = inflater.inflate(R.layout.stellarcategory_detail, container, false);

//        Palette.generateAsync(mBackgroundImg, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                int darkVibrantColor = palette.getDarkVibrantColor(0x000000);
//                TextView labelTemp = (TextView) rootView.findViewById(R.id.stellarcategory_temprange);
//                //labelTemp.setTextColor(darkVibrantColor);
//            }
//        });

        if (mItem != null) {
            StringBuilder temperature = new StringBuilder();
            temperature.append("As cold as  ");
            temperature.append(String.valueOf(mItem.getMinTemp()));
            temperature.append("K and upwards to ");
            temperature.append(String.valueOf(mItem.getMaxTemp()));
            temperature.append("K hot");
            ((TextView) rootView.findViewById(R.id.stellarcategory_detail)).setText(mItem.getDescription());
            ((TextView) rootView.findViewById(R.id.stellarcategory_temprange)).setText(temperature.toString());
            ((TextView) rootView.findViewById(R.id.stellarcategory_classname)).setText(mItem.getSpectralClass());
        }

        return rootView;
    }
}
