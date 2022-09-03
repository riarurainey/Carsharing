package carsharing.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PreparedDataBase {
    private final String JDBC_DRIVER;
    private final String DB_URL;

    public PreparedDataBase(String[] args) {
        JDBC_DRIVER = "org.h2.Driver";
        DB_URL = "jdbc:h2:./src/carsharing/db/" + getNameDB(args);
    }

    public void start() {
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    public void createCompanyTable() {
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany =

                    "CREATE TABLE IF NOT EXISTS COMPANY(\n" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                            "NAME VARCHAR(200) UNIQUE NOT NULL);";

            statement.executeUpdate(sqlCreateCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropTables() {
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany = "DROP TABLE CUSTOMER;\n" + "DROP TABLE CAR;\n" + "DROP TABLE COMPANY;";


            statement.executeUpdate(sqlCreateCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createCarTable() {
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany =

                    "CREATE TABLE IF NOT EXISTS CAR(\n" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                            "NAME VARCHAR(200) UNIQUE NOT NULL,\n" +
                            "COMPANY_ID INT NOT NULL,\n" +
                            "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID)\n" +
                            "REFERENCES COMPANY(ID));";

            statement.executeUpdate(sqlCreateCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createCustomerTable() {
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany =

                    "CREATE TABLE IF NOT EXISTS CUSTOMER(\n" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                            "NAME VARCHAR(200) UNIQUE NOT NULL, \n" +
                            "RENTED_CAR_ID INT DEFAULT NULL, \n" +
                            "CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID)\n" +
                            "REFERENCES CAR(ID));";


            statement.executeUpdate(sqlCreateCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String getNameDB(String[] args) {
        if (args.length > 1) {
            if ("-databaseFileName".equals(args[0])) {
                return args[1];
            }
        }
        return "test";
    }


}
