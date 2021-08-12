package com.qqspace.service.model;

import java.util.Date;
import java.util.List;



import org.hibernate.validator.constraints.NotEmpty;


public class PhotoAlbumsModel {
	//相册id
	private Integer paId;
	//相册名称
	@NotEmpty(message="相册名称不能为空")
	private String paName;
	//相册封面图片
	private String paCoverimg;
	//相册创建时间
	private Date paCreatedate;
	//相册描述信息
	private String paDescription;
	//相册访问权限
	private Integer paJurisdiction;
	//所属用户id
	private Integer paUserid;
	//附属图片信息
	private List<PhotoDetailModel> photoDetailModels;
	//附属的总图片数
	private Integer  photoNums;
	
	
	
	public Integer getPhotoNums() {
		return photoNums;
	}
	public void setPhotoNums(Integer photoNums) {
		this.photoNums = photoNums;
	}
	public Integer getPaId() {
		return paId;
	}
	public void setPaId(Integer paId) {
		this.paId = paId;
	}
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
	public String getPaCoverimg() {
		return paCoverimg;
	}
	public void setPaCoverimg(String paCoverimg) {
		this.paCoverimg = paCoverimg;
	}
	public Date getPaCreatedate() {
		return paCreatedate;
	}
	public void setPaCreatedate(Date paCreatedate) {
		this.paCreatedate = paCreatedate;
	}
	public String getPaDescription() {
		return paDescription;
	}
	public void setPaDescription(String paDescription) {
		this.paDescription = paDescription;
	}
	public Integer getPaJurisdiction() {
		return paJurisdiction;
	}
	public void setPaJurisdiction(Integer paJurisdiction) {
		this.paJurisdiction = paJurisdiction;
	}
	public Integer getPaUserid() {
		return paUserid;
	}
	public void setPaUserid(Integer paUserid) {
		this.paUserid = paUserid;
	}
	public List<PhotoDetailModel> getPhotoDetailModels() {
		return photoDetailModels;
	}
	public void setPhotoDetailModels(List<PhotoDetailModel> photoDetailModels) {
		this.photoDetailModels = photoDetailModels;
	}
	@Override
	public String toString() {
		return "PhotoAlbumsModel [paId=" + paId + ", paName=" + paName + ", paCoverimg=" + paCoverimg
				+ ", paCreatedate=" + paCreatedate + ", paDescription=" + paDescription + ", paJurisdiction="
				+ paJurisdiction + ", paUserid=" + paUserid + ", photoDetailModels=" + photoDetailModels
				+ ", photoNums=" + photoNums + "]";
	}
	
	
	
	
}
