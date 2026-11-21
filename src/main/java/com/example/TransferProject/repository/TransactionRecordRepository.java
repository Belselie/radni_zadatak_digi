package com.example.TransferProject.repository;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.orm.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {

    @Query("select tr from TransactionRecord tr where tr.transactionId = :transactionId LIMIT 1")
    Optional<TransactionRecord> findById(@Param("transactionId") String transactionId);
}
