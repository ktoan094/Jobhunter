package vn.hoidanit.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.RestResponse;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.Error.IdInvalidException;
import vn.hoidanit.jobhunter.util.annontation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Get CompanyById
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") long id) {
        Company fetchCompany = this.companyService.fetchCompanyByID(id);
        // return ResponseEntity.ok(fetchUser);
        return ResponseEntity.status(HttpStatus.OK).body(fetchCompany);
    }

    // Get All companies
    @GetMapping("/companies")
    @ApiMessage("fetch all companies")
    public ResponseEntity<ResultPaginationDTO> getAllCompanies(@Filter Specification<Company> spec, Pageable pageable) {
        return ResponseEntity.ok(this.companyService.fetchAllCompany(spec, pageable));
    }

    // Create new Company
    @PostMapping("/companies")
    public ResponseEntity<Company> createNewCompany(@Valid @RequestBody Company company) {
        Company newCompany = this.companyService.handleCreateCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) {
        Company newCompany = this.companyService.handleUpdateCompany(company);
        return ResponseEntity.status(HttpStatus.OK).body(newCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<RestResponse> deleteCompany(@PathVariable("id") Long id) throws IdInvalidException {
        if (id > 100) {
            throw new IdInvalidException("id không được lớn hơn 100");
        }
        this.companyService.hanldeDeleteCompanyById(id);
        RestResponse res = new RestResponse();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
