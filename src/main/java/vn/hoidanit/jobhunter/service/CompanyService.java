package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company fetchCompanyByID(Long id) {
        Optional<Company> company = this.companyRepository.findById(id);
        if (company.isPresent()) {
            return company.get();
        }
        return null;
    }

    public List<Company> fetchAllCompany() {
        return this.companyRepository.findAll();
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Company handleUpdateCompany(Company company) {
        Company currentCompany = this.fetchCompanyByID(company.getId());
        if (currentCompany != null) {
            currentCompany.setName(company.getName());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setAddress(company.getAddress());
            currentCompany.setLogo(company.getLogo());
            currentCompany = this.companyRepository.save(currentCompany);
        }
        return currentCompany;
    }

    public void hanldeDeleteCompanyById(Long id) {
        this.companyRepository.deleteById(id);
    }

}
