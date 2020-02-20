package model.DAO;

import model.DTO.Match;

import java.util.List;

public interface MatchDAO {

    List<Match> matches();
    Match getMatchById(int id);
    Match getFirstMatch();
    Match getLastMatch();
    boolean insert(Match match);
    boolean update(Match match);
}
