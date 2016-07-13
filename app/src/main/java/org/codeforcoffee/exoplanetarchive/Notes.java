package org.codeforcoffee.exoplanetarchive;

import java.util.ArrayList;

/**
 * Created by codeforcoffee on 7/12/16.
 */
public class Notes extends ArrayList<String> {

    private static Notes mInstance = new Notes();

    public static Notes getInstance() {
        return mInstance ;
    }

    private Notes() {

    }
}
