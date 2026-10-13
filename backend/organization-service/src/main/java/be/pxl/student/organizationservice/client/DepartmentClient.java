package be.pxl.student.organizationservice.client;

import be.pxl.student.organizationservice.client.dto.DepartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "department-service")
public interface DepartmentClient {

    @GetMapping("/api/departments/organization/{organizationId}")
    List<DepartmentResponse> getDepartmentsByOrganization(@PathVariable Long organizationId);
}
