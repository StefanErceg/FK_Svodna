package model.DAO.mysql;

import model.DAO.SponsorDAO;
import model.DTO.Sponsor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLSponsorDAO implements SponsorDAO {

    @Override
    public List<Sponsor> sponsors() {
        ArrayList<Sponsor> sponsors = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from sponzor where Obrisan=0";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                sponsors.add(new Sponsor(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Adresa"),
                        rs.getString("Email"), rs.getString("BrojTelefona"), rs.getString("Vrsta"),
                        rs.getString("JmbJib")));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return sponsors;
    }

    @Override
    public Sponsor getSponsorById(int id) {
        Sponsor sponsor = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from sponzor where Id=? and Obrisan=0";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                sponsor = new Sponsor(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Adresa"),
                        rs.getString("Email"), rs.getString("BrojTelefona"), rs.getString("Vrsta"),
                        rs.getString("JmbJib"));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return sponsor;
    }

    @Override
    public boolean insert(Sponsor sponsor) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into sponzor(Ime, Adresa, Email, BrojTelefona, Vrsta, JmbJib, Obrisan) values (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);

            ps.setString(1, sponsor.getName());
            ps.setString(2, sponsor.getAddress());
            ps.setString(3, sponsor.getEmail());
            ps.setString(4, sponsor.getPhoneNumber());
            ps.setString(5, sponsor.getKind());
            ps.setString(6, sponsor.getJmbjib());
            ps.setInt(7, 0);

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
    public boolean update(Sponsor sponsor) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update sponzor set " +
                       "Ime=?, " +
                       "Adresa=?, " +
                       "Email=?, " +
                       "BrojTelefona=? , " +
                       "Vrsta=?, " +
                       "JmbJib=? " +
                       "where Id=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();ps = conn.prepareStatement(query);
            ps.setString(1, sponsor.getName());
            ps.setString(2, sponsor.getAddress());
            ps.setString(3, sponsor.getEmail());
            ps.setString(4, sponsor.getPhoneNumber());
            ps.setString(5, sponsor.getKind());
            ps.setString(6, sponsor.getJmbjib());
            ps.setInt(7, sponsor.getId());

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
    public boolean delete(Sponsor sponsor) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update sponzor set " +
                "obrisan=1 " +
                "where Id=?;";

        try {
            conn = ConnectionPool.getInstance().checkOut();ps = conn.prepareStatement(query);
            ps.setInt(1, sponsor.getId());

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
