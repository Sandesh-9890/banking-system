package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.TransactionRepository;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @PositiveOrZero(message = "Balance cannot be negative")
    private double balance;
    

    // getters and setters

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}