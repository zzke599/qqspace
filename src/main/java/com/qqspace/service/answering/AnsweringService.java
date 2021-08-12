package com.qqspace.service.answering;

import java.util.List;

import com.qqspace.service.model.AnsweringModel;

/**
* @author zzke
* @ClassName
* @Description  //留言回复的业务接口
*/
public interface AnsweringService {
	/**
	 *  添加留言回复
	 * @param answeringModel
	 * @return AnsweringModel
	 */
	AnsweringModel addMessageAnswering(AnsweringModel answeringModel);
	/**
	 * 根据留言id ，查询留言回复信息
	 * @param anMessageid 留言id
	 * @return List<AnsweringModel>
	 */
	List<AnsweringModel> selectByAnMessageid(Integer anMessageid);
	/**
	 * 根据留言id ，删除留言回复信息
	 * @param anMessageid
	 * @return boolean
	 */
	boolean deleteByAnMessageid(Integer anMessageid);
	/**
	 * 根据留言回复id ，删除留言回复信息
	 * @param anId
	 * @return boolean
	 */
	boolean delAnsweredByAnId(Integer anId);

}
