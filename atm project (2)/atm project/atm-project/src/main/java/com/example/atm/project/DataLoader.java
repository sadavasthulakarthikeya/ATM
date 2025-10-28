package com.example.atm.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create sample accounts for testing with 16-digit account numbers
        Account account1 = new Account("1234567890123456", "1234", 1000.0, 500.0);
        accountRepository.save(account1);
        Account account2 = new Account("5940955036312345", "1234", 1000.0, 500.0);
        accountRepository.save(account2);
    }
}
