package be.pxl.student.organizationservice.client;

import be.pxl.student.organizationservice.client.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/api/employees/organization/{organizationId}")
    List<EmployeeResponse> getEmployeesByOrganization(@PathVariable Long organizationId);
}
