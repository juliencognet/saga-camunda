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
import java.util.UUID;

@Slf4j
@RestController
@Component
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class StockResource {

    @Autowired
    BookingService bookingService;

    @PostMapping(path = "/stock")
    public ResponseEntity<StockLevel> initStock(@RequestParam("stockLevel") int stockLevel) {
        log.info("Init stock to {}", stockLevel);
        return ResponseEntity.ok(bookingService.initStock(stockLevel));
    }

    @GetMapping(path = "/stock")
    public ResponseEntity<StockLevel> getStock(){
        return ResponseEntity.ok(bookingService.getStockLevel());
    }
}
