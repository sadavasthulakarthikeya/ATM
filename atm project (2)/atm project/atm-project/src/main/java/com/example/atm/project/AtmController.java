package com.example.atm.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin(origins = "*")
public class AtmController {

    @Autowired
    private AtmService atmService;

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody LoginRequest loginRequest) {
        Account account = atmService.login(loginRequest.getAccountNumber(), loginRequest.getPin());
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> checkBalance(@PathVariable String accountNumber) {
        double balance = atmService.checkBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestBody AmountRequest amountRequest) {
        boolean success = atmService.withdraw(accountNumber, amountRequest.getAmount());
        if (success) {
            return ResponseEntity.ok("Withdrawal successful");
        }
        return ResponseEntity.badRequest().body("Withdrawal failed");
    }

    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestBody AmountRequest amountRequest) {
        boolean success = atmService.deposit(accountNumber, amountRequest.getAmount());
        if (success) {
            return ResponseEntity.ok("Deposit successful");
        }
        return ResponseEntity.badRequest().body("Deposit failed");
    }

    @PostMapping("/set-limit/{accountNumber}")
    public ResponseEntity<String> setDailyLimit(@PathVariable String accountNumber, @RequestBody LimitRequest limitRequest) {
        boolean success = atmService.setDailyLimit(accountNumber, limitRequest.getDailyLimit());
        if (success) {
            return ResponseEntity.ok("Daily limit set successfully");
        }
        return ResponseEntity.badRequest().body("Failed to set daily limit");
    }

    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<TransactionRecord>> getTransactionHistory(@PathVariable String accountNumber) {
        List<TransactionRecord> transactions = atmService.getTransactionHistory(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}
