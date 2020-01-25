package model.DTO;

import java.util.Objects;

public class Tim {
    protected int id;
    protected String naziv;

    public Tim() {
        super();
    }

    public Tim(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tim tim = (Tim) o;
        return id == tim.id &&
                Objects.equals(naziv, tim.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naziv);
    }

    @Override
    public String toString() {
        return "Tim{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
