package model.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class Payment {
    protected int id;
    protected Sponsor sponsor;
    protected double amount;
    protected Timestamp expirationDate;
    protected Timestamp paymentDate;

    public Payment(){
    super();
    }

    public Payment(int id, Sponsor sponsor, double amount, Timestamp paymentDate, Timestamp expirationDate) {
        this.id = id;
        this.sponsor = sponsor;
        this.amount = amount;
        this.expirationDate = expirationDate;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", iznos=" + amount +
                ", datumIsteka=" + expirationDate +
                ", datumUplate=" + paymentDate +
                '}';
    }
}