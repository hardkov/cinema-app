package model;

public class Employee extends User{
    private Permission permissions;

    public Employee(String login, String name, String surname, Permission permissions){
        super(login, name, surname);
        this.permissions = permissions;
    }

    public Permission getPermissions() {
        return permissions;
    }
}

