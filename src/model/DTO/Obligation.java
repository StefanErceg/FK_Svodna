package model.DTO;

import java.util.Objects;

public class Obligation {
    protected int id;
    protected String description;
    protected Match match;
    protected boolean done;

    public Obligation() { super();
    }

    public Obligation(int id, String description, boolean done, Match match) {
        this.id = id;
        this.description = description;
        this.match = match;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obligation that = (Obligation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Zaduzenje{" +
                "id=" + id +
                ", opis='" + description + '\'' +
                ", odradjeno=" + done +
                '}';
    }
}
