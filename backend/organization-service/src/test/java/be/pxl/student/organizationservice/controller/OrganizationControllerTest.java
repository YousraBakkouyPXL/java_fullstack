package be.pxl.student.organizationservice.controller;

import be.pxl.student.organizationservice.domain.dto.OrganizationResponse;
import be.pxl.student.organizationservice.service.OrganizationService;
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

@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganizationService organizationService;

    @Test
    void addOrganization_withValidBody_returns201() throws Exception {
        OrganizationResponse response = new OrganizationResponse();
        response.setId(1L);
        response.setName("PXL");

        when(organizationService.createOrganization(any())).thenReturn(response);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "PXL"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void addOrganization_withInvalidBody_returns400AndDoesNotCallService() throws Exception {
        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": ""
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(organizationService, never()).createOrganization(any());
    }
}
