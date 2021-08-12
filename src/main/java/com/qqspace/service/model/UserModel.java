package com.qqspace.service.model;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author zzke
 * @ClassName
 * @Description		用户模型
 */
public class UserModel {
	//主键id
	@NotNull(message = "[提示]：请重新登录")
	private Integer id;
	//登录账号
	private String userLogin;
	//博客名
	@NotEmpty(message = "[提示]：博客名不能为！")
	@Size(max = 16, message = "[提示]：用户名长度不能超过16个字符")
	private String userName;
	//性别
	@NotNull(message = "[提示]：性别不能为空")
	private Long userGender;
	//年龄
	@Digits(integer = 0, fraction = 150, message = "[提示]：生日不输入不正确")
	private Integer userAge;
	//手机号
	@Size(max = 11, message = "[提示]: 手机号不正确")
	@Pattern(regexp = "[1][3456789]\\d{9}", message = "[提示]：手机号格式不正确")
	@NotEmpty(message="[提示]：手机号格式不正确")
	private String userTelphone;
	//邮箱
	@Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "[提示]：邮箱格式不正确")
	private String userEmail;
	//爱好
	private String userLoves;
	//地址
	@NotEmpty(message = "[提示]：地址不能为空")
	private String userAddress;
	//皮肤主题
	private String userTheme;
	//个性签名
	private String userSignature;
	//创建类型
	private Long userCreatetype;
	//创建时间
	private Date userCreatedate;
	//用户密码
	private String userPassword;
	//用户头像图片名称
	private String userPhotoName;
	//用户头像图片URL
	private String userPhotoimg;
	//最近登录时间
	private Date userLogintime;
	//个人简介
	@NotEmpty(message="[提示]：个人简介不能为空")
	private String userIntro;
	//最近登录IP地址
	private String userLoginip;
	//生日
	// 解决后台类型为时间类型无法转换的问题
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "[提示]：生日必须为过去的时间！")
	private Date userBirthday;
	//留言板寄语
	private String userSendWord;
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
	
	public String getUserSendWord() {
		return userSendWord;
	}

	public void setUserSendWord(String userSendWord) {
		this.userSendWord = userSendWord;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserGender() {
		return userGender;
	}

	public void setUserGender(Long userGender) {
		this.userGender = userGender;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserIntro() {
		return userIntro;
	}

	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}

	public String getUserTelphone() {
		return userTelphone;
	}

	public void setUserTelphone(String userTelphone) {
		this.userTelphone = userTelphone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserLoves() {
		return userLoves;
	}

	public void setUserLoves(String userLoves) {
		this.userLoves = userLoves;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserTheme() {
		return userTheme;
	}

	public void setUserTheme(String userTheme) {
		this.userTheme = userTheme;
	}

	public String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}

	public Long getUserCreatetype() {
		return userCreatetype;
	}

	public void setUserCreatetype(Long userCreatetype) {
		this.userCreatetype = userCreatetype;
	}

	public Date getUserCreatedate() {
		return userCreatedate;
	}

	public void setUserCreatedate(Date userCreatedate) {
		this.userCreatedate = userCreatedate;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserPhotoName() {
		return userPhotoName;
	}

	public void setUserPhotoName(String userPhotoName) {
		this.userPhotoName = userPhotoName;
	}

	public String getUserPhotoimg() {
		return userPhotoimg;
	}

	public void setUserPhotoimg(String userPhotoimg) {
		this.userPhotoimg = userPhotoimg;
	}

	public Date getUserLogintime() {
		return userLogintime;
	}

	public void setUserLogintime(Date userLogintime) {
		this.userLogintime = userLogintime;
	}

	public String getUserLoginip() {
		return userLoginip;
	}

	public void setUserLoginip(String userLoginip) {
		this.userLoginip = userLoginip;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", userLogin=" + userLogin + ", userName=" + userName + ", userGender="
				+ userGender + ", userAge=" + userAge + ", userTelphone=" + userTelphone + ", userEmail=" + userEmail
				+ ", userLoves=" + userLoves + ", userAddress=" + userAddress + ", userTheme=" + userTheme
				+ ", userSignature=" + userSignature + ", userCreatetype=" + userCreatetype + ", userCreatedate="
				+ userCreatedate + ", userPassword=" + userPassword + ", userPhotoimg=" + userPhotoimg
				+ ", userLogintime=" + userLogintime + ", userIntro=" + userIntro + ", userLoginip=" + userLoginip
				+ ", userBirthday=" + userBirthday + "]";
	}

}
