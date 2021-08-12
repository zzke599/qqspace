package com.qqspace.service.user;

import java.util.List;

import com.qqspace.bean.User;
import com.qqspace.service.model.UserModel;
import com.qqspace.tool.LayUIPageUtil;
import com.qqspace.vo.EChartsVO;

/**
 * @author zzke
 * @ClassName
 * @Description	用户信息类业务接口
 */
public interface UserService {

	/**
	 * 用户注册
	 * 
	 * @param userLogin
	 * @param userPasswrod
	 * @return boolean
	 */
	boolean addNewUser(String userLogin, String userPasswrod, Long userCreateType);

	/**
	 * 检查用户账号是否存在
	 * 
	 * @param userLogin
	 * @return
	 */
	boolean existLogin(String userLogin);

	/**
	 * 登录验证
	 * 
	 * @param userLogin
	 * @param userPassword
	 * @param ipaddr 
	 * @return UserModel
	 */
	UserModel userLogin(String userLogin, String userPassword, String ipaddr);

	/**
	 * 根据用户id查询用户相关信息
	 * 
	 * @param userLogin
	 * @return UserModel
	 */
	UserModel selectByUserId(Integer userId);

	/**
	 * 根据userid更新用户头像
	 * 
	 * @param userModel
	 * @return boolean
	 */
	boolean updateByUserPhoto(UserModel userModel);

	/**
	 * 根据userid更改用户主题
	 * 
	 * @param userModel
	 *            用户主题
	 * @return boolean
	 */
	boolean updateByUserTheme(UserModel userModel);
	
	/**
	 *  根据UserLogin查找用户信息
	 * @param userLogin
	 * @return UserModel
	 */
	UserModel selectByUserLogin(String userLogin);
	/**
	 * 根据id修改用户个人信息
	 * @param userModel
	 * @return
	 */
	boolean updateByUserInfo(UserModel userModel);
	/**
	 * 根据id修改用户密码
	 * @param userModel
	 * @return boolean
	 */
	boolean updateByUserPassword(UserModel userModel);
	/**
	 * 根据用户id查询用户的个人信息和头像
	 * @param userId
	 * @return UserModel
	 */
	UserModel selectUserInfoAndPhotoById(Integer userId);
	/**
	 * 根据用户id 修改用户留言寄语
	 * @param userId 用户id
	 * @return boolean
	 */
	boolean updateSendWordByUserid(String userSendWord,Integer userId);

    int selectAllUsersCount();

	List<EChartsVO> selectNearlyWeekAddUserCount();


	LayUIPageUtil<User> selectAllUsersWithPageByCondition(Integer page, Integer limit, String userName, String telePhone, String email);
}
