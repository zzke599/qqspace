package com.qqspace.service.model;

import java.util.Date;

/**
* @author zzke
* @ClassName
* @Description 	//关注模型
*/
public class FollowModel {
	//关注主键ID
	private Integer foId;
	//备注信息
	private String foNote;
	//关注人id(用户id)
	private Integer foUserid;
	//被关注人id(用户id)
	private Integer foBeuserid;
	//关注创建时间
	private Date foCreatedate;
	
	//查询关注人信息
	private UserModel userModel;

	//粉丝(被多少人关注)
	private Integer fans;
	//关注了多少人
	private Integer followCount;
	
	
	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Integer getFoId() {
		return foId;
	}

	public void setFoId(Integer foId) {
		this.foId = foId;
	}

	public String getFoNote() {
		return foNote;
	}

	public void setFoNote(String foNote) {
		this.foNote = foNote;
	}

	public Integer getFoUserid() {
		return foUserid;
	}

	public void setFoUserid(Integer foUserid) {
		this.foUserid = foUserid;
	}

	public Integer getFoBeuserid() {
		return foBeuserid;
	}

	public void setFoBeuserid(Integer foBeuserid) {
		this.foBeuserid = foBeuserid;
	}

	public Date getFoCreatedate() {
		return foCreatedate;
	}

	public void setFoCreatedate(Date foCreatedate) {
		this.foCreatedate = foCreatedate;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	
	
	
	
}
