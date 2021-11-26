package com.somelogs.rabbitmq.constant;

/**
 * 描述
 *
 * @author LBG - 2021/11/26
 */
public enum MessageStatusEnum {

	SENDING(10, "发送中"),
	SEND_OK(20, "发送成功"),
	SEND_FAILED(30, "发送失败")
	;

	private int code;
	private String desc;

	MessageStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
