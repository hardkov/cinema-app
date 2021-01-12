package model;

public enum MovieGenre {
    DRAMAT,
    KOMEDIA,
    KOMEDIODRAMAT,
    SENSACJA;

    @Override
    public String toString() {
        String enumName =  super.toString();
        return enumName.substring(0, 1).toUpperCase() + enumName.substring(1).toLowerCase();
    }
}
