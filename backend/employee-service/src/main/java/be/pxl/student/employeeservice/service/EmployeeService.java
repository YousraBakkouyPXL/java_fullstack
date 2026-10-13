package be.pxl.student.employeeservice.service;

import be.pxl.student.employeeservice.client.NotificationClient;
import be.pxl.student.employeeservice.client.dto.NotificationRequest;
import be.pxl.student.employeeservice.domain.Employee;
import be.pxl.student.employeeservice.domain.dto.EmployeeRequest;
import be.pxl.student.employeeservice.domain.dto.EmployeeResponse;
import be.pxl.student.employeeservice.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

    public EmployeeService(EmployeeRepository employeeRepository, NotificationClient notificationClient) {
        this.employeeRepository = employeeRepository;
        this.notificationClient = notificationClient;
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        updateEmployeeFields(employee, request);
        Employee savedEmployee = employeeRepository.save(employee);
        notificationClient.sendNotification(new NotificationRequest("Employee created: " + savedEmployee.getId()));
        return mapToResponse(savedEmployee);
    }

    public EmployeeResponse getEmployee(Long id) {
        return mapToResponse(findEmployee(id));
    }

    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<EmployeeResponse> getEmployeesByOrganization(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = findEmployee(id);
        updateEmployeeFields(employee, request);
        return mapToResponse(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        Employee employee = findEmployee(id);
        employeeRepository.delete(employee);
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    private void updateEmployeeFields(Employee employee, EmployeeRequest request) {
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartmentId(request.getDepartmentId());
        employee.setOrganizationId(request.getOrganizationId());
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setDepartmentId(employee.getDepartmentId());
        response.setOrganizationId(employee.getOrganizationId());
        return response;
    }
}
