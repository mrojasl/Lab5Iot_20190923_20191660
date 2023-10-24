package com.example.clase7ws.entity.Projections;

import com.example.clase7ws.entity.Employee;
import com.example.clase7ws.entity.Job;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.Instant;

@Projection(name = "EmployeeWithId", types = {Employee.class})
public interface EmployeeWithId {

    Integer getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhoneNumber();

    Instant getHireDate();

    Job getJob();

    BigDecimal getSalary();

    BigDecimal getCommissionPct();

    Integer getManagerId();

    Integer getDepartmentId();
}
