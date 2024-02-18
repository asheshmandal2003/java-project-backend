package com.example.server.employee;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Long addEmployee(Employee employee){
        Employee newEmployee = employeeRepository.save(employee);
        return  newEmployee.getId();
    }

    public void deleteEmployee(Long employeeId){
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) {
            throw new IllegalStateException("Student with id " + employeeId + " doesn't exists!");
        }
        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public void updateEmployee(
            Long employeeId,
            String first_name,
            String last_name,
            String email,
            BigInteger mobile_no,
            LocalDate dob,
            String country,
            String city,
            String skills
    ){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + employeeId + " doesn't exists!"));
        if (first_name != null
         && !Objects.equals(employee.getFirst_name(), first_name))
            employee.setFirst_name(first_name);
        if (last_name != null
        && !Objects.equals(employee.getLast_name(), last_name))
            employee.setLast_name(last_name);
        if (email != null
        && !Objects.equals(employee.getEmail(), email))
            employee.setEmail(email);
        if (mobile_no != null
        && !Objects.equals(employee.getMobile_no(), mobile_no))
            employee.setMobile_no(mobile_no);
        if (dob != null
        && !Objects.equals(employee.getDob(), dob))
            employee.setDob(dob);
        if (country != null
        && !Objects.equals(employee.getCountry(), country))
            employee.setCountry(country);
        if (city != null
        && !Objects.equals(employee.getCity(), city))
            employee.setCity(city);
        if (skills != null
        && !Objects.equals(employee.getSkills(), skills))
            employee.setSkills(skills);

    }
}
