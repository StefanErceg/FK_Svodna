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
                        rs.getBoolean("Admin")));
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
                        rs.getBoolean("Admin"));
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
    public boolean insert(UserAccount userAccount) {
        boolean retVal = false;

        // ako bude bilo potrebe implementirati
        return retVal;
    }

    @Override
    public boolean update(UserAccount userAccount) {
        boolean retVal = false;

        // ako bude potrebe implementirati
        return retVal;
    }

    @Override
    public boolean delete(UserAccount userAccount) {
        boolean retVal = false;

        // ako bude potrebe implementirati
        return retVal;
    }
}
