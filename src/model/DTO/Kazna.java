package model.DTO;

import java.sql.Date;
import java.util.Objects;

public class Kazna {
    protected int id;
    protected Date datumKazne;
    protected double iznosKazne;
    protected int brojMecevaSuspenzije;
    protected String Opis;
    protected Karton karton;

    public Kazna() {
        super();
    }
    public Kazna(int id, Date datumKazne, double iznosKazne, int brojMecevaSuspenzije, String opis, Karton karton) {
        this.id = id;
        this.datumKazne = datumKazne;
        this.iznosKazne = iznosKazne;
        this.brojMecevaSuspenzije = brojMecevaSuspenzije;
        Opis = opis;
        this.karton = karton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatumKazne() {
        return datumKazne;
    }

    public void setDatumKazne(Date datumKazne) {
        this.datumKazne = datumKazne;
    }

    public double getIznosKazne() {
        return iznosKazne;
    }

    public void setIznosKazne(double iznosKazne) {
        this.iznosKazne = iznosKazne;
    }

    public int getBrojMecevaSuspenzije() {
        return brojMecevaSuspenzije;
    }

    public void setBrojMecevaSuspenzije(int brojMecevaSuspenzije) {
        this.brojMecevaSuspenzije = brojMecevaSuspenzije;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public Karton getKarton() {
        return karton;
    }

    public void setKarton(Karton karton) {
        this.karton = karton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kazna kazna = (Kazna) o;
        return id == kazna.id &&
                Double.compare(kazna.iznosKazne, iznosKazne) == 0 &&
                brojMecevaSuspenzije == kazna.brojMecevaSuspenzije &&
                Objects.equals(datumKazne, kazna.datumKazne) &&
                Objects.equals(Opis, kazna.Opis) &&
                karton == kazna.karton;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datumKazne, iznosKazne, brojMecevaSuspenzije, Opis, karton);
    }

    @Override
    public String toString() {
        return "Kazna{" +
                "id=" + id +
                ", datumKazne=" + datumKazne +
                ", iznosKazne=" + iznosKazne +
                ", brojMecevaSuspenzije=" + brojMecevaSuspenzije +
                ", Opis='" + Opis + '\'' +
                ", karton=" + karton +
                '}';
    }
}

