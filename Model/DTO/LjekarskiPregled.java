package Model.DTO;

import java.sql.Date;
import java.util.Objects;

public class LjekarskiPregled {
    protected Date datumIsteka;
    protected Date datumPregleda;

    public LjekarskiPregled() {
        super();
    }

    public LjekarskiPregled(Date datumIsteka, Date datumPregleda) {
        this.datumIsteka = datumIsteka;
        this.datumPregleda = datumPregleda;
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
        return Objects.equals(datumIsteka, that.datumIsteka) &&
                Objects.equals(datumPregleda, that.datumPregleda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datumIsteka, datumPregleda);
    }

    @Override
    public String toString() {
        return "LjekarskiPregled{" +
                "datumIsteka=" + datumIsteka +
                ", datumPregleda=" + datumPregleda +
                '}';
    }
}
