package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    @Autowired
    private TransactionRepository transactionRepo;

    // ✅ Create account
    public Account create(Account acc) {
        return repo.save(acc);
    }

    // ✅ Get all accounts
    public List<Account> getAll() {
        return repo.findAll();
    }

    // ✅ Deposit
    public Account deposit(Long id, double amount) {
        Account acc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBalance(acc.getBalance() + amount);

        Transaction t = new Transaction();
        t.setToAccountId(id);
        t.setAmount(amount);
        t.setType("DEPOSIT");
        t.setDate(LocalDateTime.now());

        transactionRepo.save(t);

        return repo.save(acc);
    }

    // ✅ Withdraw
    public Account withdraw(Long id, double amount) {
        Account acc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - amount);

        Transaction t = new Transaction();
        t.setFromAccountId(id);
        t.setAmount(amount);
        t.setType("WITHDRAW");
        t.setDate(LocalDateTime.now());

        transactionRepo.save(t);

        return repo.save(acc);
    }

    // ✅ Transfer
    public String transfer(Long fromId, Long toId, double amount) {

        if (fromId.equals(toId)) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Account sender = repo.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = repo.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        repo.save(sender);
        repo.save(receiver);

        Transaction t = new Transaction();
        t.setFromAccountId(fromId);
        t.setToAccountId(toId);
        t.setAmount(amount);
        t.setType("TRANSFER");
        t.setDate(LocalDateTime.now());

        transactionRepo.save(t);

        return "Transfer successful";
    }

    // ✅ Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    // ✅ 🔥 IMPORTANT: Get user-specific transactions (THIS WAS MISSING)
    public List<Transaction> getUserTransactions(Long id) {
        return transactionRepo.findByFromAccountIdOrToAccountId(id, id);
    }
}