package com.example.TransferProject.orm;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "balance_rsd", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance_rsd;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalanceRsd() {
        return balance_rsd;
    }
    public void setBalance(BigDecimal balance_rsd) {
        this.balance_rsd = balance_rsd;
    }
}