package com.example.atm.project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account login(String accountNumber, String pin) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
        if (optionalAccount.isPresent() && optionalAccount.get().getPin().equals(pin)) {
            return optionalAccount.get();
        }
        return null;
    }

    public double checkBalance(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
        return optionalAccount.map(Account::getBalance).orElse(0.0);
    }

    public boolean withdraw(String accountNumber, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getBalance() >= amount && amount <= account.getDailyLimit()) {
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
                TransactionRecord transaction = new TransactionRecord(accountNumber, "WITHDRAWAL", amount, LocalDateTime.now());
                transactionRepository.save(transaction);
                return true;
            }
        }
        return false;
    }

    public boolean deposit(String accountNumber, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
            TransactionRecord transaction = new TransactionRecord(accountNumber, "DEPOSIT", amount, LocalDateTime.now());
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }

    public boolean setDailyLimit(String accountNumber, double dailyLimit) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setDailyLimit(dailyLimit);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public List<TransactionRecord> getTransactionHistory(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}
