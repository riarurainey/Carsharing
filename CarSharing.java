package carsharing;

import carsharing.model.Company;
import carsharing.service.CarService;
import carsharing.service.CompanyService;
import carsharing.service.CustomerService;
import carsharing.util.ChooseOption;

import java.util.List;

public class CarSharing {
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final CarService carService;


    public CarSharing(CompanyService companyService, CustomerService customerService, CarService carService) {
        this.companyService = companyService;
        this.customerService = customerService;
        this.carService = carService;


    }

    public void startProgram() {
        mainMenu();
    }

    public void mainMenu() {
        while (true) {
            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");

            switch (ChooseOption.getScanNumber()) {
                case 1:
                    managerMenu();
                    break;
                case 2:
                    customerService.loginCustomer();
                    break;
                case 3:
                    customerService.createCustomer();
                    break;

                case 0:
                    System.exit(0);
                default:
                    System.out.println("No such command!");
            }

        }
    }
    public void companyMenu() {

        List<Company> companyList = companyService.getCompanyList();
        if (companyList.isEmpty()) {
            System.out.println("The company list is empty!");

        } else {
            while (true) {
                System.out.println("Choose a company:");
                int count = 1;
                for (Company company : companyList) {
                    System.out.println(count + ". " + company.getName());
                    count++;
                }

                System.out.println("0. Back");
                int numberOfCompany = ChooseOption.getScanNumber();
                if (numberOfCompany == 0) {
                    managerMenu();
                }
                Company company = companyList.get(numberOfCompany - 1);

               carMenu(company);

            }


        }
    }

    public void managerMenu() {
        while (true) {
            String managerMenu = "1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back";
            System.out.println(managerMenu);


            switch (ChooseOption.getScanNumber()) {
                case 1:
                   companyMenu();
                    break;
                case 2:
                    companyService.createNewCompany();
                    break;
                case 0:
                    mainMenu();
                    break;
            }
        }

    }
    void carMenu(Company company) {

        while (true) {
            System.out.println("'" + company.getName() + "'" + " company");
            System.out.println(
                    "1. Car list\n" +
                            "2. Create a car\n" +
                            "0. Back");


            switch (ChooseOption.getScanNumber()) {
                case 1:
                    carService.getCars(company);
                    break;
                case 2:
                    carService.createCar(company);
                    break;
                case 0:
                    managerMenu();
                    break;

            }
        }
    }

}
