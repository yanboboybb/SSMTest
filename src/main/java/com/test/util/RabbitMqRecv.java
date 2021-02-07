package com.test.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Base64;

/**
 * @author: yanbobo
 * @DATE: 2021/2/7 19:50
 * @description:
 */
public class RabbitMqRecv {

    private final static String QUEUE_NAME = "oa_notice";
    private final static String HOST = "127.0.0.1";
    private final static String username = "test";
    private final static String password = "test123";
    private final static Integer PORT = 5672;

    private final static String CHAR_SET = "UTF-8";

    public static boolean getMessage(boolean base64) {
        ConnectionFactory factory = null;
        Connection connection = null;
        Channel channel = null;
        try {
            factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername(username);
            factory.setPassword(password);
            factory = new ConnectionFactory();
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), CHAR_SET);

                if (base64) {
                    message = new String(Base64.getDecoder().decode(delivery.getBody()),CHAR_SET);
                }
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        getMessage(false);
    }
}
