package com.qqspace.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qqspace.bean.Label;

public interface LabelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_info
     *
     */
    int deleteByPrimaryKey(Integer labelId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_info
     *
     */
    int insert(Label record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_info
     *
     */
    int insertSelective(Label record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_info
     *
     */
    Label selectByPrimaryKey(Integer labelId);
    /**
     * 根据用户ID 查询该用户的所有标签
     * @param labelUserid
     * @return List<Label>
     */
    List<Label> selectLabelsByUserId(Integer labelUserid);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_info
     *
     */
    int updateByPrimaryKeySelective(Label record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label_info
     *
     */
    int updateByPrimaryKey(Label record);
    /**
     * 根据标签id和用户id，删除标签
     * @param labelId
     * @param labelUserid
     * @return int
     */
	int deleteByLidAndUserid(@Param("labelId")Integer labelId,@Param("labelUserid") Integer labelUserid);
	/**
	 * 根据标签id和用户id，更新标签
	 * @param label
	 * @return int
	 */
	int updateByLidAndUserid(Label label);
}