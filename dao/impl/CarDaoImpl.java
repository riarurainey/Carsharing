package carsharing.dao.impl;

import carsharing.dao.CarDao;
import carsharing.dao.util.PreparedDataBase;
import carsharing.model.Car;
import carsharing.model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private final PreparedDataBase dataBase;

    public CarDaoImpl(PreparedDataBase dataBase) {
        this.dataBase = dataBase;

    }

    @Override
    public List<Car> getAllCars(Company company) {
        List<Car> cars = new ArrayList<>();

        try (Connection connection = dataBase.createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCars = "SELECT CAR.id as id, CAR.name as name, CAR.company_id as company_id " +
                    "FROM CAR LEFT JOIN CUSTOMER ON CAR.id = CUSTOMER.rented_car_id WHERE company_id = " + company.getId() +
                    " AND CUSTOMER.rented_car_id is null";
            ResultSet resultSet = statement.executeQuery(sqlCreateCars);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int companyId = resultSet.getInt(3);
                cars.add(new Car(id, name, companyId));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        company.setCarList(cars);
        return cars;
    }

    @Override
    public void createCar(String carName, Company company) {
        try (Connection connection = dataBase.createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany =
                    "INSERT INTO CAR(name, company_id) " +
                            "VALUES('" + carName + "'" + ", " +
                            company.getId() +
                            ");";
            statement.executeUpdate(sqlCreateCompany);
            System.out.println("The car was added!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
