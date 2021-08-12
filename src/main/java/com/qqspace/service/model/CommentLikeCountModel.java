package com.qqspace.service.model;

import java.util.Date;

/**
* @author zzke
* @ClassName
* @Description 	//评论点赞的业务模型
*/
public class CommentLikeCountModel {
	//点赞的主键id
	private Integer clId;
	//评论id
	 private Integer clCommentid;
	 //点赞用户id
	  private Integer clDianzanid;
	  //点赞时间
	  private Date clDate;
	public Integer getClId() {
		return clId;
	}
	public void setClId(Integer clId) {
		this.clId = clId;
	}
	public Integer getClCommentid() {
		return clCommentid;
	}
	public void setClCommentid(Integer clCommentid) {
		this.clCommentid = clCommentid;
	}
	public Integer getClDianzanid() {
		return clDianzanid;
	}
	public void setClDianzanid(Integer clDianzanid) {
		this.clDianzanid = clDianzanid;
	}
	public Date getClDate() {
		return clDate;
	}
	public void setClDate(Date clDate) {
		this.clDate = clDate;
	}
	  
	  
}
