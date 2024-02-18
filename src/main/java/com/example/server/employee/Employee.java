package com.example.server.employee;

import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private long id;
    @Setter
    private String first_name;
    @Setter
    private String last_name;
    @Setter
    private String email;
    @Setter
    private BigInteger mobile_no;
    @Setter
    private LocalDate dob;
    @Setter
    private String country;
    @Setter
    private String city;
    @Setter
    private String skills;

    public Employee
            (
                    String first_name,
                    String last_name,
                    String email,
                    BigInteger mobile_no,
                    LocalDate dob,
                    String country,
                    String city,
                    String skills
            ) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.dob = dob;
        this.country = country;
        this.city = city;
        this.skills = skills;
    }

    public Employee(){}

    public long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public BigInteger getMobile_no() {
        return mobile_no;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getSkills() {
        return skills;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", mobile_no=" + mobile_no +
                ", dob=" + dob +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", skills=" + skills +
                '}';
    }
}
