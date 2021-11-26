package com.somelogs.rabbitmq.producer.controller;

import com.somelogs.rabbitmq.producer.service.RabbitProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author LBG - 2021/11/25
 */
@RequestMapping("/rabbit")
@RestController
public class ProducerController {

	@Resource
	public RabbitProducerService rabbitProducerService;

	@GetMapping("/order/save")
	public Long saveOrder(Long amount) {
		return rabbitProducerService.saveOrder(amount);
	}
}
