package com.example.clase7ws.repository;

import com.example.clase7ws.entity.Job;
import com.example.clase7ws.entity.Projections.JobWithId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "job",excerptProjection = JobWithId.class)
public interface JobRepository extends JpaRepository<Job, String> {
}
