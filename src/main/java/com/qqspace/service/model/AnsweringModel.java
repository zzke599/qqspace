package com.qqspace.service.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @author zzke
* @ClassName
* @Description 留言回复业务模型
*/
public class AnsweringModel {
	//留言回复主键id
   private Integer anId;

   //留言回复时间
   private Date anDate;

   //留言回复人id
   private Integer anLeaveid;

   //留言被回复人id
   private Integer anUserid;

   //留言id
   private Integer anMessageid;

   //留言回复内容
   @NotEmpty(message="回复内容不能为空")
   private String anContent;
   
   //留言回复人的博客名
   private String anLeaveName;
   
   //留言被回复人的博客名
   private String anUserName;
   

public String getAnLeaveName() {
	return anLeaveName;
}

public void setAnLeaveName(String anLeaveName) {
	this.anLeaveName = anLeaveName;
}

public String getAnUserName() {
	return anUserName;
}

public void setAnUserName(String anUserName) {
	this.anUserName = anUserName;
}

public Integer getAnId() {
	return anId;
}

public void setAnId(Integer anId) {
	this.anId = anId;
}

public Date getAnDate() {
	return anDate;
}

public void setAnDate(Date anDate) {
	this.anDate = anDate;
}

public Integer getAnLeaveid() {
	return anLeaveid;
}

public void setAnLeaveid(Integer anLeaveid) {
	this.anLeaveid = anLeaveid;
}

public Integer getAnUserid() {
	return anUserid;
}

public void setAnUserid(Integer anUserid) {
	this.anUserid = anUserid;
}

public Integer getAnMessageid() {
	return anMessageid;
}

public void setAnMessageid(Integer anMessageid) {
	this.anMessageid = anMessageid;
}

public String getAnContent() {
	return anContent;
}

public void setAnContent(String anContent) {
	this.anContent = anContent;
}
   
   
   
}
