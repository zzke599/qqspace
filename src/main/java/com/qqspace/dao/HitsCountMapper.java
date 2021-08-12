package com.qqspace.dao;

import com.qqspace.bean.HitsCount;

public interface HitsCountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hitscount_info
     *
     * @mbg.generated Fri Jul 26 12:48:29 CST 2021
     */
    int deleteByPrimaryKey(Integer hiArticleid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hitscount_info
     *
     * @mbg.generated Fri Jul 26 12:48:29 CST 2021
     */
    int insert(HitsCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hitscount_info
     *
     * @mbg.generated Fri Jul 26 12:48:29 CST 2021
     */
    int insertSelective(HitsCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hitscount_info
     *
     * @mbg.generated Fri Jul 26 12:48:29 CST 2021
     */
    HitsCount selectByPrimaryKey(Integer hiArticleid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hitscount_info
     *
     * @mbg.generated Fri Jul 26 12:48:29 CST 2021
     */
    int updateByPrimaryKeySelective(HitsCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hitscount_info
     *
     * @mbg.generated Fri Jul 26 12:48:29 CST 2021
     */
    int updateByPrimaryKey(HitsCount record);
    /**
     *  自增点击量++1
     * @param articleId
     * @return int
     */
	int updateAutoCountByArticleId(Integer hiArticleid);
}