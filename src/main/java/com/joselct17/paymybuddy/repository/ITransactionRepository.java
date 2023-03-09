package com.joselct17.paymybuddy.repository;

import com.joselct17.paymybuddy.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends CrudRepository<Transaction, Integer> {
}
