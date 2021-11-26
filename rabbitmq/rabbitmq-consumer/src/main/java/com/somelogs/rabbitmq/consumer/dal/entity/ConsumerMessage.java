package com.somelogs.rabbitmq.consumer.dal.entity;

import lombok.Data;

import java.util.Date;

/**
 * Producer message DO
 *
 * @author LBG - 2021/11/26
 */
@Data
public class ConsumerMessage {

	private Long id;
	private Date createTime;
	private Date updateTime;
	private Boolean deleteFlag;
	private String topic;
	private String msgId;
	private String msgBody;
}
