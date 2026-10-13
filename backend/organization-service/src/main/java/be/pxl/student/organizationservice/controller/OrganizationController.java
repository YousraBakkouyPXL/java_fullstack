package be.pxl.student.organizationservice.controller;

import be.pxl.student.organizationservice.client.DepartmentClient;
import be.pxl.student.organizationservice.client.EmployeeClient;
import be.pxl.student.organizationservice.domain.dto.OrganizationRequest;
import be.pxl.student.organizationservice.domain.dto.OrganizationResponse;
import be.pxl.student.organizationservice.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final DepartmentClient departmentClient;
    private final EmployeeClient employeeClient;

    public OrganizationController(OrganizationService organizationService, DepartmentClient departmentClient, EmployeeClient employeeClient) {
        this.organizationService = organizationService;
        this.departmentClient = departmentClient;
        this.employeeClient = employeeClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationResponse addOrganization(@Valid @RequestBody OrganizationRequest request) {
        return organizationService.createOrganization(request);
    }

    @GetMapping("/{id}")
    public OrganizationResponse getOrganization(@PathVariable Long id) {
        return organizationService.getOrganization(id);
    }

    @GetMapping
    public List<OrganizationResponse> getOrganizations() {
        return organizationService.getOrganizations();
    }

    @GetMapping("/{id}/with-departments")
    public Map<String, Object> getOrganizationWithDepartments(@PathVariable Long id) {
        return Map.of(
                "organization", organizationService.getOrganization(id),
                "departments", departmentClient.getDepartmentsByOrganization(id)
        );
    }

    @GetMapping("/{id}/with-employees")
    public Map<String, Object> getOrganizationWithEmployees(@PathVariable Long id) {
        return Map.of(
                "organization", organizationService.getOrganization(id),
                "employees", employeeClient.getEmployeesByOrganization(id)
        );
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public Map<String, Object> getOrganizationWithDepartmentsAndEmployees(@PathVariable Long id) {
        return Map.of(
                "organization", organizationService.getOrganization(id),
                "departments", departmentClient.getDepartmentsByOrganization(id),
                "employees", employeeClient.getEmployeesByOrganization(id)
        );
    }

    @PutMapping("/{id}")
    public OrganizationResponse updateOrganization(@PathVariable Long id, @Valid @RequestBody OrganizationRequest request) {
        return organizationService.updateOrganization(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
    }
}
