package com.somelogs.rabbitmq.consumer.service;

import com.somelogs.rabbitmq.consumer.dal.entity.ConsumerMessage;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
public interface RabbitConsumerService {

	void saveMessage(String msgId);

	ConsumerMessage getMsg(String msgId);
}
