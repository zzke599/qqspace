package com.qqspace.dao;

import com.qqspace.bean.AdminPhoto;

public interface AdminPhotoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_photo
     *
     */
    int deleteByPrimaryKey(Integer adminId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_photo
     *
     */
    int insert(AdminPhoto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_photo
     *
     */
    int insertSelective(AdminPhoto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_photo
     *
     */
    AdminPhoto selectByPrimaryKey(Integer adminId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_photo
     *
     */
    int updateByPrimaryKeySelective(AdminPhoto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_photo
     *
     */
    int updateByPrimaryKey(AdminPhoto record);
}