package cn.mxj.mxjweb.bean;

import java.io.Serializable;
import java.util.Date;

public class UserRegistTempBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1355735567225087342L;
	/**
	 * 主键标志
	 */
	private int id;
	/**
	 * 账号(必须为手机号)
	 */
	private String account;
	/**
	 * 6位短信验证码
	 */
	private int msgCheckCode;
	/**
	 * 发送短信的时间
	 */
	private Date sendTime;
	/**
	 * 短信有效时长（单位为：s）
	 */
	private int validity;
	/**
	 * 是否删除
	 * 0:代表未删除；1：代表删除
	 */
	private int isdelete;
	/**
	 * 备注
	 */
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getMsgCheckCode() {
		return msgCheckCode;
	}
	public void setMsgCheckCode(int msgCheckCode) {
		this.msgCheckCode = msgCheckCode;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	public int getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
