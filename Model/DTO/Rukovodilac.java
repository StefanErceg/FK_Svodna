package Model.DTO;

import java.util.Objects;

public class Rukovodilac {
    protected int id;
    protected String ime;
    protected String prezime;
    protected String pozicija;
    protected String brojTelefona;
    protected String email;


    public Rukovodilac() {
        super();
    }

    public Rukovodilac(int id, String ime, String prezime, String pozicija, String brojTelefona, String email) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.pozicija = pozicija;
        this.brojTelefona = brojTelefona;
        this.email = email;
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

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rukovodilac that = (Rukovodilac) o;
        return id == that.id &&
                Objects.equals(ime, that.ime) &&
                Objects.equals(prezime, that.prezime) &&
                Objects.equals(pozicija, that.pozicija) &&
                Objects.equals(brojTelefona, that.brojTelefona) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime, pozicija, brojTelefona, email);
    }

    @Override
    public String toString() {
        return "Rukovodilac{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", pozicija='" + pozicija + '\'' +
                ", brojTelefona='" + brojTelefona + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
