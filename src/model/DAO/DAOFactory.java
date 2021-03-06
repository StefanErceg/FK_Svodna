package model.DAO;

import model.DAO.mysql.MySQLDAOFactory;

public abstract class DAOFactory {

    public abstract ContactPersonDAO getContactPersonDAO();
    public abstract EquipmentDAO getEquipmentDAO();
    public abstract ManagerDAO getManagerDAO();
    public abstract MatchDAO getMatchDAO();
    public abstract MedicalExaminationDAO getMedicalExaminationDAO();
    public abstract ObligationDAO getObligationDAO();
    public abstract PaymentDAO geTPaymentDAO();
    public abstract PersonDAO getPersonDAO();
    public abstract PersonTeamDAO getPersonTeamDAO();
    public abstract TeamMatchDAO getPersonTeamMatchDAO();
    public abstract PunishmentDAO getPunishmentDAO();
    public abstract SponsorContactPersonDAO getsponsorContractPersonDAO();
    public abstract SponsorDAO getSponsorDAO();
    public abstract TeamDAO getTeamDAO();
    public abstract UserAccountDAO getUserAccountDAO();
    public static DAOFactory getDAOFactory() {
        return new MySQLDAOFactory();
    }

}
