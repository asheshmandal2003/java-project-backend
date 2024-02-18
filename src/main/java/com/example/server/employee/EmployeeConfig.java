package com.example.server.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Configuration
public class EmployeeConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            EmployeeRepository employeeRepository){

        return args -> {
            Employee employee1 = new Employee(
                    "Ashesh",
                    "Mandal",
                    "asheshmandal73@gmail.com",
                    new BigInteger("9064094159"),
                    LocalDate.of(2003, Month.APRIL, 29),
                    "India",
                    "Kalyani",
                    "[\"AWS\", \"Full Stack\"]"
            );
            Employee employee2 = new Employee(
                    "Devayan",
                    "Mandal",
                    "devayanm@gmail.com",
                    new BigInteger("1234567890"),
                    LocalDate.of(2004, Month.MARCH, 28),
                    "India",
                    "Chakdaha",
                    "[\"Webservices\"]"
            );
            employeeRepository.saveAll(
                    List.of(employee1, employee2)
            );
        };
    }
}
