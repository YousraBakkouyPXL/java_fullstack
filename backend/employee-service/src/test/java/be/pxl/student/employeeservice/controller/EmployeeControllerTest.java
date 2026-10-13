package be.pxl.student.employeeservice.controller;

import be.pxl.student.employeeservice.domain.dto.EmployeeResponse;
import be.pxl.student.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void addEmployee_withValidBody_returns201() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setFirstName("Yousra");
        response.setLastName("Student");
        response.setEmail("yousra.student@example.com");
        response.setDepartmentId(10L);
        response.setOrganizationId(20L);

        when(employeeService.createEmployee(any())).thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Yousra",
                                  "lastName": "Student",
                                  "email": "yousra.student@example.com",
                                  "departmentId": 10,
                                  "organizationId": 20
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void addEmployee_withInvalidBody_returns400AndDoesNotCallService() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "",
                                  "lastName": "Student",
                                  "email": "not-an-email",
                                  "departmentId": 10,
                                  "organizationId": 20
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).createEmployee(any());
    }
}
