package com.qqspace.service.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zzke
 * @ClassName
 * @Description 文章模型
 */
public class ArticleModel {
	
	//文章id
	private Integer articleId;
	
	//文章标题
	@NotEmpty(message="标题不能为空")
	private String articleTitle;
	
	//文章描述展示图片
	private String articleImage;
	
	//文章简介
	@NotEmpty(message="简介不能为空")
	private String articleIntro;
	
	//文章作者
	@NotEmpty(message="作者不能为空")
	private String articleAuthor;
	
	//文章查看权限（1.公共，2.博友，3.仅自己）
	private Integer articleAccessright;
	
	//文章推送（0.不推送，1.推送）
	private Integer articlePush;
	
	//文章审核状态(1.通过，2.未审核，3.未通过)
	private Integer articleExamine;
	
	//所属的标签
	@NotNull(message="分类不能为空")
	 private Integer articleLabelid;
	
	//发表文章用户Id
	private Integer articleUserid;
	
	//文章的创建时间
	private Date articleCreatedate;
	
	//文章内容
	@NotEmpty(message="正文不能为空")
	private String articleContent;
	
	//所属的用户信息
	private UserModel userModel;
	//所属的标签名称
	private String articleLabeName;
	
	//浏览量
	private Integer hiCount;
	
	//是否关注过发表人
	private boolean foId;
	

	public boolean isFoId() {
		return foId;
	}
	public void setFoId(boolean foId) {
		this.foId = foId;
	}
	
	public Integer getHiCount() {
		return hiCount;
	}
	public void setHiCount(Integer hiCount) {
		this.hiCount = hiCount;
	}
	public String getArticleLabeName() {
		return articleLabeName;
	}
	public void setArticleLabeName(String articleLabeName) {
		this.articleLabeName = articleLabeName;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleImage() {
		return articleImage;
	}
	public void setArticleImage(String articleImage) {
		this.articleImage = articleImage;
	}
	public String getArticleIntro() {
		return articleIntro;
	}
	public void setArticleIntro(String articleIntro) {
		this.articleIntro = articleIntro;
	}
	public String getArticleAuthor() {
		return articleAuthor;
	}
	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}
	public Integer getArticleAccessright() {
		return articleAccessright;
	}
	public void setArticleAccessright(Integer articleAccessright) {
		this.articleAccessright = articleAccessright;
	}
	public Integer getArticlePush() {
		return articlePush;
	}
	public void setArticlePush(Integer articlePush) {
		this.articlePush = articlePush;
	}
	public Integer getArticleExamine() {
		return articleExamine;
	}
	public void setArticleExamine(Integer articleExamine) {
		this.articleExamine = articleExamine;
	}
	
	public Integer getArticleLabelid() {
		return articleLabelid;
	}
	public void setArticleLabelid(Integer articleLabelid) {
		this.articleLabelid = articleLabelid;
	}
	public Integer getArticleUserid() {
		return articleUserid;
	}
	public void setArticleUserid(Integer articleUserid) {
		this.articleUserid = articleUserid;
	}
	public Date getArticleCreatedate() {
		return articleCreatedate;
	}
	public void setArticleCreatedate(Date articleCreatedate) {
		this.articleCreatedate = articleCreatedate;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	
	
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	@Override
	public String toString() {
		return "ArticleModel [articleId=" + articleId + ", articleTitle=" + articleTitle + ", articleImage="
				+ articleImage + ", articleIntro=" + articleIntro + ", articleAuthor=" + articleAuthor
				+ ", articleAccessright=" + articleAccessright + ", articlePush=" + articlePush + ", articleExamine="
				+ articleExamine + ", articleLabelid=" + articleLabelid + ", articleUserid=" + articleUserid
				+ ", articleCreatedate=" + articleCreatedate + ", articleContent=" + articleContent + ", userModel="
				+ userModel + ", articleLabeName=" + articleLabeName + ", hiCount=" + hiCount + ", foId=" + foId + "]";
	}
	
	
	
}
