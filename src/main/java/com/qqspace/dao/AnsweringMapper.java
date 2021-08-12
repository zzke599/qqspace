package com.qqspace.dao;

import java.util.List;

import com.qqspace.bean.Answering;

public interface AnsweringMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    int deleteByPrimaryKey(Integer anId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    int insert(Answering record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    int insertSelective(Answering record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    Answering selectByPrimaryKey(Integer anId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    int updateByPrimaryKeySelective(Answering record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    int updateByPrimaryKeyWithBLOBs(Answering record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table answering_info
     *
     */
    int updateByPrimaryKey(Answering record);
    /**
     * 根据留言回复id 查询 留言回复信息
     * @param anId
     * @return Answering
     */
	Answering selectByAnId(Integer anId);
	/**
	 * 根据留言id 查询留言回复信息
	 * @param anMessageid
	 * @return List<Answering>
	 */
	List<Answering> selectByAnMessageId(Integer anMessageid);
	/**
	 * 根据留言id 删除留言回复信息
	 * @param anMessageid
	 * @return int
	 */
	int deleteByAnMessageid(Integer anMessageid);
}