package com.jcognet.orchestration.bookingservice.service;

import com.jcognet.orchestration.bookingservice.model.BookingTransaction;
import com.jcognet.orchestration.bookingservice.model.StockLevel;
import com.jcognet.orchestration.bookingservice.model.TransactionStatus;
import com.jcognet.orchestration.bookingservice.web.websocket.StateAndTransactions;
import com.jcognet.orchestration.bookingservice.web.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private ArrayList<BookingTransaction> listOfTransactions = new ArrayList<>();
    private StockLevel stockLevel = new StockLevel(0,0);

    @Value("${app.stock}")
    int initialStock;

    @PostConstruct
    public void postConstruct(){
        this.initStock(initialStock);
    }

    @Autowired
    WebSocketService webSocketService;

    private void notifyWebSocketClients(){
        webSocketService.sendNotification(getStateAndTransaction());
    }

    public StateAndTransactions getStateAndTransaction(){
        StateAndTransactions stateAndTransactions = new StateAndTransactions();
        stateAndTransactions.setStockLevel(stockLevel);
        stateAndTransactions.setBookingTransactions(listOfTransactions);
        return stateAndTransactions;
    }

    public StockLevel initStock(int stockLevel) {
        this.listOfTransactions.clear();
        this.stockLevel.setCurrentStockLevel(stockLevel);
        this.stockLevel.setMaxStockLevel(stockLevel);
        this.notifyWebSocketClients();
        return this.stockLevel;
    }

    public StockLevel getStockLevel(){
        return this.stockLevel;
    }

    public List<BookingTransaction> getListOfTransactions(){
        return listOfTransactions;
    }

    public StockLevel addBooking(BookingTransaction bookingTransaction) throws BookingError {
        if (bookingTransaction.getTransactionAmount() <= 0) {
            throw new BookingError("Amount of transaction must be positive");
        }
        if (stockLevel.getCurrentStockLevel()- bookingTransaction.getTransactionAmount() < 0) {
            throw new BookingError("Not enough stock to satisfy your reservation");
        }
        long nbTransactionsWithSameId = listOfTransactions
                .stream()
                .filter(tr -> tr.getGuid().equals(bookingTransaction.getGuid()))
                .count();
        if (nbTransactionsWithSameId>0){
            throw new BookingError("Transaction exists yet");
        }
        bookingTransaction.setTransactionStatus(TransactionStatus.ACTIVE);
        bookingTransaction.setTransactionDate(LocalDateTime.now());
        listOfTransactions.add(bookingTransaction);
        stockLevel.setCurrentStockLevel(stockLevel.getCurrentStockLevel()-bookingTransaction.getTransactionAmount());
        this.notifyWebSocketClients();
        return stockLevel;
    }

    public StockLevel removeBooking(UUID transactionId) throws BookingError{
        BookingTransaction bookingTransaction = listOfTransactions
                .stream()
                .filter(tr -> tr.getGuid().equals(transactionId))
                .findFirst()
                .orElseThrow( () -> new BookingError("Transaction not found"));
        if (bookingTransaction.getTransactionStatus().equals(TransactionStatus.CANCELLED)){
            throw new BookingError("Transaction is yet cancelled");
        }
        stockLevel.setCurrentStockLevel(stockLevel.getCurrentStockLevel()+ bookingTransaction.getTransactionAmount());
        bookingTransaction.setTransactionStatus(TransactionStatus.CANCELLED);
        this.notifyWebSocketClients();
        return stockLevel;
    }
}
