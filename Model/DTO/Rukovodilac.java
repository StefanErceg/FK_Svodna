package Model.DTO;

public class Rukovodilac {

    protected String ime;
    protected String prezime;
    protected String pozicija;
    protected String brojTelefona;
    protected String email;


    public Rukovodilac() {
        super();
    }

    public Rukovodilac(String ime, String prezime, String pozicija, String brojTelefona, String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.pozicija = pozicija;
        this.brojTelefona = brojTelefona;
        this.email = email;
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Rukovodilac{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", pozicija='" + pozicija + '\'' +
                ", brojTelefona='" + brojTelefona + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
