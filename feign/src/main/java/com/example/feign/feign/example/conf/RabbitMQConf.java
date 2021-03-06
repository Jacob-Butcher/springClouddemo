package com.example.feign.feign.example.conf;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by littlersmall on 16/5/16.
 */
@Configuration
public class RabbitMQConf {
    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory() {
        Properties properties = new Properties();
        System.out.println("load conectionFactory");
        try {
            Resource res = new ClassPathResource("rabbitmq.properties");
            properties.load(res.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rabbitmq.properties!");
        }

        String ip = properties.getProperty("ip");
        int port = Integer.parseInt(properties.getProperty("port"));
        String userName = properties.getProperty("user_name");
        String password = properties.getProperty("password");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(ip, port);

        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(true); // enable confirm mode
        //connectionFactory.getRabbitConnectionFactory().setAutomaticRecoveryEnabled(true);

        return connectionFactory;
    }
}
