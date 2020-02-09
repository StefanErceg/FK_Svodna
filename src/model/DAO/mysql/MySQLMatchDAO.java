package model.DAO.mysql;

import model.DAO.MatchDAO;
import model.DTO.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLMatchDAO implements MatchDAO {

    @Override
    public List<Match> matches() {
        ArrayList<Match> matches = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from utakmica";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                matches.add(new Match(rs.getInt("Id"), rs.getTimestamp("DatumIVrijeme"),
                        rs.getString("ProtivnickiTim"), rs.getString("Rezultat")));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return matches;
    }

    @Override
    public Match getMatchById(int id) {
        Match match = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from utakmica where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                match = new Match(rs.getInt("Id"), rs.getTimestamp("DatumIVrijeme"),
                        rs.getString("ProtivnickiTim"), rs.getString("Rezultat"));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return match;
    }

    @Override
    public Match getFirstMatch() {
        Match match = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from utakmica order by DatumIVrijeme limit 1";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if(rs.next()) {
                match = new Match(rs.getInt("Id"), rs.getTimestamp("DatumIVrijeme"),
                        rs.getString("ProtivnickiTim"), rs.getString("Rezultat"));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return match;
    }

    @Override
    public boolean insert(Match match) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into utakmica(DatumIVrijeme, ProtivnickiTim, Rezultat) values (?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, match.getDate());
            ps.setString(2, match.getOpposingTeam());
            ps.setString(3, match.getResult());

            retVal = ps.executeUpdate() == 1;

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps);
        }
        return retVal;
    }

    @Override
    public boolean update(Match match) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update utakmica set " +
                       "DatumIVrijeme=?, " +
                       "ProtivnickiTim=?, " +
                       "Rezultat=? " +
                       "where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, match.getDate());
            ps.setString(2, match.getOpposingTeam());
            ps.setString(3, match.getResult());
            ps.setInt(4, match.getId());

            retVal = ps.executeUpdate() == 1;

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps);
        }
        return  retVal;
    }
}
