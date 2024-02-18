package com.example.server.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {
    private  final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        return  employeeService.getEmployees();
    }

    @PostMapping
    public long addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(
            @PathVariable("employeeId") Long id
    ){
        employeeService.deleteEmployee(id);
    }

    @PutMapping(path = "{employeeId}")
    public void updateEmployee(
            @PathVariable("employeeId") Long id,
            @RequestParam(required = false) String first_name,
            @RequestParam(required = false) String last_name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile_no,
            @RequestParam(name = "date" ,required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dob,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String skills
            ){
        employeeService.updateEmployee(
                id, first_name, last_name, email, new BigInteger(mobile_no), dob, country, city, URLDecoder.decode(skills)
        );
    }

}
