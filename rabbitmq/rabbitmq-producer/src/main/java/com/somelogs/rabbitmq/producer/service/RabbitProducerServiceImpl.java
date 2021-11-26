package com.somelogs.rabbitmq.producer.service;

import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import com.somelogs.rabbitmq.producer.config.MessageData;
import com.somelogs.rabbitmq.producer.config.RabbitProducer;
import com.somelogs.rabbitmq.producer.dal.entity.Order;
import com.somelogs.rabbitmq.producer.dal.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Rabbitmq Message Producer
 *
 * @author LBG - 2021/11/25
 */
@Service
public class RabbitProducerServiceImpl implements RabbitProducerService {

	@Resource
	private OrderMapper orderMapper;
	@Resource
	private RabbitProducer rabbitProducer;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long saveOrder(Long amount) {
		Order order = new Order();
		order.setAmount(BigDecimal.valueOf(amount));
		order.setOrderCode(System.currentTimeMillis()); // use timestamp
		orderMapper.insert(order);

		// send message
		MessageData message = new MessageData();
		message.setPayload(order.getOrderCode());
		message.setRoutingKey(RabbitmqConstant.Direct.ROUTING_KEY);
		rabbitProducer.send(message);

		return order.getId();
	}
}
