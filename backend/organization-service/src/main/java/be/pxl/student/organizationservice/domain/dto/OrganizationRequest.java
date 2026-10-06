package be.pxl.student.organizationservice.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class OrganizationRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
