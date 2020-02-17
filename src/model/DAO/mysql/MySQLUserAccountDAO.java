package model.DAO.mysql;

import model.DAO.UserAccountDAO;
import model.DTO.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserAccountDAO implements UserAccountDAO {

    @Override
    public List<UserAccount> accounts() {
        ArrayList<UserAccount> accounts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from korisnickinalog where Obrisan=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                accounts.add(new UserAccount(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getNString("KorisnickoIme"), rs.getString("Lozinka"),
                        rs.getBoolean("Administrator")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return accounts;
    }

    @Override
    public UserAccount getAccountById(int id) {
        UserAccount userAccount = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from korisnickinalog where Obrisan=0 and Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                userAccount = new UserAccount(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getNString("KorisnickoIme"), rs.getString("Lozinka"),
                        rs.getBoolean("Administrator"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return userAccount;
    }

    @Override
    public UserAccount getAccountByUsername(String username) {
        UserAccount userAccount = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from korisnickinalog where Obrisan=0 and KorisnickoIme=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if(rs.next()) {
                userAccount = new UserAccount(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getNString("KorisnickoIme"), rs.getString("Lozinka"),
                        rs.getBoolean("Administrator"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return userAccount;
    }

    @Override
    public UserAccount getAccount(String username, String password) {
        UserAccount userAccount = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from korisnickinalog where Obrisan=0 and KorisnickoIme=? and Lozinka=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if(rs.next()) {
                userAccount = new UserAccount(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getNString("KorisnickoIme"), rs.getString("Lozinka"),
                        rs.getBoolean("Administrator"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return userAccount;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        ArrayList<UserAccount> accounts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select * from korisnickinalog";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                accounts.add(new UserAccount(rs.getInt("Id"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getNString("KorisnickoIme"), rs.getString("Lozinka"),
                        rs.getBoolean("Administrator")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        for(UserAccount account : accounts) {
            if(username.equals(account.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean insert(UserAccount userAccount) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into korisnickinalog(Ime, Prezime, KorisnickoIme, Lozinka, Administrator, Obrisan) values (?, ?, ?, ?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, userAccount.getName());
            ps.setString(2, userAccount.getSurname());
            ps.setString(3, userAccount.getUsername());
            ps.setString(4, userAccount.getPassword());
            ps.setBoolean(5, userAccount.isAdmin());
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
    public boolean update(UserAccount userAccount) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update korisnickinalog set " +
                       "Ime=?, " +
                       "Prezime=?, " +
                       "KorisnickoIme=?, " +
                       "Lozinka=?, " +
                       "Administrator=? " +
                       "where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, userAccount.getName());
            ps.setString(2, userAccount.getSurname());
            ps.setString(3, userAccount.getUsername());
            ps.setString(4, userAccount.getPassword());
            ps.setBoolean(5, userAccount.isAdmin());
            ps.setInt(6,userAccount.getId());

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
    public boolean delete(UserAccount userAccount) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update korisnickinalog set Obrisan=1 where Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userAccount.getId());

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
