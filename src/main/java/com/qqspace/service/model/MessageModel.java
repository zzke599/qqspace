package com.qqspace.service.model;

import java.util.Date;
import java.util.List;

/**
* @author zzke
* @ClassName
* @Description  留言业务模型
*/
public class MessageModel {
	 
	//留言主键id
   private Integer meId;

   //留言者id
   private Integer meLeaveid;

   //被留言者ID
   private Integer meUserid;

   //留言时间
   private Date meDate;

   //留言内容
   private String meContent;
   
   
   //留言者的博客名
   private String meLeaveName;
   
   //留言回复信息
   private List<AnsweringModel> answeringModels;
   
   //留言者信息
   private UserModel userModel;
   

public UserModel getUserModel() {
	return userModel;
}

public void setUserModel(UserModel userModel) {
	this.userModel = userModel;
}

public String getMeLeaveName() {
	return meLeaveName;
}

public void setMeLeaveName(String meLeaveName) {
	this.meLeaveName = meLeaveName;
}

public Integer getMeId() {
	return meId;
}

public void setMeId(Integer meId) {
	this.meId = meId;
}

public Integer getMeLeaveid() {
	return meLeaveid;
}

public void setMeLeaveid(Integer meLeaveid) {
	this.meLeaveid = meLeaveid;
}

public Integer getMeUserid() {
	return meUserid;
}

public void setMeUserid(Integer meUserid) {
	this.meUserid = meUserid;
}

public Date getMeDate() {
	return meDate;
}

public void setMeDate(Date meDate) {
	this.meDate = meDate;
}

public String getMeContent() {
	return meContent;
}

public void setMeContent(String meContent) {
	this.meContent = meContent;
}

public List<AnsweringModel> getAnsweringModels() {
	return answeringModels;
}

public void setAnsweringModels(List<AnsweringModel> answeringModels) {
	this.answeringModels = answeringModels;
}
   
   
}
