package com.qqspace.service.commentback;

import java.util.List;

import com.qqspace.service.model.CommentBackModel;

/**
* @author zzke
* @ClassName
* @Description //文章评论回复的接口
*/
public interface CommentBackService {
	/**
	 * 添加评论回复信息
	 * @param commentBackModel
	 * @return boolean
	 */
	boolean addBack(CommentBackModel commentBackModel);
	/**
	 * 根据评论id 查询回复信息
	 * @param coId
	 * @return List<CommentBackModel>
	 */
	List<CommentBackModel> selectByCbCommentid(Integer coId);

    int selectAllCommentBackCount();
}
