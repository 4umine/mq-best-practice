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
		String ROUTING_KEY = "direct-routing-key";
	}
}
