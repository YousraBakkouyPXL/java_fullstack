package be.pxl.student.departmentservice.service;

import be.pxl.student.departmentservice.domain.Department;
import be.pxl.student.departmentservice.domain.dto.DepartmentRequest;
import be.pxl.student.departmentservice.domain.dto.DepartmentResponse;
import be.pxl.student.departmentservice.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = new Department();
        updateDepartmentFields(department, request);
        return mapToResponse(departmentRepository.save(department));
    }

    public DepartmentResponse getDepartment(Long id) {
        return mapToResponse(findDepartment(id));
    }

    public List<DepartmentResponse> getDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<DepartmentResponse> getDepartmentsByOrganization(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department department = findDepartment(id);
        updateDepartmentFields(department, request);
        return mapToResponse(departmentRepository.save(department));
    }

    public void deleteDepartment(Long id) {
        Department department = findDepartment(id);
        departmentRepository.delete(department);
    }

    private Department findDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
    }

    private void updateDepartmentFields(Department department, DepartmentRequest request) {
        department.setName(request.getName());
        department.setOrganizationId(request.getOrganizationId());
    }

    private DepartmentResponse mapToResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setOrganizationId(department.getOrganizationId());
        return response;
    }
}
