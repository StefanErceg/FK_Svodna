package model.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class Punishment {
    protected int id;
    protected Timestamp date;
    protected double monetaryAmount;
    protected int suspensionMatchesNumber;
    protected String description;
    protected Person person;
    protected Card card;

    public Punishment() {
        super();
    }

    public Punishment(int id, Timestamp date, double monetaryAmount, int suspensionMatchesNumber, String description, Person person, Card card) {
        this.id = id;
        this.date = date;
        this.monetaryAmount = monetaryAmount;
        this.suspensionMatchesNumber = suspensionMatchesNumber;
        this.description = description;
        this.person = person;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getMonetaryAmount() {
        return monetaryAmount;
    }

    public void setMonetaryAmount(double monetaryAmount) {
        this.monetaryAmount = monetaryAmount;
    }

    public int getSuspensionMatchesNumber() {
        return suspensionMatchesNumber;
    }

    public void setSuspensionMatchesNumber(int suspensionMatchesNumber) {
        this.suspensionMatchesNumber = suspensionMatchesNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Punishment that = (Punishment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Punishment{" +
                "id=" + id +
                ", date=" + date +
                ", monetaryAmount=" + monetaryAmount +
                ", suspensionMatchesNumber=" + suspensionMatchesNumber +
                ", description='" + description + '\'' +
                ", person=" + person +
                ", card=" + card +
                '}';
    }
}

