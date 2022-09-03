package carsharing.service;

import carsharing.dao.CompanyDao;
import carsharing.model.Company;
import carsharing.util.ChooseOption;

import java.util.List;

public class CompanyService {
    CompanyDao companyDao;
    CarService carService;

    public CompanyService(CompanyDao companyDao, CarService carService) {
        this.companyDao = companyDao;
        this.carService = carService;
    }

    public void createNewCompany() {
        System.out.println("Enter the company name:");
        String companyName = ChooseOption.getScanString();
        companyDao.createCompany(companyName);

    }

    public List<Company> getCompanyList() {
        return companyDao.getAllCompanies();
    }
}





