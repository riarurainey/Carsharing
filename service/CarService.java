package carsharing.service;

import carsharing.dao.CarDao;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.util.ChooseOption;

import java.util.List;

public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {

        this.carDao = carDao;
    }

    public void createCar(Company company) {
        System.out.println("Enter the car name:");
        String carName = ChooseOption.getScanString();

        carDao.createCar(carName, company);

    }

    List<Car> getAllCars(Company company) {
        return carDao.getAllCars(company);
    }

    public void getCars(Company company) {
        List<Car> carList = carDao.getAllCars(company);
        if (carList.isEmpty()) {
            System.out.println("The car list is empty!");

        } else {
            int count = 1;
            for (Car car : carList) {
                System.out.println(count + ". " + car.getName());
                count++;
            }

        }
    }
}
