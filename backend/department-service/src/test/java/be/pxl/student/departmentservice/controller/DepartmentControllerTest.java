package be.pxl.student.departmentservice.controller;

import be.pxl.student.departmentservice.domain.dto.DepartmentResponse;
import be.pxl.student.departmentservice.service.DepartmentService;
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

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Test
    void addDepartment_withValidBody_returns201() throws Exception {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(1L);
        response.setName("IT");
        response.setOrganizationId(20L);

        when(departmentService.createDepartment(any())).thenReturn(response);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "IT",
                                  "organizationId": 20
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void addDepartment_withInvalidBody_returns400AndDoesNotCallService() throws Exception {
        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "organizationId": 20
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(departmentService, never()).createDepartment(any());
    }
}
