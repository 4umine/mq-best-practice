package com.somelogs.rabbitmq.producer.config;

import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
	@Bean
	public Declarables directDeclare() {
		Queue queue = new Queue(RabbitmqConstant.Direct.QUEUE, true, false, false);

		Map<String, Object> param = new HashMap<>();
		param.put("alternate-exchange", RabbitmqConstant.Dlx.EXCHANGE);
		DirectExchange directExchange = new DirectExchange(RabbitmqConstant.Direct.EXCHANGE, true, false, param);

		Binding binding = BindingBuilder.bind(queue).to(directExchange).with(RabbitmqConstant.Direct.BINDING_KEY);
		return new Declarables(queue, directExchange, binding);
	}

	/**
	 * Topic Exchange
	 */
	@Bean
	public Declarables topicDeclare() {
		// Exchange 设置备份交换器
		Map<String, Object> param = new HashMap<>();
		param.put("alternate-exchange", RabbitmqConstant.Dlx.EXCHANGE);
		TopicExchange topicExchange = new TopicExchange(RabbitmqConstant.Topic.EXCHANGE, true, false, param);

		// 声明 queues, 并设置死信交换器（DLX）
		Map<String, Object> queueParam = new HashMap<>();
		queueParam.put("x-dead-letter-exchange", RabbitmqConstant.Dlx.EXCHANGE);
		Queue orderQueue = new Queue(RabbitmqConstant.Topic.ORDER_QUEUE, true, false, false, queueParam);
		Queue goodsQueue = new Queue(RabbitmqConstant.Topic.GOODS_QUEUE, true, false, false, queueParam);

		return new Declarables(
				orderQueue,
				goodsQueue,
				topicExchange,
				BindingBuilder
						.bind(orderQueue)
						.to(topicExchange).with(RabbitmqConstant.Topic.ORDER_BINDING_KEY),
				BindingBuilder
						.bind(goodsQueue)
						.to(topicExchange).with(RabbitmqConstant.Topic.GOODS_BINDING_KEY));
	}

	/**
	 * DLX & DLQ
	 */
	@Bean
	public Declarables dlxDeclare() {
		Queue dlq = new Queue(RabbitmqConstant.Dlx.QUEUE, true);
		FanoutExchange dlx = new FanoutExchange(RabbitmqConstant.Dlx.EXCHANGE, true, false);
		return new Declarables(dlq, dlx, BindingBuilder.bind(dlq).to(dlx));
	}
}
