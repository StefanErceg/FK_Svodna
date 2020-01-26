package model.DTO;

import java.util.Objects;

public class PersonTeamMatch {
    protected PersonTeam personTeam;
    protected Match match;

    public PersonTeamMatch() {
    }

    public PersonTeamMatch(PersonTeam personTeam, Match match) {
        this.personTeam = personTeam;
        this.match = match;
    }

    public PersonTeam getPersonTeam() {
        return personTeam;
    }

    public void setPersonTeam(PersonTeam personTeam) {
        this.personTeam = personTeam;
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
        PersonTeamMatch that = (PersonTeamMatch) o;
        return Objects.equals(personTeam, that.personTeam) &&
                Objects.equals(match, that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personTeam, match);
    }

    @Override
    public String toString() {
        return "PersonTeamMatch{" +
                "personTeam=" + personTeam +
                ", match=" + match +
                '}';
    }
}
