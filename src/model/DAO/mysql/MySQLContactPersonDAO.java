package model.DAO.mysql;

import model.DAO.ContactPersonDAO;
import model.DTO.ContactPerson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLContactPersonDAO implements ContactPersonDAO {

    @Override
    public List<ContactPerson> contactPersons() {
        ArrayList<ContactPerson> contactPersons = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from kontaktosoba where Obrisana=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while(rs.next()) {
                contactPersons.add(new ContactPerson(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona")));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return contactPersons;
    }

    @Override
    public ContactPerson getContactPersonById(int id) {
        ContactPerson contactPerson = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from kontaktosoba where Obrisana=0 and Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                contactPerson = new ContactPerson(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona"));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return contactPerson;
    }

    @Override
    public boolean insert(ContactPerson contactPerson) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into kontaktosoba(Ime, Prezime, BrojTelefona, Obrisana) values (?, ?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, contactPerson.getName());
            ps.setString(2, contactPerson.getSurname());
            ps.setString(3, contactPerson.getPhoneNumber());
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
    public boolean update(ContactPerson contactPerson) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update kontaktosoba set " +
                       "Ime=?, " +
                       "Prezime=?, " +
                       "BrojTelefona=? " +
                       "where Id=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, contactPerson.getName());
            ps.setString(2, contactPerson.getSurname());
            ps.setString(3, contactPerson.getPhoneNumber());
            ps.setInt(4, contactPerson.getId());

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
    public boolean delete(ContactPerson contactPerson) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update kontaktosoba set Obrisana=1 where Id=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, contactPerson.getId());

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
