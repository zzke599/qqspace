package com.qqspace.bean;

import java.util.Date;

public class Label {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column label_info.label_id
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    private Integer labelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column label_info.label_name
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    private String labelName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column label_info.label_userId
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    private Integer labelUserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column label_info.label_createDate
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    private Date labelCreatedate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column label_info.label_id
     *
     * @return the value of label_info.label_id
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public Integer getLabelId() {
        return labelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column label_info.label_id
     *
     * @param labelId the value for label_info.label_id
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column label_info.label_name
     *
     * @return the value of label_info.label_name
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public String getLabelName() {
        return labelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column label_info.label_name
     *
     * @param labelName the value for label_info.label_name
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column label_info.label_userId
     *
     * @return the value of label_info.label_userId
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public Integer getLabelUserid() {
        return labelUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column label_info.label_userId
     *
     * @param labelUserid the value for label_info.label_userId
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public void setLabelUserid(Integer labelUserid) {
        this.labelUserid = labelUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column label_info.label_createDate
     *
     * @return the value of label_info.label_createDate
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public Date getLabelCreatedate() {
        return labelCreatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column label_info.label_createDate
     *
     * @param labelCreatedate the value for label_info.label_createDate
     *
     * @mbg.generated Wed Jul 17 13:36:21 CST 2021
     */
    public void setLabelCreatedate(Date labelCreatedate) {
        this.labelCreatedate = labelCreatedate;
    }
}