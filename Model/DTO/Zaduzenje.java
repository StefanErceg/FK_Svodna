package Model.DTO;

import java.util.Objects;

public class Zaduzenje {

    protected String opis;
    protected boolean odradjeno;

    public Zaduzenje() {
        super();
    }

    public Zaduzenje(String opis, boolean odradjeno) {
        this.opis = opis;
        this.odradjeno = odradjeno;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isOdradjeno() {
        return odradjeno;
    }

    public void setOdradjeno(boolean odradjeno) {
        this.odradjeno = odradjeno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zaduzenje zaduzenje = (Zaduzenje) o;
        return odradjeno == zaduzenje.odradjeno &&
                Objects.equals(opis, zaduzenje.opis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opis, odradjeno);
    }

    @Override
    public String toString() {
        return "Zaduzenje{" +
                "opis='" + opis + '\'' +
                ", odradjeno=" + odradjeno +
                '}';
    }
}
