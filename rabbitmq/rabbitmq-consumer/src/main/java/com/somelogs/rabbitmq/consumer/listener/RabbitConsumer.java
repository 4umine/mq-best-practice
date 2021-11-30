package com.somelogs.rabbitmq.consumer.listener;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import com.somelogs.rabbitmq.consumer.dal.entity.ConsumerMessage;
import com.somelogs.rabbitmq.consumer.dal.mapper.ConsumerMessageMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;

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

	@RabbitListener(queues = {
			RabbitmqConstant.Direct.QUEUE,
			RabbitmqConstant.Topic.GOODS_QUEUE,
			RabbitmqConstant.Topic.ORDER_QUEUE
	})
	@Transactional(rollbackFor = Exception.class)
	public void directQueue(String in, Channel channel, Message message) {
		System.out.println("Message read from myQueue : " + in);

		long tag = message.getMessageProperties().getDeliveryTag();
		System.out.println("delivery tag : " + tag);

		// 这里逻辑：如果消费消息表里该消息已经消费过，相同消息过来直接ack（幂等）
		QueryWrapper<ConsumerMessage> wrapper = new QueryWrapper<>();
		wrapper.eq("msg_id", in);
		ConsumerMessage consumerMessage = consumerMessageMapper.selectOne(wrapper);
		if (consumerMessage != null) {
			ack(channel, tag);
			return;
		}

		// 执行业务逻辑代码
		try {
			// 这里模拟业务逻辑偶尔出错
			int num = RandomUtil.randomInt(1, 10);
			Assert.isTrue(num > 5, "num is too less");
		} catch(Exception e) {
		    e.printStackTrace();

			/*
			 * 业务逻辑出错后：
			 * 1、nack
			 * 2、设置 requeue = true
			 * 3、如果是重复投递，则设置 requeue = false，让消息进入死信队列
			 */
			Boolean redelivered = message.getMessageProperties().isRedelivered();
			System.out.println("redelivered : " + redelivered);
			boolean requeue = !redelivered;
			try {
				channel.basicNack(tag, false, requeue);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

			return;
		}

		// insert consumer message
		consumerMessage = new ConsumerMessage();
		consumerMessage.setMsgId(in);
		consumerMessageMapper.insert(consumerMessage);

		ack(channel, tag);
	}

	// manual ack
	private void ack(Channel channel, long deliveryTag) {
		try {
			channel.basicAck(deliveryTag, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
