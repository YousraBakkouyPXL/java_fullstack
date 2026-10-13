package be.pxl.student.departmentservice.service;

import be.pxl.student.departmentservice.domain.Department;
import be.pxl.student.departmentservice.domain.dto.DepartmentRequest;
import be.pxl.student.departmentservice.domain.dto.DepartmentResponse;
import be.pxl.student.departmentservice.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void createDepartment_savesDepartmentWithRequestFields() {
        DepartmentRequest request = new DepartmentRequest();
        request.setName("IT");
        request.setOrganizationId(20L);

        when(departmentRepository.save(any(Department.class))).thenAnswer(invocation -> {
            Department department = invocation.getArgument(0);
            department.setId(1L);
            return department;
        });

        DepartmentResponse response = departmentService.createDepartment(request);

        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        verify(departmentRepository).save(captor.capture());
        Department savedDepartment = captor.getValue();

        assertEquals("IT", savedDepartment.getName());
        assertEquals(20L, savedDepartment.getOrganizationId());
        assertEquals(1L, response.getId());
    }
}
