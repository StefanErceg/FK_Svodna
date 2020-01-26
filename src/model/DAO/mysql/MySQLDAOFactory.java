package model.DAO.mysql;

import model.DAO.*;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public  ContactPersonDAO getContactPersonDAO() {
        return new MySQLContactPersonDAO();
    }

    @Override
    public  EquipmentDAO getEquipmentDAO() {
        return new MySQLEquipmentDAO();
    }

    @Override
    public  ManagerDAO getManagerDAO() {
        return new MySQLManagerDAO();
    }

    @Override
    public MatchDAO getMatchDAO() {
        return new MySQLMatchDAO();
    }

    @Override
    public MedicalExaminationDAO getMedicalExaminationDAO() {
        return new MySQLMedicalExaminationDAO();
    }

    @Override
    public ObligationDAO getObligationDAO() {
        return new MySQLObligationDAO();
    }

    @Override
    public PaymentDAO geTPaymentDAO() {
        return new MySQLPaymentDAO();
    }

    @Override
    public PersonDAO getPersonDAO() {
        return new MySQLPersonDAO();
    }

    @Override
    public PersonTeamDAO getPersonTeamDAO() {
        return new MySQLPersonTeamDAO();
    }

    @Override
    public PersonTeamMatchDAO getPersonTeamMatchDAO() {
        return new MySQLPersonTeamMatchDAO();
    }

    @Override
    public PunishmentDAO getPunishmentDAO() {
        return new MySQLPunishmentDAO();
    }

    @Override
    public SponsorContactPersonDAO getsponsorContractPersonDAO() {
        return new MySQLSponsorContactPersonDAO();
    }

    @Override
    public SponsorDAO getSponsorDAO() {
        return new MySQLSponsorDAO();
    }

    @Override
    public TeamDAO getTeamDAO() {
        return new MySQLTeamDAO();
    }

    @Override
    public UserAccountDAO getUserAccountDAO() {
        return new MySQLUserAccountDAO();
    }
}
