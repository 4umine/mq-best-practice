package com.somelogs.rabbitmq.consumer.controller;

import com.somelogs.rabbitmq.consumer.dal.entity.ConsumerMessage;
import com.somelogs.rabbitmq.consumer.service.RabbitConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
@RequestMapping("/rabbit")
@RestController
public class ConsumerController {

	@Resource
	private RabbitConsumerService rabbitConsumerService;

	@GetMapping("/save")
	public String testSave(String msgId) {
		rabbitConsumerService.saveMessage(msgId);
		return "ok";
	}

	@GetMapping("/get")
	public ConsumerMessage testGet(String msgId) {
		return rabbitConsumerService.getMsg(msgId);
	}
}
