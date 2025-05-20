package com.globant.academy.service;
import com.globant.academy.config.RabbitMQConfig;
import com.globant.academy.model.Message;
import com.globant.academy.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class NotificationService {

   private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);


    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void receive (Notification notification){
        LOGGER.info("Received Notification: {}", notification);
            sendEmail(notification);
            sendText(notification);
    }

    public void sendEmail(Notification notification){

        LOGGER.info("Sending email to "+ notification.getEmail()+ " "+ getMessage(notification));
    }

    public void sendText(Notification notification){

        LOGGER.info("Sending text to "+ notification.getPhone()+ " " + getMessage(notification));
    }

    public String getMessage(Notification notification){
        switch (notification.getMessage()) {
            case BIRTHDAY:
                return "Happy Birthday " + notification.getNombre() + "!!!";
            case CART_SUBMITTED:
                return "Cart submitted successfully";
            default:
                return "Unknown notification type";
        }
    }
}
