package org.codeforcoffee.planetbuilder;

import android.icu.math.BigDecimal;

/**
 * Created by codeforcoffee on 7/6/16.
 */
public class Planet {

    // name
    // radius Double
    // mass BigDecimal
    // planetType

    private int id;
    private String name;
    private double radius;
    private double mass;
    private int categoryId;


//    private String determinePlanetType(BigDecimal jupiterMass) {
//
//
//
//    }
public Planet(int id, String name, double radius, double mass) {
    this.id = id;
    this.name = name;
    this.radius = radius;
    this.mass = mass;
}

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", radius=" + radius +
                ", mass=" + mass +
                ", categoryId=" + categoryId +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getRadius() {
        return radius;
    }

    public Double getMass() {
        return mass;
    }

    public int getCategoryId() {
        return categoryId;
    }


}
