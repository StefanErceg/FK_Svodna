package model.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class PersonTeam {
    protected Person person;
    protected Team team;
    protected Timestamp dateFrom;
    protected Timestamp dateTo;
    protected String role;
    protected String playerPosition;
    protected int jerseyNumber;

    public PersonTeam() {
    }

    public PersonTeam(Person person, Team team, Timestamp dateFrom, Timestamp dateTo, String role, String playerPosition, int jerseyNumber) {
        this.person = person;
        this.team = team;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.role = role;
        this.playerPosition = playerPosition;
        this.jerseyNumber = jerseyNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonTeam that = (PersonTeam) o;
        return Objects.equals(person, that.person) &&
                Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, team);
    }

    @Override
    public String toString() {
        return "PersonTeam{" +
                "person=" + person +
                ", team=" + team +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", role='" + role + '\'' +
                ", playerPosition='" + playerPosition + '\'' +
                '}';
    }
}
