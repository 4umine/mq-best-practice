package com.somelogs.rabbitmq.producer.config;

import lombok.Data;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
@Data
public class MessageData {

	/**
	 * 消息内容
	 */
	private Object payload;

	/**
	 * 消息 ttl
	 */
	private Long ttl;

	/**
	 * routing key
	 */
	private String routingKey;
}
