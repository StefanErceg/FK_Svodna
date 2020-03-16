package model.DAO.mysql;

import model.DAO.DAOFactory;
import model.DAO.TeamMatchDAO;
import model.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLTeamMatchDAO implements TeamMatchDAO {

    @Override
    public List<TeamMatch> teamMatch() {
        ArrayList<TeamMatch> teamMatch = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "\"select timId,utakmicaId from timutakmica;";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                Team team= DAOFactory.getDAOFactory().getTeamDAO().getTeamById(rs.getInt(1));
                Match match=DAOFactory.getDAOFactory().getMatchDAO().getMatchById(rs.getInt(2));
                teamMatch.add(new TeamMatch(team,match));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return teamMatch;
    }

    @Override
    public TeamMatch getTeamMatchByIds( int teamId, int matchId) {
        TeamMatch teamMatch = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "\"select tu.timId,tu.utakmicaId from timutakmica tu \" +\n" +
                "                \"where tu.timId in (select id from tim) \" +\n" +
                "                \"and tu.utakmicaId in (select id from utakmica);";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);

            ps.setInt(1, teamId);
            ps.setInt(2, matchId);
            rs = ps.executeQuery();

            if(rs.next()) {
                Team team= DAOFactory.getDAOFactory().getTeamDAO().getTeamById(rs.getInt(1));
                Match match=DAOFactory.getDAOFactory().getMatchDAO().getMatchById(rs.getInt(2));
                teamMatch=new TeamMatch(team,match);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return teamMatch;
    }
    public List<TeamMatch> getTeamMatchByTeamID(int teamId){
        ArrayList<TeamMatch> teamMatch = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select timId,utakmicaId from timutakmica where timID=?;";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, teamId);
            rs = ps.executeQuery();

            while(rs.next()) {
                Team team= DAOFactory.getDAOFactory().getTeamDAO().getTeamById(rs.getInt(1));
                Match match=DAOFactory.getDAOFactory().getMatchDAO().getMatchById(rs.getInt(2));
                teamMatch.add(new TeamMatch(team,match));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return teamMatch;
    }

    @Override
    public boolean insert(TeamMatch teamMatch) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into timutakmica values (?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, teamMatch.getTeam().getId());
            ps.setInt(2, teamMatch.getMatch().getId());
            retVal = ps.executeUpdate() == 1;
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps);
        }
        return retVal;
    }
}
