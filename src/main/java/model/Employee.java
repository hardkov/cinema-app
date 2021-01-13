package model;

import java.util.Objects;

public class Employee extends User {
    private Permission permissions;

    public Employee() {}

    public Employee(String login, String name, String surname, Permission permissions){
        super(login, name, surname);
        this.permissions = permissions;
    }

    public Permission getPermissions() {
        return permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return permissions == employee.permissions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissions);
    }

    @Override
    public String toString(){
        return super.toString() + String.format(", Permissions: %s", permissions.toString());
    }
}

