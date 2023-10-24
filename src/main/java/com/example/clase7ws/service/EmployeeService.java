package com.example.clase7ws.service;

import com.example.clase7ws.entity.Employee;
import com.example.clase7ws.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findEmployeesByManagerId(Integer managerId) {
        return employeeRepository.findEmployeesByManagerId(managerId);
    }

    public Optional<Employee> findEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public String updateMeeting(Integer managerId, Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findEmployeeByIdAndManagerId(employeeId, managerId);
        if (!employee.isPresent()) {
            return "El manager no corresponde al empleado";
        }
        int updatedRows = employeeRepository.updateMeeting(employeeId);
        if (updatedRows == 0) {
            return "El trabajador ya tiene una cita asignada. Elija otro trabajador";
        }
        return "Se actualizó la cita";
    }

    public LocalDateTime findMeetingDateByEmployeeId(Integer employeeId) {
        return employeeRepository.findMeetingDateByEmployeeId(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El empleado no tiene una cita o no existe"));
    }

    public Employee updateEmployeeFeedback(Integer employeeId, String feedback) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El empleado no existe"));

        if (!"1".equals(employee.getMeeting())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado no tiene una cita asignada");
        }

        if (feedback.length() > 200) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El feedback no puede tener más de 200 caracteres");
        }

        employee.setEmployeeFeedback(feedback);
        return employeeRepository.save(employee);
    }

}