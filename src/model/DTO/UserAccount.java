package model.DTO;

import java.util.Objects;

public class UserAccount {
    protected int id;
    protected String name;
    protected String surname;
    protected String username;
    protected String password;
    protected boolean admin;

    public UserAccount() {
        super();
    }

    public UserAccount(int id, String name, String surname, String username, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", ime='" + name + '\'' +
                ", prezime='" + surname + '\'' +
                ", korisnickoIme='" + username + '\'' +
                ", sifra='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
