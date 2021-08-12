package com.qqspace.service.model;

import java.util.Date;
import java.util.List;

/**
* @author zzke
* @ClassName
* @Description  //文章评论的业务层模型
*/
public class CommentModel {
	
	 //文章评论的主键id
	 private Integer coId;
	 //文章id
	 private Integer coArticleid;
	 //评论人id
	 private Integer coUserid;
	 //评论内容
	 private String coContent;
	 //评论时间
	 private Date coCreatedate;
	 //评论人用户信息
	 private UserModel userModel;
	 //评论回复信息
	 private List<CommentBackModel> commentBackModels;
	 //点赞总数
	 private Integer likeCount;
	 //当前登录用户是否给该评论点赞过,false为没有点赞过，反之
	 private boolean exLike;
	

	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public boolean isExLike() {
		return exLike;
	}
	public void setExLike(boolean exLike) {
		this.exLike = exLike;
	}
	public List<CommentBackModel> getCommentBackModels() {
		return commentBackModels;
	}
	public void setCommentBackModels(List<CommentBackModel> commentBackModels) {
		this.commentBackModels = commentBackModels;
	}
	public Integer getCoId() {
		return coId;
	}
	public void setCoId(Integer coId) {
		this.coId = coId;
	}
	public Integer getCoArticleid() {
		return coArticleid;
	}
	public void setCoArticleid(Integer coArticleid) {
		this.coArticleid = coArticleid;
	}
	public Integer getCoUserid() {
		return coUserid;
	}
	public void setCoUserid(Integer coUserid) {
		this.coUserid = coUserid;
	}
	public String getCoContent() {
		return coContent;
	}
	public void setCoContent(String coContent) {
		this.coContent = coContent;
	}
	
	public Date getCoCreatedate() {
		return coCreatedate;
	}
	public void setCoCreatedate(Date coCreatedate) {
		this.coCreatedate = coCreatedate;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	 
}
