package model.DTO;

import java.util.Objects;

public class TeamMatch {
    protected Team team;
    protected Match match;

    public TeamMatch() {
    }

    public TeamMatch(Team team, Match match) {
        this.team = team;
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamMatch that = (TeamMatch) o;
        return Objects.equals(team, that.team) &&
                Objects.equals(match, that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, match);
    }

    @Override
    public String toString() {
        return "TeamMatch{" +
                "team=" + team +
                ", match=" + match +
                '}';
    }
}
