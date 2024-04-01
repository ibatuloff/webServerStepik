package database;

import org.h2.jdbcx.JdbcDataSource;
import AccountService.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private Connection connection;

    public DatabaseService() {
        this.connection = getH2Connection();
    }


    private static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create() throws Exception {
        try {
            System.out.println("Creating table users if needed");
            (new UserDao(connection)).createTable();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public long addUser(UserProfile userProfile) throws Exception {
        try {
            connection.setAutoCommit(false);
            UserDao dao = new UserDao(connection);
            dao.insertUser(userProfile);
            connection.commit();
            return dao.getUserId(userProfile.getLogin());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new Exception(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public UserProfile getUser(String login) throws Exception {
        try {
            UserDao dao = new UserDao(connection);
            UserData dataSet = dao.get(login);
            return new UserProfile(dataSet.getLogin(), dataSet.getPassword());
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void cleanUp() throws Exception {
        UserDao dao = new UserDao(connection);
        try {
            dao.cleanup();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}