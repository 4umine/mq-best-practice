package com.somelogs.rabbitmq.consumer.listener;

import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Rabbitmq listener
 *
 * @author LBG - 2021/11/25
 */
@Component
@Configuration
public class RabbitConsumer {

	@RabbitListener(queues = RabbitmqConstant.Direct.QUEUE)
	public void directQueue(String in) {
		System.out.println("Message read from myQueue : " + in);
	}

	@Bean
	public Queue directQueue() {
		return new Queue(RabbitmqConstant.Direct.QUEUE, true, false, false);
	}
}
