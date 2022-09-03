package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Company;

import java.util.List;

public interface CarDao {
    List<Car> getAllCars(Company company);
    void createCar(String carName, Company company);

}
