package com.somelogs.rabbitmq.producer.config;

import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbitmq config
 *
 * @author LBG - 2021/11/25
 */
@Configuration
public class RabbitConfig {

	/**
	 * Direct Exchange
	 */
	public static class DirectExchangeDemoConfiguration {
		@Bean
		public Queue directQueue() {
			return new Queue(RabbitmqConstant.Direct.QUEUE, true, false, false);
		}

		@Bean
		public DirectExchange directExchange() {
			return new DirectExchange(RabbitmqConstant.Direct.EXCHANGE, true, false);
		}

		@Bean
		public Binding directBinding(Queue directQueue, DirectExchange directExchange) {
			return BindingBuilder.bind(directQueue).to(directExchange).with(RabbitmqConstant.Direct.ROUTING_KEY);
		}
	}
}
