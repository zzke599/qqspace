package com.qqspace.bean;

import java.util.Date;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.id
     *
     * @mbg.generated Thu Jul 04 10:53:20 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_login
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userLogin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_name
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_gender
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private Long userGender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_age
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private Integer userAge;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_telphone
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userTelphone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_email
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userEmail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_loves
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userLoves;

    
    private String userAddress;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_theme
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userTheme;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_signature
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private String userSignature;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_createType
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private Long userCreatetype;
    
    private String userIntro;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_createDate
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    private Date userCreatedate;
    
    private Date userBirthday;
    
    private String userSendWord;
    
    public String getUserSendWord() {
		return userSendWord;
	}

	public void setUserSendWord(String userSendWord) {
		this.userSendWord = userSendWord;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.id
     *
     * @return the value of user_info.id
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.id
     *
     * @param id the value for user_info.id
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_login
     *
     * @return the value of user_info.user_login
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_login
     *
     * @param userLogin the value for user_info.user_login
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin == null ? null : userLogin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_name
     *
     * @return the value of user_info.user_name
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_name
     *
     * @param userName the value for user_info.user_name
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_gender
     *
     * @return the value of user_info.user_gender
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public Long getUserGender() {
        return userGender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_gender
     *
     * @param userGender the value for user_info.user_gender
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserGender(Long userGender) {
        this.userGender = userGender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_age
     *
     * @return the value of user_info.user_age
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public Integer getUserAge() {
        return userAge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_age
     *
     * @param userAge the value for user_info.user_age
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_telphone
     *
     * @return the value of user_info.user_telphone
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserTelphone() {
        return userTelphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_telphone
     *
     * @param userTelphone the value for user_info.user_telphone
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserTelphone(String userTelphone) {
        this.userTelphone = userTelphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_email
     *
     * @return the value of user_info.user_email
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_email
     *
     * @param userEmail the value for user_info.user_email
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_loves
     *
     * @return the value of user_info.user_loves
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserLoves() {
        return userLoves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_loves
     *
     * @param userLoves the value for user_info.user_loves
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserLoves(String userLoves) {
        this.userLoves = userLoves == null ? null : userLoves.trim();
    }
    
    public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_theme
     *
     * @return the value of user_info.user_theme
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserTheme() {
        return userTheme;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_theme
     *
     * @param userTheme the value for user_info.user_theme
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserTheme(String userTheme) {
        this.userTheme = userTheme == null ? null : userTheme.trim();
    }

    
    
    public String getUserIntro() {
		return userIntro;
	}

	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_signature
     *
     * @return the value of user_info.user_signature
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public String getUserSignature() {
        return userSignature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_signature
     *
     * @param userSignature the value for user_info.user_signature
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature == null ? null : userSignature.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_createType
     *
     * @return the value of user_info.user_createType
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public Long getUserCreatetype() {
        return userCreatetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_createType
     *
     * @param userCreatetype the value for user_info.user_createType
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserCreatetype(Long userCreatetype) {
        this.userCreatetype = userCreatetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_createDate
     *
     * @return the value of user_info.user_createDate
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public Date getUserCreatedate() {
        return userCreatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_createDate
     *
     * @param userCreatedate the value for user_info.user_createDate
     *
     * @mbg.generated Thu Jul 04 10:53:21 CST 2021
     */
    public void setUserCreatedate(Date userCreatedate) {
        this.userCreatedate = userCreatedate;
    }

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
    
}