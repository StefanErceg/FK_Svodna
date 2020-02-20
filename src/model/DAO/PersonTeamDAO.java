package model.DAO;

import model.DTO.PersonTeam;
import model.DTO.Team;

import java.util.List;

public interface PersonTeamDAO {

    List<PersonTeam> personTeams();
    PersonTeam getPersonTeamByIds(int personId, int teamId);
    List<PersonTeam> getPlayersForTeam(int teamId);
    List<PersonTeam> getStaffForTeam(int teamId);
    boolean insert(PersonTeam personTeam);
    boolean update(PersonTeam personTeam);
    boolean delete(PersonTeam personTeam);
}
