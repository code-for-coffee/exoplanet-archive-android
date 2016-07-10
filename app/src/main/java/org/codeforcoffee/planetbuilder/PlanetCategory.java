package org.codeforcoffee.planetbuilder;

/**
 * Created by codeforcoffee on 7/10/16.
 */
public class PlanetCategory {

    private int id;
    private double minMass;
    private double maxMass;
    private String name;
    private String description;

    public PlanetCategory(int id, double minMass, double maxMass, String name, String description) {
        this.id = id;
        this.minMass = minMass;
        this.maxMass = maxMass;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getMinMass() {
        return minMass;
    }

    public double getMaxMass() {
        return maxMass;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PlanetCategory{" +
                "id=" + id +
                ", minMass=" + minMass +
                ", maxMass=" + maxMass +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
