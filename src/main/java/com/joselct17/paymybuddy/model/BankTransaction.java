package com.joselct17.paymybuddy.model;



import javax.persistence.*;


@Entity
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
}
