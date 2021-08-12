package com.qqspace.service.message;

import com.qqspace.service.model.MessageModel;
import com.qqspace.tool.PageSupport;

/**

*/
public interface MessageService {
	/**
	 *  添加用户留言信息
	 * @param messageModel 
	 * @return MessageModel
	 */
	MessageModel addMessage(MessageModel messageModel);
	/**
	 * 根据被留言的用户id 查询留言信息 带分页
	 * @param meUserid 被留言者id
	 * @param page 当前页
	 * @return PageSupport<MessageModel>
	 */
	PageSupport<MessageModel> selectByMeUserid(Integer meUserid,Integer page);
	/**
	 *  根据留言id 和被留言者id 删除留言信息和留言回复信息
	 * @param meId 留言id
	 * @param meUserid 被留言者id
	 * @return boolean
	 */
	boolean delMessageByMeIdAndMeUserid(Integer meId, Integer meUserid);

}
