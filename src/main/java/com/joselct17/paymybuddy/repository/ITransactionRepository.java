package com.joselct17.paymybuddy.repository;

import com.joselct17.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query(value =
            "SELECT * "
                    + "FROM transaction "
                    + "WHERE usersource_id = :usersourceid OR userdestination_id = :usersourceid "
            ,
            nativeQuery = true)
    Page<Transaction> findUserTransactionByUserId(@Param("usersourceid") int usersourceid, Pageable pageRequest);
}
