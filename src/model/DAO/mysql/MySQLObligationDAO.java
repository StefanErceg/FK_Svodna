package model.DAO.mysql;

import model.DAO.ObligationDAO;
import model.DTO.Match;
import model.DTO.Obligation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLObligationDAO implements ObligationDAO {

    @Override
    public List<Obligation> obligations() {
        ArrayList<Obligation> obligations = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select z.Id, z.Opis, z.Odradjeno, u.Id as uId, u.DatumIVrijeme, u.ProtivnickiTim, u.Rezultat " +
                       "from zaduzenje z " +
                       "inner join utakmica u on u.Id=z.UtakmicaId " +
                       "where z.Obrisano=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                obligations.add(new Obligation(rs.getInt("Id"), rs.getString("Opis"),
                        rs.getBoolean("Odradjeno"), new Match(rs.getInt("uId"),
                        rs.getTimestamp("DatumIVrijeme"), rs.getString("ProtivnickiTim"),
                        rs.getString("Rezultat"))));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return obligations;
    }

    @Override
    public Obligation getObligationById(int id) {
        Obligation obligation = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select z.Id, z.Opis, z.Odradjeno, u.Id as uId, u.DatumIVrijeme, u.ProtivnickiTim, u.Rezultat " +
                "from zaduzenje z " +
                "inner join utakmica u on u.Id=z.UtakmicaId " +
                "where z.Obrisano=0 and z.Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                obligation = new Obligation(rs.getInt("Id"), rs.getString("Opis"),
                        rs.getBoolean("Odradjeno"), new Match(rs.getInt("uId"),
                        rs.getTimestamp("DatumIVrijeme"), rs.getString("ProtivnickiTim"),
                        rs.getString("Rezultat")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return obligation;
    }

    @Override
    public List<Obligation> getObligationsForMatch(int matchId) {
        ArrayList<Obligation> obligations = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select z.Id, z.Opis, z.Odradjeno, u.Id as uId, u.DatumIVrijeme, u.ProtivnickiTim, u.Rezultat " +
                "from zaduzenje z " +
                "inner join utakmica u on u.Id=z.UtakmicaId " +
                "where z.Obrisano=0 and z.UtakmicaId=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, matchId);
            rs = ps.executeQuery();

            while(rs.next()) {
                obligations.add(new Obligation(rs.getInt("Id"), rs.getString("Opis"),
                        rs.getBoolean("Odradjeno"), new Match(rs.getInt("uId"),
                        rs.getTimestamp("DatumIVrijeme"), rs.getString("ProtivnickiTim"),
                        rs.getString("Rezultat"))));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return obligations;
    }

    @Override
    public boolean insert(Obligation obligation) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into zaduzenje (Opis, Odradjeno, UtakmicaId, Obrisano) values (?, ?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, obligation.getDescription());
            ps.setBoolean(2, obligation.isDone());
            ps.setInt(3, obligation.getMatch().getId());
            ps.setInt(4, 0);

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
    public boolean update(Obligation obligation) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update zaduzenje set " +
                       "Opis=?, " +
                       "Odradjeno=?, " +
                       "UtakmicaId=? " +
                       "where Id=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, obligation.getDescription());
            ps.setBoolean(2, obligation.isDone());
            ps.setInt(3, obligation.getMatch().getId());
            ps.setInt(4, obligation.getId());

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
    public boolean delete(Obligation obligation) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update zaduzenje set Obrisano=1 where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, obligation.getId());

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
