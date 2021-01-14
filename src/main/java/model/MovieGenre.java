package model;

public enum MovieGenre {
    DRAMA,
    COMEDY,
    COMEDYDRAMA,
    THRILLER;

    @Override
    public String toString() {
        String enumName =  super.toString();
        return enumName.substring(0, 1).toUpperCase() + enumName.substring(1).toLowerCase();
    }
}
