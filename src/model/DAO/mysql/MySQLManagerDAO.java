package model.DAO.mysql;

import model.DAO.ManagerDAO;
import model.DTO.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLManagerDAO implements ManagerDAO {

    @Override
    public List<Manager> managers() {
        ArrayList<Manager> managers = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from rukovodilac where Obrisan=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                managers.add(new Manager(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Email"), rs.getString("Pozicija")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return managers;
    }

    @Override
    public Manager getManagerById(int id) {
        Manager manager = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from rukovodilac where Id=? and Obrisan=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                manager = new Manager(rs.getInt("Id"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Email"), rs.getString("Pozicija"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return manager;
    }

    @Override
    public boolean insert(Manager manager) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into rukovodilac(Ime, Prezime, BrojTelefona, Email, Pozicija, Obrisan) values (?, ?, ?, ?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, manager.getName());
            ps.setString(2, manager.getSurname());
            ps.setString(3, manager.getPhoneNumber());
            ps.setString(4, manager.getEmail());
            ps.setString(5, manager.getPosition());
            ps.setInt(6,0);

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
    public boolean update(Manager manager) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update rukovodilac set " +
                       "Ime=?, " +
                       "Prezime=?, " +
                       "BrojTelefona=?, " +
                       "Email=?, " +
                       "Pozicija=? " +
                       "where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, manager.getName());
            ps.setString(2, manager.getSurname());
            ps.setString(3, manager.getPhoneNumber());
            ps.setString(4, manager.getEmail());
            ps.setString(5, manager.getPosition());
            ps.setInt(6, manager.getId());

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
    public boolean delete(Manager manager) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update rukovodilac set Obrisan=1 where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, manager.getId());

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
