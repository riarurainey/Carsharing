package carsharing.dao;

import carsharing.model.Company;

import java.util.List;

public interface CompanyDao {
    List<Company> getAllCompanies();
    void createCompany(String companyName);

}
