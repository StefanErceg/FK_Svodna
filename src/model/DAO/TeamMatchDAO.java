package model.DAO;

import model.DTO.TeamMatch;

import java.util.List;

public interface TeamMatchDAO {

    List<TeamMatch> teamMatch();
    TeamMatch getTeamMatchByIds( int teamId, int matchId);
    List<TeamMatch> getTeamMatchByTeamID(int teamId);
    boolean insert(TeamMatch teamMatch);
}
