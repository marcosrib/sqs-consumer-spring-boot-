package com.sqs.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerListener {
    @JmsListener(destination = "${aws.sqs.consumer.queue}")
    public void receiveMessage(String message) {
     log.info(message);
    }

}
