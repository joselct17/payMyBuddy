package com.joselct17.paymybuddy.repository;

import com.joselct17.paymybuddy.model.BankTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankTransactionRepository extends CrudRepository<BankTransaction, Integer> {
}
