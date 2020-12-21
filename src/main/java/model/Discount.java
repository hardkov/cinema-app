package model;

public class Discount {
    private String name;
    private float value;

    public Discount() {}

    public Discount(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }
}
