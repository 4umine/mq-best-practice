package com.somelogs.rabbitmq.producer.dal.entity;

import lombok.Data;

import java.util.Date;

/**
 * Producer message DO
 *
 * @author LBG - 2021/11/26
 */
@Data
public class ProducerMessage {

	private Long id;
	private Date createTime;
	private Date updateTime;
	private Boolean deleteFlag;
	private String topic;
	private Long msgId;
	private String msgBody;
	private Integer msgStatus;
	private Long ttl;
}
