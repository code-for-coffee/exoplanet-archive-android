package org.codeforcoffee.planetbuilder;

import android.icu.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codeforcoffee on 7/6/16.
 */
public class PlanetTypeLibrary {

    public class PlanetCategory {
        public String name;
        public Double minMass;
        public Double maxMass;
    }

    public ArrayList<PlanetCategory> mPlanetCategories = new ArrayList<>();

    public PlanetTypeLibrary() {
        mPlanetCategories.add(makePlanetCategory("Asteroidian", 0.0, 0.00001));
        mPlanetCategories.add(makePlanetCategory("Mercurian", 0.00011, 0.1));
        mPlanetCategories.add(makePlanetCategory("Subterran", 0.10001, 0.5));
    }

    public PlanetCategory makePlanetCategory(String name, Double min, Double max) {

        PlanetCategory tempPlanet = new PlanetCategory();
        tempPlanet.name = name;
        tempPlanet.minMass = min;
        tempPlanet.maxMass = max;
        return tempPlanet;

    }
}
