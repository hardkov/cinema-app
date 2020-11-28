package model;

public class Employee extends User{
    private Permission permissions;

    public Employee(String name, String surname, Permission permissions){
        super(name, surname);
        this.permissions = permissions;
    }

    public Permission getPermissions() {
        return permissions;
    }
}

