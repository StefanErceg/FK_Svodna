package model.DAO.mysql;

import model.DAO.SponsorContactPersonDAO;
import model.DTO.ContactPerson;
import model.DTO.Sponsor;
import model.DTO.SponsorContactPerson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLSponsorContactPersonDAO implements SponsorContactPersonDAO {

    @Override
    public List<SponsorContactPerson> sponsorContactPersons() {
        ArrayList<SponsorContactPerson> sponsorContactPersons = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select sko.SponzorId, sko.KontaktOsobaId, s.Ime, s.Adresa, s.BrojTelefona, s.Email, s.Vrsta, s.JmbJib, ko.Ime as koIme, ko.Prezime, ko.BrojTelefona as koBrojTelefona" +
                       "from sponzorkontaktosoba sko " +
                       "inner join sponzor s on s.Id=sko.SponzorId " +
                       "inner join kontaktosoba ko on ko.Id=sko.KontaktOsobaId " +
                       "where s.Obrisan=0 and ko.Obrisana=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                sponsorContactPersons.add(new SponsorContactPerson(new Sponsor(rs.getInt("SpozorId"), rs.getString("Ime"),
                        rs.getString("Adresa"), rs.getString("BrojTelefona"), rs.getString("Email"),
                        rs.getString("Vrsta"), rs.getString("JmbJib")), new ContactPerson(rs.getInt("KontaktOsobaId"),
                        rs.getString("koIme"), rs.getString("Prezime"), rs.getString("koBrojTelefona"))));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return sponsorContactPersons;
    }

    @Override
    public SponsorContactPerson getSponsorContactPersonByIds(int sponsorId, int contactPersonId) {
        SponsorContactPerson sponsorContactPerson = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select sko.SponzorId, sko.KontaktOsobaId, s.Ime, s.Adresa, s.BrojTelefona, s.Email, s.Vrsta, s.JmbJib, ko.Ime as koIme, ko.Prezime, ko.BrojTelefona as koBrojTelefona" +
                "from sponzorkontaktosoba sko " +
                "inner join sponzor s on s.Id=sko.SponzorId " +
                "inner join kontaktosoba ko on ko.Id=sko.KontaktOsobaId " +
                "where sko.SponzorId=? and sko.KontaktOsobaId=? and s.Obrisan=0 and ko.Obrisana=0";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sponsorId);
            ps.setInt(2, contactPersonId);
            rs = ps.executeQuery();

            if(rs.next()) {
                sponsorContactPerson = new SponsorContactPerson(new Sponsor(rs.getInt("SpozorId"), rs.getString("Ime"),
                        rs.getString("Adresa"), rs.getString("BrojTelefona"), rs.getString("Email"),
                        rs.getString("Vrsta"), rs.getString("JmbJib")), new ContactPerson(rs.getInt("KontaktOsobaId"),
                        rs.getString("koIme"), rs.getString("Prezime"), rs.getString("koBrojTelefona")));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }

        return sponsorContactPerson;
    }

    @Override
    public boolean insert(SponsorContactPerson sponsorContactPerson) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into sponzorkontaktosoba values (?, ?)";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, sponsorContactPerson.getSponsor().getId());
            ps.setInt(2, sponsorContactPerson.getContactPerson().getId());

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
