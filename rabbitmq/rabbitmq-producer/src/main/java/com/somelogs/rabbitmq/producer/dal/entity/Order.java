package com.somelogs.rabbitmq.producer.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
@Data
public class Order {

	private Long id;
	private Date createTime;
	private Date updateTime;
	private Boolean deleteFlag;
	private Long orderCode;
	private Integer orderStatus;
	private BigDecimal amount;
}
