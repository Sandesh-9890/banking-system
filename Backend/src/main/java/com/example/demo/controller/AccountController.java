package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.service.AccountService;
import com.example.demo.repository.UserRepository;
import java.security.Principal;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountService service;

    // ✅ Create account
    @PostMapping
    public Account create(@RequestBody Account acc) {
        return service.create(acc);
    }

    // ✅ Deposit
    @PostMapping("/deposit")
    public Account deposit(@RequestParam Long id, @RequestParam double amount) {
        return service.deposit(id, amount);
    }

    // ✅ Withdraw (no try-catch now)
    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam Long id, @RequestParam double amount) {
        return service.withdraw(id, amount);
    }

    // ✅ Transfer (FIXED)
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam double amount) {
        return service.transfer(fromId, toId, amount);
    }

    // ✅ Get all accounts
    @GetMapping
    public List<Account> getAll() {
        return service.getAll();
    }

    // ✅ Get all transactions
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    // ✅ Get transactions of specific user (FIXED)
    @GetMapping("/{id}/transactions")
    public List<Transaction> getUserTransactions(@PathVariable Long id) {
        return service.getUserTransactions(id);
    }

    @GetMapping("/my-account")
    public Account getMyAccount(
            Principal principal) {

        return service.getMyAccount(
                principal.getName());
    }
}