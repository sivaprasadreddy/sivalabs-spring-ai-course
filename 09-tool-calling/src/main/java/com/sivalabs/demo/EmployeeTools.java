package com.sivalabs.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeTools {
    private static final Logger log = LoggerFactory.getLogger(EmployeeTools.class);
    private final EmployeeService employeeService;

    public EmployeeTools(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Tool(description = "Get employee details for a given employee id of SivaLabs company")
    public Employee getEmployee(String empId) {
        log.info("Getting employee: {}", empId);
        Employee employee = employeeService.getEmployee(empId);
        log.info("Employee: {}", employee);
        return employee;
    }

    @Tool(description = "Find employees of SivaLabs company who are on leave for a given date in YYYY-MM-DD format")
    public List<Employee> findEmployeesOnLeave(LocalDate date) {
        log.info("Finding employees on leave for date: {}", date);
        List<Employee> employeesOnLeave = employeeService.findEmployeesOnLeave(date);
        log.info("Employees on leave: {} on date {}", employeesOnLeave, date);
        return employeesOnLeave;
    }

    @Tool(description = "Apply leave for a given employee id of SivaLabs company and date in YYYY-MM-DD format")
    public void applyLeave(String empId, LocalDate date) {
        log.info("Applying leave for employee: {} on date: {}", empId, date);
        employeeService.applyLeave(empId, date);
    }
}
