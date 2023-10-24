package com.example.clase7ws.controller;

import com.example.clase7ws.entity.Employee;
import com.example.clase7ws.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/byManager/{managerId}")
    public List<Employee> findEmployeesByManagerId(@PathVariable Integer managerId) {
        return employeeService.findEmployeesByManagerId(managerId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Integer id) {
        return employeeService.findEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/updateMeeting/{managerId}/{employeeId}")
    public ResponseEntity<String> updateMeeting(@PathVariable Integer managerId, @PathVariable Integer employeeId) {
        String result = employeeService.updateMeeting(managerId, employeeId);
        if (result.equals("Se actualizó la cita")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @GetMapping("/meetingDate/{employeeId}")
    public LocalDateTime findMeetingDateByEmployeeId(@PathVariable Integer employeeId) {
        return employeeService.findMeetingDateByEmployeeId(employeeId);
    }

    @PutMapping("/feedback/{employeeId}")
    public Employee updateEmployeeFeedback(@PathVariable Integer employeeId, @RequestBody String feedback) {
        return employeeService.updateEmployeeFeedback(employeeId, feedback);
    }

}