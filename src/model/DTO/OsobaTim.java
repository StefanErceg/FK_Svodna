package model.DTO;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class OsobaTim {
    protected int id;
    protected Date datum_od;
    protected Date datum_do;
    protected String uloga;
    protected String pozicijaIraca;
    protected Osoba osoba;
    protected Tim tim;
    protected List<Utakmica> utakmice;

    public OsobaTim() {
        super();
    }

    public OsobaTim(int id, Date datum_od, Date datum_do, String uloga, String pozicijaIraca, Osoba osoba, Tim tim, List<Utakmica> utakmice) {
        this.id = id;
        this.datum_od = datum_od;
        this.datum_do = datum_do;
        this.uloga = uloga;
        this.pozicijaIraca = pozicijaIraca;
        this.osoba = osoba;
        this.tim = tim;
        this.utakmice = utakmice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum_od() {
        return datum_od;
    }

    public void setDatum_od(Date datum_od) {
        this.datum_od = datum_od;
    }

    public Date getDatum_do() {
        return datum_do;
    }

    public void setDatum_do(Date datum_do) {
        this.datum_do = datum_do;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public String getPozicijaIraca() {
        return pozicijaIraca;
    }

    public void setPozicijaIraca(String pozicijaIraca) {
        this.pozicijaIraca = pozicijaIraca;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public Tim getTim() {
        return tim;
    }

    public void setTim(Tim tim) {
        this.tim = tim;
    }

    public List<Utakmica> getUtakmice() {
        return utakmice;
    }

    public void setUtakmice(List<Utakmica> utakmice) {
        this.utakmice = utakmice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OsobaTim osobaTim = (OsobaTim) o;
        return id == osobaTim.id &&
                Objects.equals(datum_od, osobaTim.datum_od) &&
                Objects.equals(datum_do, osobaTim.datum_do) &&
                Objects.equals(uloga, osobaTim.uloga) &&
                Objects.equals(pozicijaIraca, osobaTim.pozicijaIraca) &&
                Objects.equals(osoba, osobaTim.osoba) &&
                Objects.equals(tim, osobaTim.tim) &&
                Objects.equals(utakmice, osobaTim.utakmice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datum_od, datum_do, uloga, pozicijaIraca, osoba, tim, utakmice);
    }

    @Override
    public String toString() {
        return "OsobaTim{" +
                "id=" + id +
                ", datum_od=" + datum_od +
                ", datum_do=" + datum_do +
                ", uloga='" + uloga + '\'' +
                ", pozicijaIraca='" + pozicijaIraca + '\'' +
                ", osoba=" + osoba +
                ", tim=" + tim +
                ", utakmice=" + utakmice +
                '}';
    }
}
