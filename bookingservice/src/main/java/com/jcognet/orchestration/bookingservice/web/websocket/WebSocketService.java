package com.jcognet.orchestration.bookingservice.web.websocket;

import com.jcognet.orchestration.bookingservice.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    BookingService bookingService;

    public void sendNotification(StateAndTransactions stateAndTransactions){
        log.info("Sent notification");
        webSocket.convertAndSend("/topic/stateAndTransactions",stateAndTransactions);
    }

    @MessageMapping("/topic/subscribe")
    public void subscribeToStateAndTransactionsUpdate() {
        log.info("Received subscription");
        sendNotification(bookingService.getStateAndTransaction());
    }

}
