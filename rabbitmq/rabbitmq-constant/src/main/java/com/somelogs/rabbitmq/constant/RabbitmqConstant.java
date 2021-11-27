package com.somelogs.rabbitmq.constant;

/**
 * Rabbitmq Constants
 *
 * @author LBG - 2021/11/25
 */
public class RabbitmqConstant {

	private RabbitmqConstant() {}

	public interface Direct {
		String EXCHANGE = "direct-exchange";
		String QUEUE = "direct-queue";
		String BINDING_KEY = "direct-routing-key";
	}

	public interface Topic {
		String EXCHANGE = "topic-exchange";

		// order
		String ORDER_QUEUE = "order-topic-queue";
		String ORDER_ROUTING_KEY = "com.somelogs.order.save";
		String ORDER_BINDING_KEY = "com.somelogs.order.*";

		// goods
		String GOODS_QUEUE = "goods-topic-queue";
		String GOODS_ROUTING_KEY = "com.somelogs.goods.save";
		String GOODS_BINDING_KEY = "com.somelogs.goods.*";

	}

	public interface Dlx {
		String EXCHANGE = "dlx-exchange";
		String QUEUE = "dlq-queue";
	}
}
