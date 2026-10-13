package be.pxl.student.employeeservice.service;

import be.pxl.student.employeeservice.client.NotificationClient;
import be.pxl.student.employeeservice.domain.Employee;
import be.pxl.student.employeeservice.domain.dto.EmployeeRequest;
import be.pxl.student.employeeservice.domain.dto.EmployeeResponse;
import be.pxl.student.employeeservice.repository.EmployeeRepository;
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
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployee_savesEmployeeWithRequestFields() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Yousra");
        request.setLastName("Student");
        request.setEmail("yousra.student@example.com");
        request.setDepartmentId(10L);
        request.setOrganizationId(20L);

        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee employee = invocation.getArgument(0);
            employee.setId(1L);
            return employee;
        });

        EmployeeResponse response = employeeService.createEmployee(request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        verify(notificationClient).sendNotification(any());
        Employee savedEmployee = captor.getValue();

        assertEquals("Yousra", savedEmployee.getFirstName());
        assertEquals("Student", savedEmployee.getLastName());
        assertEquals("yousra.student@example.com", savedEmployee.getEmail());
        assertEquals(10L, savedEmployee.getDepartmentId());
        assertEquals(20L, savedEmployee.getOrganizationId());
        assertEquals(1L, response.getId());
    }
}
