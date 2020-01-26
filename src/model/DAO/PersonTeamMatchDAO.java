package model.DAO;

import model.DTO.PersonTeamMatch;

import java.util.List;

public interface PersonTeamMatchDAO {

    List<PersonTeamMatch> personTeamMatches();
    PersonTeamMatch getPersonTeamMatchByIds(int personId, int teamId, int matchId);
    boolean insert(PersonTeamMatch personTeamMatch);
}
