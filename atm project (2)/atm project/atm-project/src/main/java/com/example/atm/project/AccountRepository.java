package com.example.atm.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    // Removed custom finder, using findById for accountNumber since it's the ID
}
