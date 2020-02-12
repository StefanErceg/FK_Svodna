package model.DAO.mysql;

import model.DAO.PersonDAO;
import model.DTO.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersonDAO implements PersonDAO {

    @Override
    public List<Person> persons() {
        ArrayList<Person> persons = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from osoba where Obrisana=0";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                persons.add(new Person(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return persons;
    }

    @Override
    public Person getPersonById(int id) {
        Person person = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from osoba where Obrisana=0 and Id=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                person = new Person(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence"));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return person;
    }

    @Override
    public boolean insert(Person person) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into osoba (Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence, Obrisana) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPhoneNumber());
            ps.setString(4, person.getJmb());
            ps.setString(5, person.getEmail());
            ps.setString(6, person.getAddress());
            ps.setString(7, person.getLicenceNumber());
            ps.setInt(8, 0);

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
    public boolean update(Person person) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update osoba set " +
                       "Ime=?, " +
                       "Prezime=?, " +
                       "BrojTelefona=?, " +
                       "Jmb=?, " +
                       "Email=?, " +
                       "Adresa=?, " +
                       "BrojLicence=? " +
                       "where Id=? ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPhoneNumber());
            ps.setString(4, person.getJmb());
            ps.setString(5, person.getEmail());
            ps.setString(6, person.getAddress());
            ps.setString(7, person.getLicenceNumber());
            ps.setInt(8, person.getId());

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
    public boolean delete(Person person) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update osoba set Obrisana=1 where Id=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, person.getId());

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
