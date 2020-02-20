package model.DAO;

import model.DTO.Person;
import model.DTO.Team;

import java.util.List;

public interface TeamDAO {

    List<Team> teams();
    Team getTeamById(int id);
    Team getTeamByName(String name);
    boolean insert(Team team);
    boolean update(Team team);
    boolean delete(Team team);
}
