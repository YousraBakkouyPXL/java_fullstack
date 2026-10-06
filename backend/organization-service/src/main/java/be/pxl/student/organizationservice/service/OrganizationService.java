package be.pxl.student.organizationservice.service;

import be.pxl.student.organizationservice.domain.Organization;
import be.pxl.student.organizationservice.domain.dto.OrganizationRequest;
import be.pxl.student.organizationservice.domain.dto.OrganizationResponse;
import be.pxl.student.organizationservice.repository.OrganizationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public OrganizationResponse createOrganization(OrganizationRequest request) {
        Organization organization = new Organization();
        updateOrganizationFields(organization, request);
        return mapToResponse(organizationRepository.save(organization));
    }

    public OrganizationResponse getOrganization(Long id) {
        return mapToResponse(findOrganization(id));
    }

    public List<OrganizationResponse> getOrganizations() {
        return organizationRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrganizationResponse updateOrganization(Long id, OrganizationRequest request) {
        Organization organization = findOrganization(id);
        updateOrganizationFields(organization, request);
        return mapToResponse(organizationRepository.save(organization));
    }

    public void deleteOrganization(Long id) {
        Organization organization = findOrganization(id);
        organizationRepository.delete(organization);
    }

    private Organization findOrganization(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found"));
    }

    private void updateOrganizationFields(Organization organization, OrganizationRequest request) {
        organization.setName(request.getName());
    }

    private OrganizationResponse mapToResponse(Organization organization) {
        OrganizationResponse response = new OrganizationResponse();
        response.setId(organization.getId());
        response.setName(organization.getName());
        return response;
    }
}
