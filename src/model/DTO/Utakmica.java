package model.DTO;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Utakmica {
    protected int id;
    protected String protivnickiTim;
    protected Date datum;
    protected String rezultat;
    protected List<Zaduzenje> zaduzenja;
    protected List<OsobaTim> postava;

    public Utakmica() {
        super();
    }

    public Utakmica(int id, String protivnickiTim, Date datum, String rezultat, List<Zaduzenje> zaduzenja, List<OsobaTim> postava) {
        this.id = id;
        this.protivnickiTim = protivnickiTim;
        this.datum = datum;
        this.rezultat = rezultat;
        this.zaduzenja = zaduzenja;
        this.postava = postava;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProtivnickiTim() {
        return protivnickiTim;
    }

    public void setProtivnickiTim(String protivnickiTim) {
        this.protivnickiTim = protivnickiTim;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    public List<Zaduzenje> getZaduzenja() {
        return zaduzenja;
    }

    public void setZaduzenja(List<Zaduzenje> zaduzenja) {
        this.zaduzenja = zaduzenja;
    }

    public List<OsobaTim> getPostava() {
        return postava;
    }

    public void setPostava(List<OsobaTim> postava) {
        this.postava = postava;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utakmica utakmica = (Utakmica) o;
        return id == utakmica.id &&
                Objects.equals(protivnickiTim, utakmica.protivnickiTim) &&
                Objects.equals(datum, utakmica.datum) &&
                Objects.equals(rezultat, utakmica.rezultat) &&
                Objects.equals(zaduzenja, utakmica.zaduzenja) &&
                Objects.equals(postava, utakmica.postava);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, protivnickiTim, datum, rezultat, zaduzenja, postava);
    }

    @Override
    public String toString() {
        return "Utakmica{" +
                "id=" + id +
                ", protivnickiTim='" + protivnickiTim + '\'' +
                ", datum=" + datum +
                ", rezultat='" + rezultat + '\'' +
                ", zaduzenja=" + zaduzenja +
                ", postava=" + postava +
                '}';
    }
}
