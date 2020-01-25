package Model.DTO;

import java.util.List;
import java.util.Objects;

public class KontaktOsoba {
    protected String ime;
    protected String prezime;
    protected String brojTelefona;
    protected List<Sponzor> sponzori;

    public KontaktOsoba() {
        super();
    }

    public KontaktOsoba(String ime, String prezime, String brojTelefona, List<Sponzor> sponzori) {
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.sponzori = sponzori;
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

    public List<Sponzor> getSponzori() {
        return sponzori;
    }

    public void setSponzori(List<Sponzor> sponzori) {
        this.sponzori = sponzori;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KontaktOsoba that = (KontaktOsoba) o;
        return Objects.equals(ime, that.ime) &&
                Objects.equals(prezime, that.prezime) &&
                Objects.equals(brojTelefona, that.brojTelefona) &&
                Objects.equals(sponzori, that.sponzori);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime, prezime, brojTelefona, sponzori);
    }

    @Override
    public String toString() {
        return "KontaktOsoba{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", brojTelefona='" + brojTelefona + '\'' +
                ", sponzori=" + sponzori +
                '}';
    }
}
