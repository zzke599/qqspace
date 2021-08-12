package com.qqspace.service.follow;

import com.qqspace.service.model.FollowModel;
import com.qqspace.tool.PageSupport;

/**
* @author zzke
* @ClassName
* @Description 	关注信息业务接口
*/
public interface FollowService {
	/**
	 * 添加关注
	 * @param foUserid 关注人用户id
	 * @param foBeuserid 被关注人用户id
	 * @return boolean
	 */
	boolean addFollow(Integer foUserid,Integer foBeuserid);
	/**
	 * 	根据关注人用户id 和被关注人用户id ，查询数据，有数据：返回 true，反之
	 * @param foUserid 关注人用户id
	 * @param foBeuserid 被关注人用户id
	 * @return FollowModel
	 */
	FollowModel selectByUserIdAndBeUserId(Integer foUserid,Integer foBeuserid);
	/**
	 * 取消关注
	 * @param foUserid 关注人用户id
	 * @param foBeuserid 被关注人用户id
	 * @return boolean
	 */
	boolean delFollow(Integer foUserid, Integer foBeuserid);
	/**
	 * 根据用户id 查询关注信息
	 * @param foUserid 关注人用户id
	 * @param page 当前页
	 * @return PageSupport<FollowModel>
	 */
	PageSupport<FollowModel> selectByFoUserid(Integer foUserid, Integer page);
	/**
	 * 根据用户id 查询用的关注量和粉丝量
	 * @param id 用户id
	 * @return FollowModel
	 */
	FollowModel selectFansandFollowCountByUserid(Integer userid);
}
