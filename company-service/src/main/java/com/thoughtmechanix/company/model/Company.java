package com.thoughtmechanix.company.model;

import javax.persistence.*;
import java.time.LocalDateTime;


@Table(name = "companys_table")
@Entity
public class Company {

    @Id
    @Column(name= "company_id", nullable= false)
    private String companyId;

    @Column(name = "company_name", nullable= false)
    private String companyName;

    @Column(name = "year_established")
    private String yearEstablished;

    @Column(name = "number_of_employees")
    private Integer numOfEmployees;

    @Column(name = "capital")
    private double capital;

    public Company() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(String yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public Integer getNumOfEmployees() {
        return numOfEmployees;
    }

    public void setNumOfEmployees(Integer numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }
}
