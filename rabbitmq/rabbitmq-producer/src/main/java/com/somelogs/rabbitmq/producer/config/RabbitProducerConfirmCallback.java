package com.somelogs.rabbitmq.producer.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.somelogs.rabbitmq.producer.dal.entity.ProducerMessage;
import com.somelogs.rabbitmq.producer.dal.mapper.ProducerMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Producer confirm callback
 *
 * @author LBG - 2021/11/26
 */
@Slf4j
@Component
public class RabbitProducerConfirmCallback implements RabbitTemplate.ConfirmCallback {

	@Resource
	private ProducerMessageMapper producerMessageMapper;

	public RabbitProducerConfirmCallback(RabbitTemplate rabbitTemplate) {
		rabbitTemplate.setConfirmCallback(this);
	}

	/**
	 * Confirmation callback.
	 *
	 * @param correlationData correlation data for the callback.
	 * @param ack             true for ack, false for nack
	 * @param cause           An optional cause, for nack, when available, otherwise null.
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("message confirm callback call data: {}, ack: {}, cause: {}", correlationData, ack, cause);
		if (ack) {
			QueryWrapper<ProducerMessage> wrapper = new QueryWrapper<>();
			wrapper.eq("msg_id", correlationData.getId());
			ProducerMessage message = producerMessageMapper.selectOne(wrapper);
			if (message != null) {
				ProducerMessage tmp = new ProducerMessage();
				tmp.setId(message.getId());
				tmp.setMsgStatus(20); // success
				producerMessageMapper.updateById(tmp);
				log.info("message {} confirm ok", correlationData.getId());
			}
		} else {
			// nack message will be resend by scheduler
			log.info("message {} nack for cause: {}", correlationData.getId(), cause);
		}
	}
}
