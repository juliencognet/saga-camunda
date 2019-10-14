package com.jcognet.orchestration.bookingservice.web.rest;

import com.jcognet.orchestration.bookingservice.model.BookingTransaction;
import com.jcognet.orchestration.bookingservice.model.StockLevel;
import com.jcognet.orchestration.bookingservice.service.BookingError;
import com.jcognet.orchestration.bookingservice.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Component
@RequestMapping("/api")
public class BookingResource {

    @Autowired
    BookingService bookingService;

    @GetMapping(path = "/bookings")
    public ResponseEntity<List<BookingTransaction>> getTransactions() {
        log.debug("Calling getStockLevel REST API");
        return ResponseEntity.ok(bookingService.getListOfTransactions());
    }

    @PostMapping(path = "/bookings")
    public ResponseEntity<StockLevel> book(@RequestBody BookingTransaction bookingTransaction) throws BookingError {
        log.info("Added transaction {}", bookingTransaction);
        return ResponseEntity.ok(bookingService.addBooking(bookingTransaction));
    }

    @DeleteMapping(path = "/bookings/{transactionId}")
    public ResponseEntity<StockLevel> removeBooking(@PathVariable("transactionId") UUID transactionId) throws BookingError {
        log.info("Remove booking transaction {}", transactionId);
        return ResponseEntity.ok(bookingService.removeBooking(transactionId));
    }
}
