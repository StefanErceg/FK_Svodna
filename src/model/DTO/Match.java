package model.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class Match {
    protected int id;
    protected String opposingTeam;
    protected Timestamp date;
    protected String result;
    protected IsAway isAway;

    public Match() {
        super();
    }

    public Match(int id, Timestamp date, String opposingTeam, String result, IsAway isAway) {
        this.id = id;
        this.opposingTeam = opposingTeam;
        this.date = date;
        this.result = result;
        this.isAway = isAway;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpposingTeam() {
        return opposingTeam;
    }

    public void setOpposingTeam(String opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public IsAway getIsAway() {
        return isAway;
    }

    public void setIsAway(IsAway isAway) {
        this.isAway = isAway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match utakmica = (Match) o;
        return id == utakmica.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", opposing team='" + opposingTeam + '\'' +
                ", date=" + date +
                ", result='" + result + '\'' +
                '}';
    }
}
