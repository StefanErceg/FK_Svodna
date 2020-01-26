package model.DAO.mysql;

import model.DAO.PaymentDAO;
import model.DTO.Payment;
import model.DTO.Sponsor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPaymentDAO implements PaymentDAO {

    @Override
    public List<Payment> payments() {
        ArrayList<Payment> payments = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select u.Id, s.Id as sId, s.Ime, s.Adresa, s.BrojTelefona, s.Email, s.Vrsta, s.Jmbjib, u.Iznos, u.DatumUplate, u.DatumIsteka " +
                "from uplata u " +
                "inner join sponzor s on s.Id=u.SponzorId " +
                "where s.Obrisan=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                payments.add(new Payment(rs.getInt("Id"), new Sponsor(rs.getInt("sId"), rs.getString("Ime"),
                        rs.getString("Adresa"), rs.getString("BrojTelefona"), rs.getString("Email"),
                        rs.getString("Vrsta"), rs.getString("JmbJib")), rs.getDouble("Iznos"),
                        rs.getTimestamp("DatumUplate"), rs.getTimestamp("DatumIsteka")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return payments;
    }

    @Override
    public Payment getPaymentById(int id) {
        Payment payment = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select u.Id, s.Id as sId, s.Ime, s.Adresa, s.BrojTelefona, s.Email, s.Vrsta, s.Jmbjib, u.Iznos, u.DatumUplate, u.DatumIsteka " +
                "from uplata u " +
                "inner join sponzor s on s.Id=u.SponzorId " +
                "where s.Obrisan=0 and Id=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                payment = new Payment(rs.getInt("Id"), new Sponsor(rs.getInt("sId"), rs.getString("Ime"),
                        rs.getString("Adresa"), rs.getString("BrojTelefona"), rs.getString("Email"),
                        rs.getString("Vrsta"), rs.getString("JmbJib")), rs.getDouble("Iznos"),
                        rs.getTimestamp("DatumUplate"), rs.getTimestamp("DatumIsteka"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return payment;
    }

    @Override
    public boolean insert(Payment payment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into uplata(SponzorId, Iznos, DatumUplate, DatumIsteka) values (?, ?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, payment.getSponsor().getId());
            ps.setDouble(2, payment.getAmount());
            ps.setTimestamp(3, payment.getPaymentDate());
            ps.setTimestamp(4, payment.getExpirationDate());

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
    public boolean update(Payment payment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update uplata set " +
                       "SponzorId=?, " +
                       "Iznos=?, " +
                       "DatumUplate=?, " +
                       "DatumIsteka=? " +
                       "where Id=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, payment.getSponsor().getId());
            ps.setDouble(2, payment.getAmount());
            ps.setTimestamp(3, payment.getPaymentDate());
            ps.setTimestamp(4, payment.getExpirationDate());
            ps.setInt(5, payment.getId());

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
