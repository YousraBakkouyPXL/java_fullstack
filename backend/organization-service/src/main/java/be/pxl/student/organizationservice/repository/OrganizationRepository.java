package be.pxl.student.organizationservice.repository;

import be.pxl.student.organizationservice.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
