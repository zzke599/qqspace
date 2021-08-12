package com.qqspace.bean;

import java.util.Date;

public class Comment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment_info.co_id
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer coId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment_info.co_createDate
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Date coCreatedate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment_info.co_articleId
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer coArticleid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment_info.co_userid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer coUserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment_info.co_content
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private String coContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment_info.co_id
     *
     * @return the value of comment_info.co_id
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    
    //点赞总数
	 private Integer likeCount;
	 
    
    public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCoId() {
        return coId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment_info.co_id
     *
     * @param coId the value for comment_info.co_id
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCoId(Integer coId) {
        this.coId = coId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment_info.co_createDate
     *
     * @return the value of comment_info.co_createDate
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Date getCoCreatedate() {
        return coCreatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment_info.co_createDate
     *
     * @param coCreatedate the value for comment_info.co_createDate
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCoCreatedate(Date coCreatedate) {
        this.coCreatedate = coCreatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment_info.co_articleId
     *
     * @return the value of comment_info.co_articleId
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Integer getCoArticleid() {
        return coArticleid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment_info.co_articleId
     *
     * @param coArticleid the value for comment_info.co_articleId
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCoArticleid(Integer coArticleid) {
        this.coArticleid = coArticleid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment_info.co_userid
     *
     * @return the value of comment_info.co_userid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Integer getCoUserid() {
        return coUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment_info.co_userid
     *
     * @param coUserid the value for comment_info.co_userid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCoUserid(Integer coUserid) {
        this.coUserid = coUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment_info.co_content
     *
     * @return the value of comment_info.co_content
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public String getCoContent() {
        return coContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment_info.co_content
     *
     * @param coContent the value for comment_info.co_content
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCoContent(String coContent) {
        this.coContent = coContent == null ? null : coContent.trim();
    }
}