package com.jcognet.orchestration.bookingservice.web.websocket;

import com.jcognet.orchestration.bookingservice.model.BookingTransaction;
import com.jcognet.orchestration.bookingservice.model.StockLevel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StateAndTransactions {
    StockLevel stockLevel;
    List<BookingTransaction> bookingTransactions = new ArrayList<>();
}
