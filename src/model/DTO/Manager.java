package model.DTO;

import java.util.Objects;

public class Manager {
    protected int id;
    protected String name;
    protected String surname;
    protected String phoneNumber;
    protected String email;
    protected String position;


    public Manager() {
        super();
    }

    public Manager(int id, String name, String surname, String phoneNumber, String email, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.position = position;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return id == manager.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", ime='" + name + '\'' +
                ", prezime='" + surname + '\'' +
                ", pozicija='" + position + '\'' +
                ", brojTelefona='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
