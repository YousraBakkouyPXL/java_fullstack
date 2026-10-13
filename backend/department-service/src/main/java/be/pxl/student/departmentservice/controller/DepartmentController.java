package be.pxl.student.departmentservice.controller;

import be.pxl.student.departmentservice.client.EmployeeClient;
import be.pxl.student.departmentservice.domain.dto.DepartmentRequest;
import be.pxl.student.departmentservice.domain.dto.DepartmentResponse;
import be.pxl.student.departmentservice.service.DepartmentService;
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
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeClient employeeClient;

    public DepartmentController(DepartmentService departmentService, EmployeeClient employeeClient) {
        this.departmentService = departmentService;
        this.employeeClient = employeeClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse addDepartment(@Valid @RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    @GetMapping("/{id}")
    public DepartmentResponse getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @GetMapping
    public List<DepartmentResponse> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("/organization/{organizationId}")
    public List<DepartmentResponse> getDepartmentsByOrganization(@PathVariable Long organizationId) {
        return departmentService.getDepartmentsByOrganization(organizationId);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public Map<String, Object> getDepartmentsByOrganizationWithEmployees(@PathVariable Long organizationId) {
        return Map.of(
                "departments", departmentService.getDepartmentsByOrganization(organizationId),
                "employees", employeeClient.getEmployeesByOrganization(organizationId)
        );
    }

    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
