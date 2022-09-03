package carsharing.dao.impl;

import carsharing.dao.CompanyDao;
import carsharing.dao.util.PreparedDataBase;
import carsharing.model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CompanyDaoImpl implements CompanyDao {

    private final PreparedDataBase dataBase;

    public CompanyDaoImpl(PreparedDataBase dataBase) {
        this.dataBase = dataBase;

    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();

        try (Connection connection = dataBase.createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany = "SELECT * FROM COMPANY";
            ResultSet resultSet = statement.executeQuery(sqlCreateCompany);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                companies.add(new Company(id, name));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void createCompany(String companyName) {
        try (Connection connection = dataBase.createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany =
                    "INSERT INTO COMPANY(name) \n" +
                            "VALUES" + "(" + "'" + companyName + "'" + ");";
            statement.executeUpdate(sqlCreateCompany);

            System.out.println("The company was created!");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
