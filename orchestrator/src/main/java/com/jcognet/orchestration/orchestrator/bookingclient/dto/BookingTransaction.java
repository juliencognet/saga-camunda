package com.jcognet.orchestration.orchestrator.bookingclient.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class BookingTransaction {
    @NonNull
    UUID guid;
    @NonNull
    int transactionAmount;
    TransactionStatus transactionStatus;
    LocalDateTime transactionDate;
}
