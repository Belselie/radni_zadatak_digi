package com.example.TransferProject.tca;

import java.math.BigDecimal;

public abstract class TransactionAction {

    public static class InProgress extends TransactionAction{
        private final Long fromUserId;
        private final Long toUserId;
        private final BigDecimal amount_rsd;

        public InProgress(Long fromUserId, Long toUserId, BigDecimal amount_rsd){
            this.fromUserId = fromUserId;
            this.toUserId = toUserId;
            this.amount_rsd = amount_rsd;
        }

        public Long getFromUserId(){
            return fromUserId;
        }

        public Long getToUserId(){
            return toUserId;
        }

        public BigDecimal getAmountRsd(){
            return amount_rsd;
        }
    }


    public static class Succeeded extends TransactionAction{

    }

    public static class Failed extends TransactionAction{

        private final String failure_reason;

        public Failed(String failure_reason){
            this.failure_reason=failure_reason;
        }

        public String getFailureReason(){
            return failure_reason;
        }
    }

}
