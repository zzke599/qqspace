package com.qqspace.service.model;

import java.util.Date;

/**
* @author zzke
* @ClassName
* @Description	文章评论回复的业务模型 
*/
public class CommentBackModel {
	//主键id
	 private Integer cbId;
	 //评论回复id
	 private Integer cbCommentuserid;
	 //评论被回复人id
	 private Integer cbUserid;
	 //评论id
	 private Integer cbCommentid;
	 //评论内容
	 private String cbContent;
	 //评论回复时间
	 private Date cbDate;
	 //评论回复用户信息
	 private UserModel userModel1;	 
	//评论被回复人用户信息
	private UserModel userModel2;
	 
	 
	 
	public Integer getCbId() {
		return cbId;
	}
	public void setCbId(Integer cbId) {
		this.cbId = cbId;
	}
	public Integer getCbCommentuserid() {
		return cbCommentuserid;
	}
	public void setCbCommentuserid(Integer cbCommentuserid) {
		this.cbCommentuserid = cbCommentuserid;
	}
	public Integer getCbUserid() {
		return cbUserid;
	}
	public void setCbUserid(Integer cbUserid) {
		this.cbUserid = cbUserid;
	}
	public Integer getCbCommentid() {
		return cbCommentid;
	}
	public void setCbCommentid(Integer cbCommentid) {
		this.cbCommentid = cbCommentid;
	}
	public String getCbContent() {
		return cbContent;
	}
	public void setCbContent(String cbContent) {
		this.cbContent = cbContent;
	}
	public Date getCbDate() {
		return cbDate;
	}
	public void setCbDate(Date cbDate) {
		this.cbDate = cbDate;
	}
	public UserModel getUserModel1() {
		return userModel1;
	}
	public void setUserModel1(UserModel userModel1) {
		this.userModel1 = userModel1;
	}
	public UserModel getUserModel2() {
		return userModel2;
	}
	public void setUserModel2(UserModel userModel2) {
		this.userModel2 = userModel2;
	}
	
	 
	 
	 
}
