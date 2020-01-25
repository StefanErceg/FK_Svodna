package model.DTO;

import java.sql.Date;
import java.util.Objects;

public class Uplata{
    protected int id;
    protected double iznos;
    protected Date datumIsteka;
    protected Date datumUplate;

    public Uplata(){
    super();
    }

    public Uplata(int id, double iznos, Date datumIsteka, Date datumUplate) {
        this.id = id;
        this.iznos = iznos;
        this.datumIsteka = datumIsteka;
        this.datumUplate = datumUplate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == uplata.id &&
                Double.compare(uplata.iznos, iznos) == 0 &&
                Objects.equals(datumIsteka, uplata.datumIsteka) &&
                Objects.equals(datumUplate, uplata.datumUplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, iznos, datumIsteka, datumUplate);
    }

    @Override
    public String toString() {
        return "Uplata{" +
                "id=" + id +
                ", iznos=" + iznos +
                ", datumIsteka=" + datumIsteka +
                ", datumUplate=" + datumUplate +
                '}';
    }
}