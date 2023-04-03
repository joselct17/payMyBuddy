package com.joselct17.paymybuddy.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//child entity, owner of the relationship
    @JoinColumn(name = "usersource_id", nullable = false)
    private User userSource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//child entity, owner of the relationship
    @JoinColumn(name = "userdestination_id", nullable = false)
    private User userDestination;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "fees")
    private BigDecimal fees;

    @Column(name = "description")
    private String description;


}
