package model.DAO;

import model.DTO.Team;

import java.util.List;

public interface TeamDAO {

    List<Team> teams();
    Team getTeamById(int id);
    boolean insert(Team team);
    boolean update(Team team);
    boolean delete(Team team);
}
