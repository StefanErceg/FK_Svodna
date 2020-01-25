package Model.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Uplata{

    protected double iznos;
    protected Date datumIsteka;
    protected Date datumUplate;

    public Uplata(){
    super();
    }

    public Uplata(double iznos, Date datumUplate) {
        this.iznos = iznos;
        this.datumUplate = datumUplate;
    }

    public Uplata(double iznos, Date datumIsteka, Date datumUplate) {
        this.iznos = iznos;
        this.datumIsteka = datumIsteka;
        this.datumUplate = datumUplate;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Date getDatumIsteka() {
        return datumIsteka;
    }

    public void setDatumIsteka(Date datumIsteka) {
        this.datumIsteka = datumIsteka;
    }

    public Date getDatumUplate() {
        return datumUplate;
    }

    public void setDatumUplate(Date datumUplate) {
        this.datumUplate = datumUplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uplata uplata = (Uplata) o;
        return Objects.equals(iznos, uplata.iznos) &&
                Objects.equals(datumIsteka, uplata.datumIsteka) &&
                Objects.equals(datumUplate, uplata.datumUplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iznos, datumIsteka, datumUplate);
    }

    @Override
    public String toString() {
        return "Uplata{" +
                "iznos=" + iznos +
                ", datumIsteka=" + datumIsteka +
                ", datumUplate=" + datumUplate +
                '}';
    }
}