package com.somelogs.rabbitmq.producer.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
public class ExpirationMessagePostProcessor implements MessagePostProcessor {

	private String ttl;

	public ExpirationMessagePostProcessor(String ttl) {
		this.ttl = ttl;
	}

	@Override
	public Message postProcessMessage(Message message) throws AmqpException {
		message.getMessageProperties().setExpiration(ttl);
		return message;
	}
}
