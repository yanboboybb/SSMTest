package com.test.util;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.Base64;

/**
 * @author: yanbobo
 * @DATE: 2021/2/7 17:37
 * @description:
 */
public class RabbitMqSendUtil {
    private final static String QUEUE_NAME = "oa_notice";
    private final static String HOST = "127.0.0.1";
    private final static String username = "test";
    private final static String password = "test123";
    private final static Integer PORT = 5672;

    private final static String CHAR_SET = "UTF-8";

    public static boolean send(String message, boolean base64) {
        ConnectionFactory factory = null;
        Connection connection = null;
        Channel channel = null;
        try {

            if (base64) {
                message = Base64.getEncoder().encodeToString(message.getBytes(CHAR_SET));
            }
            factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername(username);
            factory.setPassword(password);
            factory = new ConnectionFactory();
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(CHAR_SET));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RabbitMqSendUtil.send("nihaiya",false);
    }


}
