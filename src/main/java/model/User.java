package model;

import utils.PasswordUtils;

import java.util.Objects;

public class User {
    private String login;
    private String name;
    private String surname;
    private String password;
    private String salt;

    public User() {}

    public User(String login, String name, String surname, String password, String salt) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.salt = salt;
    }

    public User(String login, String name, String surname, String password) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.salt = PasswordUtils.getSalt();
        this.password = PasswordUtils.generateSecurePassword(password, salt);
    }

    public User(String login, String name, String surname) {
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

    public boolean verifyPassword(String passwordToCheck) {
        return PasswordUtils.verifyUserPassword(passwordToCheck, password, salt);
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

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setSalt(String salt){
        this.salt = salt;
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
