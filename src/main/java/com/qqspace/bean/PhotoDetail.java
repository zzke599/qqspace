package com.qqspace.bean;

import java.util.Date;

public class PhotoDetail {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column photodetail_info.pd_id
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    private Integer pdId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column photodetail_info.pd_paId
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    private Integer pdPaid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column photodetail_info.pd_uploadDate
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    private Date pdUploaddate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column photodetail_info.pd_imgUrl
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    private String pdImgurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column photodetail_info.pd_description
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    private String pdDescription;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column photodetail_info.pd_id
     *
     * @return the value of photodetail_info.pd_id
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public Integer getPdId() {
        return pdId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column photodetail_info.pd_id
     *
     * @param pdId the value for photodetail_info.pd_id
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public void setPdId(Integer pdId) {
        this.pdId = pdId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column photodetail_info.pd_paId
     *
     * @return the value of photodetail_info.pd_paId
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public Integer getPdPaid() {
        return pdPaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column photodetail_info.pd_paId
     *
     * @param pdPaid the value for photodetail_info.pd_paId
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public void setPdPaid(Integer pdPaid) {
        this.pdPaid = pdPaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column photodetail_info.pd_uploadDate
     *
     * @return the value of photodetail_info.pd_uploadDate
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public Date getPdUploaddate() {
        return pdUploaddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column photodetail_info.pd_uploadDate
     *
     * @param pdUploaddate the value for photodetail_info.pd_uploadDate
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public void setPdUploaddate(Date pdUploaddate) {
        this.pdUploaddate = pdUploaddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column photodetail_info.pd_imgUrl
     *
     * @return the value of photodetail_info.pd_imgUrl
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public String getPdImgurl() {
        return pdImgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column photodetail_info.pd_imgUrl
     *
     * @param pdImgurl the value for photodetail_info.pd_imgUrl
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public void setPdImgurl(String pdImgurl) {
        this.pdImgurl = pdImgurl == null ? null : pdImgurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column photodetail_info.pd_description
     *
     * @return the value of photodetail_info.pd_description
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public String getPdDescription() {
        return pdDescription;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column photodetail_info.pd_description
     *
     * @param pdDescription the value for photodetail_info.pd_description
     *
     * @mbg.generated Sun Jul 21 10:19:03 CST 2021
     */
    public void setPdDescription(String pdDescription) {
        this.pdDescription = pdDescription == null ? null : pdDescription.trim();
    }
}