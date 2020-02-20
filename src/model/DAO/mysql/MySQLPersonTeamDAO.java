package model.DAO.mysql;

import model.DAO.PersonTeamDAO;
import model.DTO.Person;
import model.DTO.PersonTeam;
import model.DTO.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersonTeamDAO implements PersonTeamDAO {

    @Override
    public List<PersonTeam> personTeams() {
        ArrayList<PersonTeam> personTeams = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select OsobaId, TimId, Od, Do, Uloga, PozicijaIgraca, Naziv, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                       "from osobatim ot " +
                       "inner join osoba o on o.Id=ot.OsobaId " +
                       "inner join tim t on t.Id=ot.TimId " +
                       "where o.Obrisana=0 and t.Obrisan=0 and Do is null";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                personTeams.add(new PersonTeam(new Person(rs.getInt("OsobaId"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")), new Team(rs.getInt("TimId"),
                        rs.getString("Naziv")), rs.getTimestamp("Od"), rs.getTimestamp("Do"),
                        rs.getString("Uloga"), rs.getString("PozicijaIgraca")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return personTeams;
    }

    @Override
    public List<PersonTeam> getPlayersForTeam(int teamId) {
        ArrayList<PersonTeam> personTeams = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select OsobaId, TimId, Od, Do, Uloga, PozicijaIgraca, Naziv, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                "from osobatim ot " +
                "inner join osoba o on o.Id=ot.OsobaId " +
                "inner join tim t on t.Id=ot.TimId " +
                "where o.Obrisana=0 and t.Obrisan=0 and ot.TimId=? and Do is null and Uloga='igrac'";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, teamId);
            rs = ps.executeQuery();

            while(rs.next()) {
                personTeams.add(new PersonTeam(new Person(rs.getInt("OsobaId"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")), new Team(rs.getInt("TimId"),
                        rs.getString("Naziv")), rs.getTimestamp("Od"), rs.getTimestamp("Do"),
                        rs.getString("Uloga"), rs.getString("PozicijaIgraca")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return personTeams;
    }

    @Override
    public List<PersonTeam> getStaffForTeam(int teamId) {
        ArrayList<PersonTeam> personTeams = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select OsobaId, TimId, Od, Do, Uloga, PozicijaIgraca, Naziv, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                "from osobatim ot " +
                "inner join osoba o on o.Id=ot.OsobaId " +
                "inner join tim t on t.Id=ot.TimId " +
                "where o.Obrisana=0 and t.Obrisan=0 and TimId=? and Uloga<>'igrac' and Do is null";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, teamId);
            rs = ps.executeQuery();

            while(rs.next()) {
                personTeams.add(new PersonTeam(new Person(rs.getInt("OsobaId"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")), new Team(rs.getInt("TimId"),
                        rs.getString("Naziv")), rs.getTimestamp("Od"), rs.getTimestamp("Do"),
                        rs.getString("Uloga"), rs.getString("PozicijaIgraca")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return personTeams;
    }

    @Override
    public PersonTeam getPersonTeamByIds(int personId, int teamId) {
        PersonTeam personTeam = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select OsobaId, TimId, Od, Do, Uloga, PozicijaIgraca, Naziv, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                "from osobatim ot " +
                "inner join osoba o on o.Id=ot.OsobaId " +
                "inner join tim t on t.Id=ot.TimId " +
                "where o.Obrisana=0 and t.Obrisan=0 and OsobaId=? and TimId=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, personId);
            ps.setInt(2, teamId);
            rs = ps.executeQuery();

            if(rs.next()) {
                personTeam = new PersonTeam(new Person(rs.getInt("OsobaId"), rs.getString("Ime"), rs.getString("Prezime"),
                        rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                        rs.getString("Adresa"), rs.getString("BrojLicence")), new Team(rs.getInt("TimId"),
                        rs.getString("Naziv")), rs.getTimestamp("Od"), rs.getTimestamp("Do"),
                        rs.getString("Uloga"), rs.getString("PozicijaIgraca"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return personTeam;
    }

    @Override
    public boolean insert(PersonTeam personTeam) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into osobatim values (?, ?, ?, ?, ?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, personTeam.getPerson().getId());
            ps.setInt(2, personTeam.getTeam().getId());
            ps.setTimestamp(3, personTeam.getDateFrom());
            ps.setTimestamp(4, personTeam.getDateTo());
            ps.setString(5, personTeam.getRole());
            ps.setString(6, personTeam.getPlayerPosition());

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
    public boolean update(PersonTeam personTeam) {
        boolean retVal = false;
        
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update osobatim set " +
                       "Od=?, " +
                       "Do=?, " +
                       "Uloga=?, " +
                       "PozicijaIgraca=? " +
                       "where OsobaId=? and TimId=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, personTeam.getDateFrom());
            ps.setTimestamp(2, personTeam.getDateTo());
            ps.setString(3, personTeam.getRole());
            ps.setString(4, personTeam.getPlayerPosition());
            ps.setInt(5, personTeam.getPerson().getId());
            ps.setInt(6, personTeam.getTeam().getId());

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
    public boolean delete(PersonTeam personTeam) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "delete from osobatim where OsobaId=? and TimId=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, personTeam.getPerson().getId());
            ps.setInt(2, personTeam.getTeam().getId());

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
