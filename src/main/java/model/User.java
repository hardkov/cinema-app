package model;

import java.util.Objects;

public class User {
    private String login;
    private String name;
    private String surname;

    public User() {}

    public User(String login, String name, String surname){
        this.login = login;
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return login.equals(user.login) &&
                name.equals(user.name) &&
                surname.equals(user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, surname);
    }

    @Override
    public String toString(){
        return String.format("Login: %s, Name: %s, Surname: %s", login, name, surname);
    }
}
