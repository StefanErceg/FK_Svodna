package model.DTO;

import java.util.Objects;

public class Equipment {
    protected int id;
    protected Person person;
    protected String type;
    protected Integer jerseyNumber;
    protected String code;
    protected boolean obligated;

    public Equipment() {
        super();
    }

    public Equipment(int id, Person person, String type, Integer jerseyNumber, String code, boolean obligated) {
        this.id = id;
        this.person = person;
        this.type = type;
        this.jerseyNumber = jerseyNumber;
        this.code = code;
        this.obligated = obligated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isObligated() {
        return obligated;
    }

    public void setObligated(boolean obligated) {
        this.obligated = obligated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", person=" + person +
                ", type='" + type + '\'' +
                ", jerseyNumber=" + jerseyNumber +
                ", code=" + code +
                '}';
    }
}
