package model.DAO.mysql;

import model.DAO.EquipmentDAO;
import model.DTO.Equipment;
import model.DTO.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLEquipmentDAO implements EquipmentDAO {

    @Override
    public List<Equipment> equipment() {
        ArrayList<Equipment> equipment = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select op.Id, OsobaId, Tip, BrojDresa, Sifra, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                       "from oprema op " +
                       "inner join osoba os on os.Id=op.OsobaId " +
                       "where os.Obrisana=0 and op.Zaduzena=1";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                equipment.add(new Equipment(rs.getInt("Id"), new Person(rs.getInt("OsobaId"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona"), rs.getString("Jmb"),
                        rs.getString("Email"), rs.getString("Adresa"), rs.getString("BrojLicence")),
                        rs.getString("Tip"), rs.getInt("BrojDresa"), rs.getString("Sifra"),true));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return equipment;
    }

    @Override
    public Equipment getEquipmentById(int id) {
        Equipment equipment = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select op.Id, OsobaId, Tip, BrojDresa, Sifra, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                "from oprema op " +
                "inner join osoba os on os.Id=op.OsobaId " +
                "where os.Obrisana=0 and op.Zaduzena=1 and op.Id=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                equipment = new Equipment(rs.getInt("Id"), new Person(rs.getInt("OsobaId"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona"), rs.getString("Jmb"),
                        rs.getString("Email"), rs.getString("Adresa"), rs.getString("BrojLicence")),
                        rs.getString("Tip"), rs.getInt("BrojDresa"), rs.getString("Sifra"), rs.getBoolean("Zaduzena"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return equipment;
    }

    @Override
    public boolean obligate(Equipment equipment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update oprema set Zaduzena=1 where Id=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, equipment.getId());

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
    public boolean returnEquipment(Equipment equipment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update oprema set Zaduzena=0 where Id=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, equipment.getId());

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
    public boolean insert(Equipment equipment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into oprema (OsobaId, Tip, BrojDresa, Sifra, Zaduzena) values (?, ?, ?, ?, ?)";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, equipment.getPerson().getId());
            ps.setString(2, equipment.getType());
            ps.setInt(3, equipment.getJerseyNumber());
            ps.setString(4, equipment.getCode());
            ps.setBoolean(5, equipment.isObligated());

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
    public boolean update(Equipment equipment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update oprema set " +
                       "OsobaId=?, " +
                       "Tip=?, " +
                       "BrojDresa=?, " +
                       "Sifra=? " +
                       "where Id=? ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, equipment.getPerson().getId());
            ps.setString(2, equipment.getType());
            ps.setInt(3, equipment.getJerseyNumber());
            ps.setString(4, equipment.getCode());
            ps.setInt(5, equipment.getId());

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
