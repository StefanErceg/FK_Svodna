package model.DAO;

import model.DTO.PersonTeam;

import java.util.List;

public interface PersonTeamDAO {

    List<PersonTeam> personTeams();
    PersonTeam getPersonTeamByIds(int personId, int teamId);
    boolean insert(PersonTeam personTeam);
    boolean update(PersonTeam personTeam);
}
