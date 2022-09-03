package carsharing.service;

import carsharing.dao.CustomerDao;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;
import carsharing.util.ChooseOption;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;
    private final CompanyService companyService;
    private final CarService carService;

    public CustomerService(CustomerDao customerDao, CompanyService companyService, CarService carService) {
        this.customerDao = customerDao;
        this.companyService = companyService;
        this.carService = carService;
    }

    public void loginCustomer() {
        List<Customer> customers = getAllCustomers();
        if (getAllCustomers().isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        } else {
            System.out.println("Choose a customer:");
            int count = 1;
            for (Customer customer : customers) {
                System.out.println(count + ". " + customer.getName());

                count++;
            }
            System.out.println("0. Back");
            int choiceCustomer = ChooseOption.getScanNumber();
            if (choiceCustomer == 0) {
                return;
            }
            Customer customer = customers.get(choiceCustomer - 1);
            customerMenu(customer);
        }

    }

    public void createCustomer() {
        System.out.println("Enter the customer name:");
        String customerName = ChooseOption.getScanString();
        customerDao.createCustomer(customerName);

    }


    private List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    private void customerMenu(Customer customer) {
        while (true) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            switch (ChooseOption.getScanNumber()) {
                case 1:
                    rentedCar(customer);
                    break;
                case 2:
                    returnRentedCar(customer);
                    break;
                case 3:
                    getCustomerRentedCar(customer);
                    break;
                case 0:
                    return;
            }
        }
    }

    private void getCustomerRentedCar(Customer customer) {
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            customerDao.getCustomerRentedCar(customer.getRentedCarId());
        }
    }


    private void returnRentedCar(Customer customer) {
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            customerDao.returnRentedCar(customer);
            System.out.println("You've returned a rented car!'");

        }
    }

    private void rentedCar(Customer customer) {
        if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!");
        } else {
            List<Company> companyList = companyService.getCompanyList();
            if (companyList.isEmpty()) {
                System.out.println("The company list is empty!");

            } else {
                System.out.println("Choose a company:");
                int count = 1;
                for (Company company : companyList) {
                    System.out.println(count + ". " + company.getName());
                    count++;
                }
                System.out.println("0. Back");
                int numberOfCompany = ChooseOption.getScanNumber();
                if (numberOfCompany == 0) {
                    return;
                }
                Company company = companyList.get(numberOfCompany - 1);
                List<Car> carList =
                        carService.getAllCars(company);
                if (carList.isEmpty()) {
                    System.out.println("The car list is empty!");
                    return;
                } else {
                    System.out.println("Choose a car:");
                    count = 1;
                    for (Car car : carList) {
                        System.out.println(count + ". " + car.getName());
                        count++;
                    }
                    System.out.println("0. Back");
                    int numberCar = ChooseOption.getScanNumber();
                    if (numberCar == 0) {
                        return;
                    } else {
                        Car car = carList.get(numberCar - 1);
                        customerDao.rentedCar(car.getId(), customer);
                        customer.setRentedCarId(car.getId());
                        System.out.println("You rented '" + car.getName() + "'");

                    }

                }

            }
        }

    }
}
