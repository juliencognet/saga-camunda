package com.jcognet.orchestration.bookingservice.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingTransaction {
    UUID guid;
    TransactionStatus transactionStatus;
    int transactionAmount;
    LocalDateTime transactionDate;
}
