package com.somelogs.rabbitmq.producer.scheduler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.somelogs.rabbitmq.constant.MessageStatusEnum;
import com.somelogs.rabbitmq.producer.config.MessageData;
import com.somelogs.rabbitmq.producer.config.RabbitProducer;
import com.somelogs.rabbitmq.producer.dal.entity.ProducerMessage;
import com.somelogs.rabbitmq.producer.dal.mapper.ProducerMessageMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * list the non send msg to send
 *
 * prd env can be replaced by dts(eg. xxlJob)
 *
 * @author LBG - 2021/11/26
 */
@Component
public class MessageHandler {

	@Resource
	private ProducerMessageMapper producerMessageMapper;
	@Resource
	private RabbitProducer rabbitProducer;

	/**
	 * list sending message to resend
	 */
	@Scheduled(fixedDelay = 30000)
	public void sendMsg() {
		QueryWrapper<ProducerMessage> wrapper = new QueryWrapper<>();
		wrapper.eq("msg_status", MessageStatusEnum.SENDING.getCode());
		List<ProducerMessage> messageList = producerMessageMapper.selectList(wrapper);
		messageList.forEach(msg -> {
			MessageData data = new MessageData();
			data.setPayload(msg.getMsgBody());
			data.setRoutingKey(msg.getTopic());
			data.setTtl(msg.getTtl());
			rabbitProducer.send(data);
		});
	}
}
