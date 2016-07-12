package org.codeforcoffee.exoplanetarchive;

/**
 * Created by codeforcoffee on 7/10/16.
 */
public class Star {

    private int id;
    private int stellarCategoryId;
    private String name;
    private double temperature;

    public Star(int id, int stellarCategoryId, String name, double temperature) {
        this.id = id;
        this.stellarCategoryId = stellarCategoryId;
        this.name = name;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public int getStellarCategoryId() {
        return stellarCategoryId;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }


    @Override
    public String toString() {
        return "Star{" +
                "id=" + id +
                ", stellarCategoryId=" + stellarCategoryId +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
