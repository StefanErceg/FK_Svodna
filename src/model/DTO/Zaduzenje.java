package model.DTO;

import java.util.Objects;

public class Zaduzenje {
    protected int id;
    protected String opis;
    protected boolean odradjeno;

    public Zaduzenje() {
        super();
    }

    public Zaduzenje(int id, String opis, boolean odradjeno) {
        this.id = id;
        this.opis = opis;
        this.odradjeno = odradjeno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == zaduzenje.id &&
                odradjeno == zaduzenje.odradjeno &&
                Objects.equals(opis, zaduzenje.opis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opis, odradjeno);
    }

    @Override
    public String toString() {
        return "Zaduzenje{" +
                "id=" + id +
                ", opis='" + opis + '\'' +
                ", odradjeno=" + odradjeno +
                '}';
    }
}
