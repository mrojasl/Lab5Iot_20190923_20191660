package com.example.clase7ws.entity.Projections;

import com.example.clase7ws.entity.Job;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.Column;

@Projection(name = "JobWithId", types = {Job.class})
public interface JobWithId {
    String getId();
    String getJobTitle();
    Integer getMinSalary();
    Integer getMaxSalary();
}
