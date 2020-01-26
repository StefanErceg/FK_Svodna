package model.DTO;

import java.util.Objects;

public class Sponsor {
    protected int id;
    protected String name;
    protected String address;
    protected String email;
    protected String phoneNumber;
    protected String kind;
    protected String jmbJib;

    public Sponsor() {
        super();
    }

    public Sponsor(int id, String name, String address, String email, String phoneNumber, String kind, String jmbJib) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.kind = kind;
        this.jmbJib = jmbJib;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getJmbjib() {
        return jmbJib;
    }

    public void setJmbjib(String jmbJib) {
        this.jmbJib = jmbJib;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sponsor sponsor = (Sponsor) o;
        return id == sponsor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone number='" + phoneNumber + '\'' +
                ", kind='" + kind + '\'' +
                ", jmb/jib='" + jmbJib + '\'' +
                '}';
    }
}
