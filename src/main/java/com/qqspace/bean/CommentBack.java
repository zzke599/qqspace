package com.qqspace.bean;

import java.util.Date;

public class CommentBack {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commentback_info.cb_id
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer cbId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commentback_info.cb_commentUserid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer cbCommentuserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commentback_info.cb_userid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer cbUserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commentback_info.cb_date
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Date cbDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commentback_info.cb_commentId
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private Integer cbCommentid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commentback_info.cb_content
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    private String cbContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commentback_info.cb_id
     *
     * @return the value of commentback_info.cb_id
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Integer getCbId() {
        return cbId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commentback_info.cb_id
     *
     * @param cbId the value for commentback_info.cb_id
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCbId(Integer cbId) {
        this.cbId = cbId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commentback_info.cb_commentUserid
     *
     * @return the value of commentback_info.cb_commentUserid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Integer getCbCommentuserid() {
        return cbCommentuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commentback_info.cb_commentUserid
     *
     * @param cbCommentuserid the value for commentback_info.cb_commentUserid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCbCommentuserid(Integer cbCommentuserid) {
        this.cbCommentuserid = cbCommentuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commentback_info.cb_userid
     *
     * @return the value of commentback_info.cb_userid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Integer getCbUserid() {
        return cbUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commentback_info.cb_userid
     *
     * @param cbUserid the value for commentback_info.cb_userid
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCbUserid(Integer cbUserid) {
        this.cbUserid = cbUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commentback_info.cb_date
     *
     * @return the value of commentback_info.cb_date
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Date getCbDate() {
        return cbDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commentback_info.cb_date
     *
     * @param cbDate the value for commentback_info.cb_date
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCbDate(Date cbDate) {
        this.cbDate = cbDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commentback_info.cb_commentId
     *
     * @return the value of commentback_info.cb_commentId
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public Integer getCbCommentid() {
        return cbCommentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commentback_info.cb_commentId
     *
     * @param cbCommentid the value for commentback_info.cb_commentId
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCbCommentid(Integer cbCommentid) {
        this.cbCommentid = cbCommentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commentback_info.cb_content
     *
     * @return the value of commentback_info.cb_content
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public String getCbContent() {
        return cbContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commentback_info.cb_content
     *
     * @param cbContent the value for commentback_info.cb_content
     *
     * @mbg.generated Tue Jul 30 16:35:44 CST 2021
     */
    public void setCbContent(String cbContent) {
        this.cbContent = cbContent == null ? null : cbContent.trim();
    }
}