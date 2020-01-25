package Model.DTO;

import java.util.List;
import java.util.Objects;

public class Osoba {
    protected int id;
    protected String ime;
    protected String prezime;
    protected String brojTelefona;
    protected String email;
    protected String adresa;
    protected String jmb;
    protected String brojLicence;
    protected List<LjekarskiPregled> ljekarskiPregledi;
    protected List<Kazna> kazne;
    protected List<Oprema> zaduzenaOprema;


    public Osoba() {
        super();
    }

    public Osoba(int id, String ime, String prezime, String brojTelefona, String email, String adresa, String jmb, String brojLicence, List<LjekarskiPregled> ljekarskiPregledi, List<Kazna> kazne, List<Oprema> zaduzenaOprema) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.adresa = adresa;
        this.jmb = jmb;
        this.brojLicence = brojLicence;
        this.ljekarskiPregledi = ljekarskiPregledi;
        this.kazne = kazne;
        this.zaduzenaOprema = zaduzenaOprema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getJmb() {
        return jmb;
    }

    public void setJmb(String jmb) {
        this.jmb = jmb;
    }

    public String getBrojLicence() {
        return brojLicence;
    }

    public void setBrojLicence(String brojLicence) {
        this.brojLicence = brojLicence;
    }

    public List<LjekarskiPregled> getLjekarskiPregledi() {
        return ljekarskiPregledi;
    }

    public void setLjekarskiPregledi(List<LjekarskiPregled> ljekarskiPregledi) {
        this.ljekarskiPregledi = ljekarskiPregledi;
    }

    public List<Kazna> getKazne() {
        return kazne;
    }

    public void setKazne(List<Kazna> kazne) {
        this.kazne = kazne;
    }

    public List<Oprema> getZaduzenaOprema() {
        return zaduzenaOprema;
    }

    public void setZaduzenaOprema(List<Oprema> zaduzenaOprema) {
        this.zaduzenaOprema = zaduzenaOprema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Osoba osoba = (Osoba) o;
        return id == osoba.id &&
                Objects.equals(ime, osoba.ime) &&
                Objects.equals(prezime, osoba.prezime) &&
                Objects.equals(brojTelefona, osoba.brojTelefona) &&
                Objects.equals(email, osoba.email) &&
                Objects.equals(adresa, osoba.adresa) &&
                Objects.equals(jmb, osoba.jmb) &&
                Objects.equals(brojLicence, osoba.brojLicence) &&
                Objects.equals(ljekarskiPregledi, osoba.ljekarskiPregledi) &&
                Objects.equals(kazne, osoba.kazne) &&
                Objects.equals(zaduzenaOprema, osoba.zaduzenaOprema);
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime, brojTelefona, email, adresa, jmb, brojLicence, ljekarskiPregledi, kazne, zaduzenaOprema);
    }

    @Override
    public String toString() {
        return "Osoba{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", brojTelefona='" + brojTelefona + '\'' +
                ", email='" + email + '\'' +
                ", adresa='" + adresa + '\'' +
                ", jmb='" + jmb + '\'' +
                ", brojLicence='" + brojLicence + '\'' +
                ", ljekarskiPregledi=" + ljekarskiPregledi +
                ", kazne=" + kazne +
                ", zaduzenaOprema=" + zaduzenaOprema +
                '}';
    }
}
