package Model.DTO;

import java.util.Objects;

public class KorisnickiNalog {
    protected String ime;
    protected String prezime;
    protected String korisnickoIme;
    protected String sifra;
    protected boolean admin;

    public KorisnickiNalog(){
        super();
    }

    public KorisnickiNalog(String ime, String prezime, String korisnickoIme, String sifra, boolean admin) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.admin = admin;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
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
        KorisnickiNalog that = (KorisnickiNalog) o;
        return admin == that.admin &&
                Objects.equals(ime, that.ime) &&
                Objects.equals(prezime, that.prezime) &&
                Objects.equals(korisnickoIme, that.korisnickoIme) &&
                Objects.equals(sifra, that.sifra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime, prezime, korisnickoIme, sifra, admin);
    }

    @Override
    public String toString() {
        return "KorisnickiNalog{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", sifra='" + sifra + '\'' +
                ", admin=" + admin +
                '}';
    }
}
