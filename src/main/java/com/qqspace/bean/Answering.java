package com.qqspace.bean;

import java.util.Date;

public class Answering {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column answering_info.an_id
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    private Integer anId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column answering_info.an_date
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    private Date anDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column answering_info.an_leaveId
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    private Integer anLeaveid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column answering_info.an_userid
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    private Integer anUserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column answering_info.an_messageId
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    private Integer anMessageid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column answering_info.an_content
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    private String anContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column answering_info.an_id
     *
     * @return the value of answering_info.an_id
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    
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

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column answering_info.an_id
     *
     * @param anId the value for answering_info.an_id
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public void setAnId(Integer anId) {
        this.anId = anId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column answering_info.an_date
     *
     * @return the value of answering_info.an_date
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public Date getAnDate() {
        return anDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column answering_info.an_date
     *
     * @param anDate the value for answering_info.an_date
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public void setAnDate(Date anDate) {
        this.anDate = anDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column answering_info.an_leaveId
     *
     * @return the value of answering_info.an_leaveId
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public Integer getAnLeaveid() {
        return anLeaveid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column answering_info.an_leaveId
     *
     * @param anLeaveid the value for answering_info.an_leaveId
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public void setAnLeaveid(Integer anLeaveid) {
        this.anLeaveid = anLeaveid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column answering_info.an_userid
     *
     * @return the value of answering_info.an_userid
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public Integer getAnUserid() {
        return anUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column answering_info.an_userid
     *
     * @param anUserid the value for answering_info.an_userid
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public void setAnUserid(Integer anUserid) {
        this.anUserid = anUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column answering_info.an_messageId
     *
     * @return the value of answering_info.an_messageId
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public Integer getAnMessageid() {
        return anMessageid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column answering_info.an_messageId
     *
     * @param anMessageid the value for answering_info.an_messageId
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public void setAnMessageid(Integer anMessageid) {
        this.anMessageid = anMessageid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column answering_info.an_content
     *
     * @return the value of answering_info.an_content
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public String getAnContent() {
        return anContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column answering_info.an_content
     *
     * @param anContent the value for answering_info.an_content
     *
     * @mbg.generated Sun Jul 28 08:59:57 CST 2021
     */
    public void setAnContent(String anContent) {
        this.anContent = anContent == null ? null : anContent.trim();
    }
}