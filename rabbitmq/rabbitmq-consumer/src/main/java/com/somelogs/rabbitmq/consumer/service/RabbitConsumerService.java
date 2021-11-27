package com.somelogs.rabbitmq.consumer.service;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
public interface RabbitConsumerService {

	void saveMessage(String msgId);
}
