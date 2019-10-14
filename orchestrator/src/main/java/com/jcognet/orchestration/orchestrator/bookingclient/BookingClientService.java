package com.jcognet.orchestration.orchestrator.bookingclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jcognet.orchestration.orchestrator.bookingclient.dto.BookingTransaction;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableFeignClients
public class BookingClientService {

    @FeignClient(name = "bookingClient")
    private static interface BookingClient {

        @PostMapping(value = "/bookings", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
        BookingTransaction book(URI serviceURI, BookingTransaction bookingTransaction);

        @DeleteMapping(value = "/bookings/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
        BookingTransaction cancelBooking(URI serviceURI, @PathVariable("transactionId") UUID transactionId);
    }

    @Autowired
    private BookingClient bookingClient;

    /**
     * Book %transactionAmount% units on service %serviceURI% with transaction %transactionId%
     * 
     * @param transactionId
     * @param serviceURI
     * @param transactionAmount
     * @throws URISyntaxException
     */
    public void book(String transactionId, String serviceURI, int transactionAmount) throws URISyntaxException {
        log.info("About to send booking transaction #{} to {} requesting {} units", transactionId, serviceURI,
            transactionAmount);
        BookingTransaction bookingTransaction =
            new BookingTransaction(UUID.fromString(transactionId), transactionAmount);
        try {
            bookingClient.book(new URI(serviceURI), bookingTransaction);
        } catch (FeignException e) {
            throw new BpmnError(e.getMessage());
        }
    }

    /**
     * Cancel transaction %transactionId% on service %serviceURI%
     * 
     * @param transactionId
     * @param serviceURI
     * @throws URISyntaxException
     */
    public void cancelBooking(String transactionId, String serviceURI) throws URISyntaxException {
        log.info("About to cancel booking transaction #{} to {}", transactionId, serviceURI);
        bookingClient.cancelBooking(new URI(serviceURI), UUID.fromString(transactionId));
    }
}
