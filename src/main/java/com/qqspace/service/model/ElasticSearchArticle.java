package com.qqspace.service.model;
/**
* @author zzke
* @ClassName
* @Description  ElasticSearch文档类型实体
*/
public class ElasticSearchArticle {

	//id
	private Integer id;
	//标题
	private String title;
	//内容
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
