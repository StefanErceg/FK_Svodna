package Model.DTO;

import java.util.Objects;

public class Tim {
    protected String naziv;


    public Tim(String naziv) {
        this.naziv = naziv;
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
        return Objects.equals(naziv, tim.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    @Override
    public String toString() {
        return "Tim{" +
                "naziv='" + naziv + '\'' +
                '}';
    }
}
