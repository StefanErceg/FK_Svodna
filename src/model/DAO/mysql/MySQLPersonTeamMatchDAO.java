package model.DAO.mysql;

import model.DAO.PersonTeamMatchDAO;
import model.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersonTeamMatchDAO implements PersonTeamMatchDAO {

    @Override
    public List<PersonTeamMatch> personTeamMatches() {
        ArrayList<PersonTeamMatch> personTeamMatches = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select OsobaId, TimId, UtakmicaId, Naziv, Od, Do, Uloga, PozicijaIgraca, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence, " +
                       "DatumIVrijeme, ProtivnickiTim, Rezultat " +
                       "from osobatimutakmica otu " +
                       "inner join osobatim ot on ot.OsobaId=otu.OsobaId and ot.TimId=otu.TimId " +
                       "inner join osoba o on o.Id=ot.OsobaId " +
                       "inner join tim t on t.Id=ot.TimId " +
                       "inner join utakmica u on u.Id=ot.UtakmicaId " +
                       "where t.Obrisan=0 and o.Obrisana=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                personTeamMatches.add(new PersonTeamMatch(new PersonTeam(new Person(rs.getInt("OsobaId"),
                        rs.getString("Ime"), rs.getString("Prezime"), rs.getString("BrojTelefona"),
                        rs.getString("Jmb"), rs.getString("Email"), rs.getString("Adresa"), rs.getString("BrojLicence")),
                        new Team(rs.getInt("TimId"), rs.getString("Naziv")), rs.getTimestamp("Od"),
                        rs.getTimestamp("Do"), rs.getString("Uloga"), rs.getString("PozicijaIgraca")),
                        new Match(rs.getInt("TimId"), rs.getTimestamp("DatumIVrijeme"), rs.getString("ProtivnickiTim"),
                        rs.getString("Rezultat"))));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return personTeamMatches;
    }

    @Override
    public PersonTeamMatch getPersonTeamMatchByIds(int personId, int teamId, int matchId) {
        PersonTeamMatch personTeamMatch = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select otu.OsobaId, otu.TimId, otu.UtakmicaId, Naziv, Od, Do, Uloga, PozicijaIgraca, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence, " +
                "DatumIVrijeme, ProtivnickiTim, Rezultat " +
                "from osobatimutakmica otu " +
                "inner join osobatim ot on ot.OsobaId=otu.OsobaId and ot.TimId=otu.TimId " +
                "inner join osoba o on o.Id=ot.OsobaId " +
                "inner join tim t on t.Id=ot.TimId " +
                "inner join utakmica u on u.Id=ot.UtakmicaId " +
                "where t.Obrisan=0 and o.Obrisana=0 and otu.OsobaId=? and otu.TimId=? and otu.matchId=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, personId);
            ps.setInt(2, teamId);
            ps.setInt(3, matchId);
            rs = ps.executeQuery();

            if(rs.next()) {
                personTeamMatch = new PersonTeamMatch(new PersonTeam(new Person(rs.getInt("OsobaId"),
                        rs.getString("Ime"), rs.getString("Prezime"), rs.getString("BrojTelefona"),
                        rs.getString("Jmb"), rs.getString("Email"), rs.getString("Adresa"), rs.getString("BrojLicence")),
                        new Team(rs.getInt("TimId"), rs.getString("Naziv")), rs.getTimestamp("Od"),
                        rs.getTimestamp("Do"), rs.getString("Uloga"), rs.getString("PozicijaIgraca")),
                        new Match(rs.getInt("TimId"), rs.getTimestamp("DatumIVrijeme"), rs.getString("ProtivnickiTim"),
                                rs.getString("Rezultat")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return personTeamMatch;
    }

    @Override
    public boolean insert(PersonTeamMatch personTeamMatch) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into osobatimutakmica values (?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, personTeamMatch.getPersonTeam().getPerson().getId());
            ps.setInt(2, personTeamMatch.getMatch().getId());

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
