package com.somelogs.rabbitmq.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
@MapperScan("com.somelogs.rabbitmq.consumer.dal.mapper")
public class RabbitmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqConsumerApplication.class, args);
	}
}
