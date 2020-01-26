package model.DAO.mysql;

import java.sql.*;

public class DBUtilities {

    private static DBUtilities instance;

    public static DBUtilities getInstance() {
        if (instance == null)
            instance = new DBUtilities();
        return instance;
    }

    private DBUtilities() {}

    public void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
               // AzilUtilities.getDAOFactory().getLoggerDAO().insert(new LoggerDTO("DBUtilities - close", new Date(Calendar.getInstance().getTime().getTime()), ex.fillInStackTrace().toString()));
            }
        }
    }

    public void close(Statement s) {
        if (s != null) {
            try {
                s.close();
            } catch (SQLException ex) {
                //AzilUtilities.getDAOFactory().getLoggerDAO().insert(new LoggerDTO("DBUtilities - close", new Date(Calendar.getInstance().getTime().getTime()), ex.fillInStackTrace().toString()));
            }
        }
    }

    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                //AzilUtilities.getDAOFactory().getLoggerDAO().insert(new LoggerDTO("DBUtilities - close", new Date(Calendar.getInstance().getTime().getTime()), ex.fillInStackTrace().toString()));
            }
        }
    }

    public void close(Connection conn, Statement s) {
        close(s);
        close(conn);
    }

    public void close(Connection conn, ResultSet rs) {
        close(rs);
        close(conn);
    }

    public void close(Statement s, ResultSet rs) {
        close(rs);
        close(s);
    }

    public void close(Connection conn, Statement s, ResultSet rs) {
        close(rs);
        close(s);
        close(conn);
    }

    public String preparePattern(String text) {
        return text.replace('*', '%').replace('?', '_');
    }

}
