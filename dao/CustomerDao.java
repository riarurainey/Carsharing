package carsharing.dao;

import carsharing.model.Customer;

import java.util.List;

public interface CustomerDao {
    void createCustomer(String customerName);
    List<Customer> getAllCustomers();
    void rentedCar(int carId, Customer customer);
    void getCustomerRentedCar(Integer id);
    void returnRentedCar(Customer customer);

}
