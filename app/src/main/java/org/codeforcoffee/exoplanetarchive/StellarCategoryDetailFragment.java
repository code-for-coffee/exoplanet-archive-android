package org.codeforcoffee.exoplanetarchive;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int arrayPosToTableId = Integer.valueOf(getArguments().getString(ARG_ITEM_ID)) + 1;
            mItem = db.getStellarCategory(arrayPosToTableId);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stellarcategory_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.stellarcategory_detail)).setText(mItem.getDescription());
        }

        return rootView;
    }
}
