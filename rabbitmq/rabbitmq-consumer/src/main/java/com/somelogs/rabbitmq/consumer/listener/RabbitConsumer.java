package com.somelogs.rabbitmq.consumer.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import com.somelogs.rabbitmq.consumer.dal.entity.ConsumerMessage;
import com.somelogs.rabbitmq.consumer.dal.mapper.ConsumerMessageMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Rabbitmq listener
 *
 * @author LBG - 2021/11/25
 */
@Component
@Configuration
public class RabbitConsumer {

	@Resource
	private ConsumerMessageMapper consumerMessageMapper;
	@Resource
	private RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = {
			RabbitmqConstant.Direct.QUEUE,
			RabbitmqConstant.Topic.GOODS_QUEUE,
			RabbitmqConstant.Topic.ORDER_QUEUE
	})
	@Transactional(rollbackFor = Exception.class)
	public void directQueue(String in) {
		System.out.println("Message read from myQueue : " + in);

		QueryWrapper<ConsumerMessage> wrapper = new QueryWrapper<>();
		wrapper.eq("msg_id", in);
		ConsumerMessage consumerMessage = consumerMessageMapper.selectOne(wrapper);
		if (consumerMessage != null) {
			return;
		}

		// call business logic

		// insert consumer message
		consumerMessage = new ConsumerMessage();
		consumerMessage.setMsgId(in);
		consumerMessageMapper.insert(consumerMessage);

		// manual ack
	}
}
