package model.DAO.mysql;

import model.DAO.MedicalExaminationDAO;
import model.DTO.MedicalExamination;
import model.DTO.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLMedicalExaminationDAO implements MedicalExaminationDAO {

    @Override
    public List<MedicalExamination> medicalExaminations() {
        ArrayList<MedicalExamination> medicalExaminations = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select lj.Id, DatumPregleda, DatumIsteka, OsobaId, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                       "from ljekarskipregled lj " +
                       "inner join osoba o on o.Id=lj.OsobaId " +
                       "where o.Obrisana=0";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                medicalExaminations.add(new MedicalExamination(rs.getInt("Id"), rs.getTimestamp("DatumPregleda"), rs.getTimestamp("DatumIsteka"),
                        new Person(rs.getInt("OsobaId"), rs.getString("Ime"), rs.getString("Prezime"),
                                rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                                rs.getString("Adresa"), rs.getString("BrojLicence"))));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return medicalExaminations;
    }

    @Override
    public MedicalExamination getMedicalExaminationById(int id) {
        MedicalExamination medicalExamination = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select lj.Id, DatumPregleda, DatumIsteka, OsobaId, Ime, Prezime, BrojTelefona, Jmb, Email, Adresa, BrojLicence " +
                "from ljekarskipregled lj " +
                "inner join osoba o on o.Id=lj.OsobaId " +
                "where o.Obrisana=0 and lj.Id=? ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                medicalExamination = new MedicalExamination(rs.getInt("Id"), rs.getTimestamp("DatumPregleda"), rs.getTimestamp("DatumIsteka"),
                        new Person(rs.getInt("OsobaId"), rs.getString("Ime"), rs.getString("Prezime"),
                                rs.getString("BrojTelefona"), rs.getString("Jmb"), rs.getString("Email"),
                                rs.getString("Adresa"), rs.getString("BrojLicence")));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return medicalExamination;
    }

    @Override
    public boolean insert(MedicalExamination medicalExamination) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "insert into ljekarskipregled(DatumPregleda, DatumIsteka, OsobaId) values (?, ?, ?)";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, medicalExamination.getExaminationDate());
            ps.setTimestamp(2, medicalExamination.getExpirationDate());
            ps.setInt(3, medicalExamination.getPerson().getId());

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
    public boolean update(MedicalExamination medicalExamination) {
        boolean retVal = false;

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "update ljekarskipregled set " +
                       "DatumPregleda=?, " +
                       "DatumIsteka=? " +
                       "where Id=? ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, medicalExamination.getExaminationDate());
            ps.setTimestamp(2, medicalExamination.getExpirationDate());
            ps.setInt(3, medicalExamination.getId());

            retVal = ps.executeUpdate() == 1;

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps);
        }
        return  retVal;
    }
}
