package model.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class MedicalExamination {
    protected int id;
    protected Timestamp examinationDate;
    protected Timestamp expirationDate;
    protected Person person;

    public MedicalExamination() {
        super();
    }

    public MedicalExamination(int id, Timestamp examinationDate, Timestamp expirationDate, Person person) {
        this.id = id;
        this.examinationDate = examinationDate;
        this.expirationDate = expirationDate;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(Timestamp examinationDate) {
        this.examinationDate = examinationDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalExamination that = (MedicalExamination) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MedicalExamination{" +
                "id=" + id +
                ", expiration date=" + expirationDate +
                ", examination date=" + examinationDate +
                '}';
    }
}
