package com.qqspace.dao;

import org.apache.ibatis.annotations.Param;

import com.qqspace.bean.CommentLikeCount;

public interface CommentLikeCountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commentlikecount_info
     *
     */
    int deleteByPrimaryKey(Integer clId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commentlikecount_info
     *
     */
    int insert(CommentLikeCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commentlikecount_info
     *
     */
    int insertSelective(CommentLikeCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commentlikecount_info
     *
     */
    CommentLikeCount selectByPrimaryKey(Integer clId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commentlikecount_info
     *
     */
    int updateByPrimaryKeySelective(CommentLikeCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commentlikecount_info
     *
     */
    int updateByPrimaryKey(CommentLikeCount record);
    /**
     * 根据用户id 和评论id 查询该用户是否有点赞过该评论
     * @param clCommentid
     * @param clDianzanid
     * @return CommentLikeCount
     */
	CommentLikeCount selectExByCommentidAndDianzanid(@Param("clCommentid")Integer clCommentid,@Param("clDianzanid") Integer clDianzanid);
	/**
	 *  根据用户id 和评论id 删除点赞
	 * @param clCommentid
	 * @param clDianzanid
	 * @return int
	 */
	int deleteByCommentidAndDianzanid(@Param("clCommentid")Integer clCommentid,@Param("clDianzanid") Integer clDianzanid);
	/**
	 *  根据用户id 和评论id 查询是否点赞
	 * @param clCommentid 评论id
	 * @param clDianzanid 点赞人id
	 * @return int
	 */
	int selectExLike(@Param("clCommentid")Integer clCommentid,@Param("clDianzanid") Integer clDianzanid);
	/**
	 * 根据评论id 删除点赞
	 * @param coId
	 */
	void deleteByclCommentid(Integer coId);
}