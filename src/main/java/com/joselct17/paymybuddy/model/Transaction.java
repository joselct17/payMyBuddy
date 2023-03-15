package com.joselct17.paymybuddy.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//child entity, owner of the relationship
    @JoinColumn(name = "usersource_id", nullable = false)
    private User userSource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//child entity, owner of the relationship
    @JoinColumn(name = "userdestination_id", nullable = false)
    private User userDestination;

    @Column(name = "dateTime")
    private String dateTime;

    @Column(name = "amount")
    private Double amount;
    @Column(name = "currency")
    private String currency;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public User getUserSource() {
        return userSource;
    }

    public void setUserSource(User userSource) {
        this.userSource = userSource;
    }

    public User getUserDestination() {
        return userDestination;
    }

    public void setUserDestination(User userDestination) {
        this.userDestination = userDestination;
    }
}
