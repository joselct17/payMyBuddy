package com.joselct17.paymybuddy.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "banktransaction")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankTransaction_id")
    private Integer id;

    @Column(name = "dateTime")
    private String dateTime;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "currency")
    private String currency;

}
