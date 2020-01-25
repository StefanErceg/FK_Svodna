package model.DTO;

import java.sql.Date;
import java.util.Objects;

public class LjekarskiPregled {
    protected int id;
    protected Date datumIsteka;
    protected Date datumPregleda;

    public LjekarskiPregled() {
        super();
    }

    public LjekarskiPregled(int id, Date datumIsteka, Date datumPregleda) {
        this.id = id;
        this.datumIsteka = datumIsteka;
        this.datumPregleda = datumPregleda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatumIsteka() {
        return datumIsteka;
    }

    public void setDatumIsteka(Date datumIsteka) {
        this.datumIsteka = datumIsteka;
    }

    public Date getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(Date datumPregleda) {
        this.datumPregleda = datumPregleda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LjekarskiPregled that = (LjekarskiPregled) o;
        return id == that.id &&
                Objects.equals(datumIsteka, that.datumIsteka) &&
                Objects.equals(datumPregleda, that.datumPregleda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datumIsteka, datumPregleda);
    }

    @Override
    public String toString() {
        return "LjekarskiPregled{" +
                "id=" + id +
                ", datumIsteka=" + datumIsteka +
                ", datumPregleda=" + datumPregleda +
                '}';
    }
}
