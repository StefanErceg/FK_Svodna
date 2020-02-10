package model.util;

import model.DAO.DAOFactory;

public class FKSvodnaUtilities {
    private static DAOFactory daoFactory;

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null)
            daoFactory = DAOFactory.getDAOFactory();
        return daoFactory;
    }
}
