package com.somelogs.rabbitmq.producer.service;

import com.somelogs.rabbitmq.constant.RabbitmqConstant;
import com.somelogs.rabbitmq.producer.config.MessageData;
import com.somelogs.rabbitmq.producer.config.RabbitProducer;
import com.somelogs.rabbitmq.producer.dal.entity.Goods;
import com.somelogs.rabbitmq.producer.dal.entity.Order;
import com.somelogs.rabbitmq.producer.dal.mapper.GoodsMapper;
import com.somelogs.rabbitmq.producer.dal.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Rabbitmq Message Producer
 *
 * @author LBG - 2021/11/25
 */
@Service
@RequiredArgsConstructor
public class RabbitProducerServiceImpl implements RabbitProducerService {

	private final OrderMapper orderMapper;
	private final GoodsMapper goodsMapper;
	private final RabbitProducer rabbitProducer;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long saveOrder(Long amount) {
		// 创建订单
		Order order = new Order();
		order.setAmount(BigDecimal.valueOf(amount));
		order.setOrderCode(System.currentTimeMillis()); // use timestamp
		orderMapper.insert(order);

		// 创建商品
		Goods goods = new Goods();
		goods.setGoodsName("Clean Code");
		goods.setGoodsPrice(BigDecimal.valueOf(169L));
		goodsMapper.insert(goods);

		// send message
		MessageData orderMsg = new MessageData();
		orderMsg.setPayload(order.getOrderCode());
		orderMsg.setRoutingKey(RabbitmqConstant.Topic.ORDER_ROUTING_KEY);
		rabbitProducer.send(orderMsg);

		MessageData goodsMsg = new MessageData();
		goodsMsg.setPayload(goods.getId());
		goodsMsg.setRoutingKey(RabbitmqConstant.Topic.GOODS_ROUTING_KEY);
		rabbitProducer.send(goodsMsg);

		return order.getId();
	}
}
