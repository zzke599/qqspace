package com.qqspace.service.model;

import java.util.Date;
import java.util.List;

/**
* @author zzke
* @ClassName
* @Description 	标签模型
*/
public class LabelModel {
	
	 //标签ID
	 private Integer labelId;
	 
	 //标签名称
	 private String labelName;
	 
	 //用户ID
	 private Integer labelUserid;
	
	 //创建时间
	 private Date labelCreatedate;
	 
	 //附属标签下的文章
	 private List<ArticleModel> articleModelList;

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getLabelUserid() {
		return labelUserid;
	}

	public void setLabelUserid(Integer labelUserid) {
		this.labelUserid = labelUserid;
	}

	public Date getLabelCreatedate() {
		return labelCreatedate;
	}

	public void setLabelCreatedate(Date labelCreatedate) {
		this.labelCreatedate = labelCreatedate;
	}

	public List<ArticleModel> getArticleModelList() {
		return articleModelList;
	}

	public void setArticleModelList(List<ArticleModel> articleModelList) {
		this.articleModelList = articleModelList;
	}
	 
	 
}
