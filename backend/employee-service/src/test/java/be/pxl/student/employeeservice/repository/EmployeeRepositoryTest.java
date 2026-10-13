package be.pxl.student.employeeservice.repository;

import be.pxl.student.employeeservice.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class EmployeeRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer("mysql:8.4");

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findByDepartmentId_returnsMatchingEmployees() {
        Employee employee = new Employee();
        employee.setFirstName("Yousra");
        employee.setLastName("Student");
        employee.setEmail("yousra.student@example.com");
        employee.setDepartmentId(10L);
        employee.setOrganizationId(20L);
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findByDepartmentId(10L);

        assertEquals(1, employees.size());
        assertEquals("Yousra", employees.getFirst().getFirstName());
    }
}
