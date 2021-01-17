package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;
        Discount discount = (Discount) o;
        return Float.compare(discount.value, value) == 0 &&
                name.equals(discount.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return name;
    }
}
