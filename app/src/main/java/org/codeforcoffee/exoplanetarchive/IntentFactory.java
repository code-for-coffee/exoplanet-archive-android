package org.codeforcoffee.exoplanetarchive;

import android.content.Intent;

/**
 * Created by codeforcoffee on 7/12/16.
 */
public class IntentFactory {

    public Intent ShareIntent(String content) {
        Intent mIntent = new Intent();
        mIntent.setAction(Intent.ACTION_SEND);
        mIntent.putExtra(Intent.EXTRA_TEXT, content);
        mIntent.setType("text/plain");
        return mIntent;
    }
}
