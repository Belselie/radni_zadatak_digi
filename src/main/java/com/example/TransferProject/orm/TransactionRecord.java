package com.example.TransferProject.orm;

import com.example.TransferProject.tca.TransactionStatusNew;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_record")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_account_id", nullable = false)
    private Long fromAccountId;

    @Column(name = "to_account_id",   nullable = false)
    private Long toAccountId;

    @Column(name = "amount_rsd", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatusNew status;

    @Column(name = "fail_reason")
    private String failReason;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public TransactionRecord() {}

    public TransactionRecord(Long fromAccountId, Long toAccountId, BigDecimal amount, TransactionStatusNew status, String failReason) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.status = status;
        this.failReason = failReason;
    }
}
