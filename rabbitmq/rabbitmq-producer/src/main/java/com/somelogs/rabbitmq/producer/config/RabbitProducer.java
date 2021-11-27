package com.somelogs.rabbitmq.producer.config;

import cn.hutool.json.JSONUtil;
import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import com.somelogs.rabbitmq.producer.dal.entity.ProducerMessage;
import com.somelogs.rabbitmq.producer.dal.mapper.ProducerMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
@Component
@RequiredArgsConstructor
public class RabbitProducer {

	private final RabbitTemplate rabbitTemplate;
	private final ProducerMessageMapper producerMessageMapper;

	/**
	 * thread pool to send msg
	 */
	private static final ExecutorService SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);

	public void send(@NonNull MessageData data) {
		Assert.notNull(data.getPayload(), "payload must be not null");
		Assert.notNull(data.getRoutingKey(), "routingKey must be not null");

		// 正式环境请用分布式唯一ID替换
		long msgId = System.currentTimeMillis();

		ProducerMessage message = new ProducerMessage();
		message.setMsgId(msgId);
		message.setMsgBody(JSONUtil.toJsonStr(data.getPayload()));
		message.setTopic(data.getRoutingKey());
		message.setTtl(data.getTtl());
		producerMessageMapper.insert(message);

		// send msg to rabbitmq broker
		SERVICE.execute(() -> doSend(msgId, data.getPayload(), data.getRoutingKey(), data.getTtl()));
	}

	/**
	 * send msg
	 */
	private void doSend(Long msgId, Object payload, String routingKey, Long ttl) {
		CorrelationData correlationData = new CorrelationData(String.valueOf(msgId));
		if (ttl != null) {
			// 这里一般是用来做延时消息
			ExpirationMessagePostProcessor processor = new ExpirationMessagePostProcessor(String.valueOf(ttl));
			rabbitTemplate.convertAndSend(
					//RabbitmqConstant.Direct.EXCHANGE,
					RabbitmqConstant.Topic.EXCHANGE,
					routingKey,
					payload,
					processor,
					correlationData);
		} else {
			rabbitTemplate.convertAndSend(
					//RabbitmqConstant.Direct.EXCHANGE,
					RabbitmqConstant.Topic.EXCHANGE,
					routingKey,
					payload,
					correlationData);
		}
	}
}
