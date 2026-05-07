package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AccountRepository repo;

    // ✅ Get all accounts
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {

        return repo.findAll();
    }

    // ✅ Freeze account
    @PutMapping("/freeze/{id}")
    public String freezeAccount(
            @PathVariable Long id) {

        Account acc = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Account not found"));

        acc.setFrozen(true);

        repo.save(acc);

        return "Account frozen";
    }

    // ✅ Unfreeze account
    @PutMapping("/unfreeze/{id}")
    public String unfreezeAccount(
            @PathVariable Long id) {

        Account acc = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Account not found"));

        acc.setFrozen(false);

        repo.save(acc);

        return "Account unfrozen";
    }

    // ✅ Delete account
    @DeleteMapping("/delete/{id}")
    public String deleteAccount(
            @PathVariable Long id) {

        repo.deleteById(id);

        return "Account deleted";
    }
}