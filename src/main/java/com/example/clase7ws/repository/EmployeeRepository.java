package com.example.clase7ws.repository;

import com.example.clase7ws.entity.Employee;
import com.example.clase7ws.entity.Projections.EmployeeWithId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "employee", excerptProjection = EmployeeWithId.class)
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

    @Query("SELECT e FROM Employee e WHERE e.manager.id = :managerId")
    List<Employee> findEmployeesByManagerId(@Param("managerId") Integer managerId);

    Optional<Employee> findById(Integer id);

    @Query("SELECT e FROM Employee e WHERE e.id = :employeeId AND e.manager.id = :managerId")
    Optional<Employee> findEmployeeByIdAndManagerId(@Param("employeeId") Integer employeeId, @Param("managerId") Integer managerId);

    @Modifying
    @Query("UPDATE Employee e SET e.meeting = '1' WHERE e.id = :employeeId AND e.meeting = '0'")
    int updateMeeting(@Param("employeeId") Integer employeeId);

    @Query("SELECT e.meetingDate FROM Employee e WHERE e.id = :employeeId AND e.meeting = '1'")
    Optional<LocalDateTime> findMeetingDateByEmployeeId(@Param("employeeId") Integer employeeId);
}


