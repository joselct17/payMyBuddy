package com.joselct17.paymybuddy.repository;

import com.joselct17.paymybuddy.model.BankTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankTransactionRepository extends CrudRepository<BankTransaction, Integer> {

    @Query(value =
            "SELECT * "
                    + "FROM banktransaction t "
                    + "WHERE t.user_id = :userid",
            nativeQuery = true)
    public Page<BankTransaction> findBankTransactionByUserId(@Param("userid") int userid, Pageable pageRequest);
}
