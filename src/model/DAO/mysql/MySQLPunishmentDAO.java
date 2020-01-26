package model.DAO.mysql;

import model.DAO.PunishmentDAO;
import model.DTO.Card;
import model.DTO.Punishment;
import model.DTO.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPunishmentDAO implements PunishmentDAO {

    @Override
    public List<Punishment> panishments() {
        ArrayList<Punishment> panishments = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select k.Id, DatumKazne, NovcaniIznos, BrojMecevaSuspenzije, Opis, OsobaId, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence, Karton " +
                       "from kazna k " +
                       "inner join osoba o on o.Id=k.OsobaId " +
                       "where o.Obrisana=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                panishments.add(new Punishment(rs.getInt("Id"), rs.getTimestamp("DatumKazne"), rs.getDouble("Iznos"),
                        rs.getInt("BrojMecevaSuspenzije"), rs.getString("Opis"), new Person(rs.getInt("OsobaId"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona"),rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")), Card.valueOf(rs.getString("Karton"))));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return panishments;
    }

    @Override
    public List<Punishment> getPanishmentForPlayersId(int id) {
        ArrayList<Punishment> panishments = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select k.Id, DatumKazne, NovcaniIznos, BrojMecevaSuspenzije, Opis, OsobaId, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence, Karton " +
                "from kazna k " +
                "inner join osoba o on o.Id=k.OsobaId " +
                "where o.Obrisana=0 and k.OsobaId=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                panishments.add(new Punishment(rs.getInt("Id"), rs.getTimestamp("DatumKazne"), rs.getDouble("Iznos"),
                        rs.getInt("BrojMecevaSuspenzije"), rs.getString("Opis"), new Person(rs.getInt("OsobaId"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona"),rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")), Card.valueOf(rs.getString("Karton"))));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return panishments;
    }

    @Override
    public boolean insert(Punishment panishment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into kazna values (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, panishment.getId());
            ps.setInt(2, panishment.getPerson().getId());
            ps.setTimestamp(3, panishment.getDate());
            ps.setDouble(4, panishment.getMonetaryAmount());
            ps.setInt(5, panishment.getSuspensionMatchesNumber());
            ps.setString(6, panishment.getDescription());
            ps.setString(7, panishment.getCard().toString());

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
    public boolean update(Punishment panishment) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update kazna set " +
                       "DatumKazne=?, " +
                       "NovcaniIznos=?, " +
                       "BrojMecevaSuspenzije=?, " +
                       "Opis=?, " +
                       "Karton=? " +
                       "where Id=? and OsobaId=? ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);

            ps.setTimestamp(1, panishment.getDate());
            ps.setDouble(2, panishment.getMonetaryAmount());
            ps.setInt(3, panishment.getSuspensionMatchesNumber());
            ps.setString(4, panishment.getDescription());
            ps.setString(5, panishment.getCard().toString());
            ps.setInt(6, panishment.getId());
            ps.setInt(7, panishment.getPerson().getId());

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
