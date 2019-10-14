package com.jcognet.orchestration.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockLevel {
    int maxStockLevel;
    int currentStockLevel;
}
