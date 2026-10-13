package be.pxl.student.organizationservice.service;

import be.pxl.student.organizationservice.domain.Organization;
import be.pxl.student.organizationservice.domain.dto.OrganizationRequest;
import be.pxl.student.organizationservice.domain.dto.OrganizationResponse;
import be.pxl.student.organizationservice.repository.OrganizationRepository;
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
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    @Test
    void createOrganization_savesOrganizationWithRequestFields() {
        OrganizationRequest request = new OrganizationRequest();
        request.setName("PXL");

        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization organization = invocation.getArgument(0);
            organization.setId(1L);
            return organization;
        });

        OrganizationResponse response = organizationService.createOrganization(request);

        ArgumentCaptor<Organization> captor = ArgumentCaptor.forClass(Organization.class);
        verify(organizationRepository).save(captor.capture());
        Organization savedOrganization = captor.getValue();

        assertEquals("PXL", savedOrganization.getName());
        assertEquals(1L, response.getId());
    }
}
