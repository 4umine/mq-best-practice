package com.somelogs.rabbitmq.producer.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @author LBG - 2021/11/27
 */
@Data
public class Goods {

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String goodsName;
	private BigDecimal goodsPrice;
}
