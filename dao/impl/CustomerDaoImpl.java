package carsharing.dao.impl;

import carsharing.dao.CustomerDao;
import carsharing.dao.util.PreparedDataBase;
import carsharing.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private final PreparedDataBase dataBase;

    public CustomerDaoImpl(PreparedDataBase dataBase) {
        this.dataBase = dataBase;

    }

    @Override
    public void createCustomer(String customerName) {
        try (Connection connection = dataBase.createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCompany =
                    "INSERT INTO CUSTOMER(name) VALUES('" + customerName + "'" + ");";
            statement.executeUpdate(sqlCreateCompany);
            System.out.println("The customer was added!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataBase.createConnection();
             Statement statement = connection.createStatement()) {

            String sqlCreateCustomer = "SELECT * FROM CUSTOMER";
            ResultSet resultSet = statement.executeQuery(sqlCreateCustomer);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Integer rentedCarId = resultSet.getInt(3) == 0 ? null : resultSet.getInt(3);
                customers.add(new Customer(id, name, rentedCarId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void rentedCar(int carId, Customer customer) {
        try (Connection connection = dataBase.createConnection();
             PreparedStatement prepareStatement = createPreparedStatementForUpdate(connection, carId, customer.getId())) {
            prepareStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private PreparedStatement createPreparedStatementForUpdate(Connection connection, int carId, int customerId
    ) throws SQLException {
        String sqlRentedCar = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(sqlRentedCar);
        ps.setInt(1, carId);
        ps.setInt(2, customerId);
        return ps;
    }
    private PreparedStatement createPreparedStatementForUpdate(Connection connection, Integer customerId
    ) throws SQLException {
        String sqlRentedCar = "UPDATE CUSTOMER SET RENTED_CAR_ID = null WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(sqlRentedCar);
        ps.setInt(1, customerId);
        return ps;
    }

    @Override
    public void getCustomerRentedCar(Integer rentedId) {

        try (Connection connection = dataBase.createConnection();
             PreparedStatement statement = createPreparedStatementForSelect(connection, rentedId)) {

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                System.out.println("Your rented car:");
                System.out.println(set.getString(1));
                System.out.println(set.getString(2));
            } else {
                System.out.println("You didn't rent a car!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnRentedCar(Customer customer) {

        try (Connection connection = dataBase.createConnection();
             PreparedStatement statement = createPreparedStatementForUpdate(connection, customer.getId())) {

           statement.executeUpdate();
           customer.setRentedCarId(null);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private PreparedStatement createPreparedStatementForSelect(Connection connection, Integer rentedId) throws SQLException {
        String selectRentedCar = "SELECT c.NAME, com.NAME FROM CAR as c" +
                " JOIN CUSTOMER as cust ON c.ID = cust.rented_car_id" +
                " JOIN COMPANY as com ON c.COMPANY_ID = com.ID" +
                " WHERE cust.rented_car_id = ?";
        PreparedStatement ps = connection.prepareStatement(selectRentedCar);
        ps.setInt(1, rentedId);
        return ps;
    }
}
