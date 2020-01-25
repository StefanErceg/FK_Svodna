package model.DTO;

import java.util.Objects;

public class Oprema {
    protected int id;
    protected String tipOpreme;
    protected Integer sifraOpreme;
    protected Integer brojDresa;

    public Oprema() {
        super();
    }

    public Oprema(int id, String tipOpreme, Integer sifraOpreme, Integer brojDresa) {
        this.id = id;
        this.tipOpreme = tipOpreme;
        this.sifraOpreme = sifraOpreme;
        this.brojDresa = brojDresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipOpreme() {
        return tipOpreme;
    }

    public void setTipOpreme(String tipOpreme) {
        this.tipOpreme = tipOpreme;
    }

    public Integer getSifraOpreme() {
        return sifraOpreme;
    }

    public void setSifraOpreme(Integer sifraOpreme) {
        this.sifraOpreme = sifraOpreme;
    }

    public Integer getBrojDresa() {
        return brojDresa;
    }

    public void setBrojDresa(Integer brojDresa) {
        this.brojDresa = brojDresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oprema oprema = (Oprema) o;
        return id == oprema.id &&
                Objects.equals(tipOpreme, oprema.tipOpreme) &&
                Objects.equals(sifraOpreme, oprema.sifraOpreme) &&
                Objects.equals(brojDresa, oprema.brojDresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipOpreme, sifraOpreme, brojDresa);
    }

    @Override
    public String toString() {
        return "Oprema{" +
                "id=" + id +
                ", tipOpreme='" + tipOpreme + '\'' +
                ", sifraOpreme=" + sifraOpreme +
                ", brojDresa=" + brojDresa +
                '}';
    }
}
