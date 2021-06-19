package com.sandeepkrjsr.loans;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanApplication, Integer> {

}
