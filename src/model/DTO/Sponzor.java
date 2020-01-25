package model.DTO;

import java.util.List;
import java.util.Objects;

public class Sponzor {
    protected int id;
    protected String brojTelefona;
    protected String email;
    protected String adresa;
    protected String ime;
    protected String vrsta;
    protected String jmbjib;
    protected List<KontaktOsoba> kontaktOsobe;
    protected List<Uplata> uplate;

    public Sponzor() {
        super();
    }

    public Sponzor(int id, String brojTelefona, String email, String adresa, String ime, String vrsta, String jmbjib, List<KontaktOsoba> kontaktOsobe, List<Uplata> uplate) {
        this.id = id;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.adresa = adresa;
        this.ime = ime;
        this.vrsta = vrsta;
        this.jmbjib = jmbjib;
        this.kontaktOsobe = kontaktOsobe;
        this.uplate = uplate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getJmbjib() {
        return jmbjib;
    }

    public void setJmbjib(String jmbjib) {
        this.jmbjib = jmbjib;
    }

    public List<KontaktOsoba> getKontaktOsobe() {
        return kontaktOsobe;
    }

    public void setKontaktOsobe(List<KontaktOsoba> kontaktOsobe) {
        this.kontaktOsobe = kontaktOsobe;
    }

    public List<Uplata> getUplate() {
        return uplate;
    }

    public void setUplate(List<Uplata> uplate) {
        this.uplate = uplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sponzor sponzor = (Sponzor) o;
        return id == sponzor.id &&
                Objects.equals(brojTelefona, sponzor.brojTelefona) &&
                Objects.equals(email, sponzor.email) &&
                Objects.equals(adresa, sponzor.adresa) &&
                Objects.equals(ime, sponzor.ime) &&
                Objects.equals(vrsta, sponzor.vrsta) &&
                Objects.equals(jmbjib, sponzor.jmbjib) &&
                Objects.equals(kontaktOsobe, sponzor.kontaktOsobe) &&
                Objects.equals(uplate, sponzor.uplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brojTelefona, email, adresa, ime, vrsta, jmbjib, kontaktOsobe, uplate);
    }

    @Override
    public String toString() {
        return "Sponzor{" +
                "id=" + id +
                ", brojTelefona='" + brojTelefona + '\'' +
                ", email='" + email + '\'' +
                ", adresa='" + adresa + '\'' +
                ", ime='" + ime + '\'' +
                ", vrsta='" + vrsta + '\'' +
                ", jmbjib='" + jmbjib + '\'' +
                ", kontaktOsobe=" + kontaktOsobe +
                ", uplate=" + uplate +
                '}';
    }
}
