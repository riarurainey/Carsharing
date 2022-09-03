package carsharing;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.CustomerDao;
import carsharing.dao.impl.CarDaoImpl;
import carsharing.dao.impl.CompanyDaoImpl;
import carsharing.dao.impl.CustomerDaoImpl;
import carsharing.dao.util.PreparedDataBase;
import carsharing.service.CarService;
import carsharing.service.CompanyService;
import carsharing.service.CustomerService;


public class Main {
    public static void main(String[] args) {
        PreparedDataBase preparedDataBase = new PreparedDataBase(args);
//        preparedDataBase.dropTables();
        preparedDataBase.start();
        CompanyDao companyDao = new CompanyDaoImpl(preparedDataBase);
        CarDao carDao = new CarDaoImpl(preparedDataBase);
        CustomerDao customerDao = new CustomerDaoImpl(preparedDataBase);
        CarService carService = new CarService(carDao);
        CompanyService companyService = new CompanyService(companyDao, carService);
        CustomerService customerService = new CustomerService(customerDao, companyService, carService);

        CarSharing carSharing = new CarSharing(companyService, customerService, carService);
        carSharing.startProgram();

    }

}

