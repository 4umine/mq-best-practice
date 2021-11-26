package com.somelogs.rabbitmq.consumer.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.somelogs.rabbitmq.consumer.dal.entity.ConsumerMessage;
import com.somelogs.rabbitmq.consumer.dal.mapper.ConsumerMessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
@Service
public class RabbitConsumerServiceImpl implements RabbitConsumerService {

	@Resource
	private ConsumerMessageMapper consumerMessageMapper;

	@Override
	public void saveMessage(String msgId) {
		ConsumerMessage msg = new ConsumerMessage();
		msg.setMsgId(msgId);
		consumerMessageMapper.insert(msg);
	}

	@Override
	public ConsumerMessage getMsg(String msgId) {
		return consumerMessageMapper.selectOne(new QueryWrapper<ConsumerMessage>().eq("msg_id", msgId));
	}
}
