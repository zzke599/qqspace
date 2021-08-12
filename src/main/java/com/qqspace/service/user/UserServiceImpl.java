package com.qqspace.service.user;

import javax.annotation.Resource;

import com.qqspace.tool.LayUIPageUtil;
import com.qqspace.vo.EChartsVO;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.qqspace.bean.User;
import com.qqspace.bean.UserPassword;
import com.qqspace.bean.UserPhoto;
import com.qqspace.bean.UserRecentlyLogin;
import com.qqspace.dao.UserMapper;
import com.qqspace.dao.UserPasswordMapper;
import com.qqspace.dao.UserPhotoMapper;
import com.qqspace.dao.UserRecentlyLoginMapper;
import com.qqspace.service.follow.FollowService;
import com.qqspace.service.model.FollowModel;
import com.qqspace.service.model.UserModel;

import java.util.List;

/**
 * @author zzke
 * @ClassName
 * @Description 用户信息类业务接口实现类
 */
@Component("UserService")
public class UserServiceImpl implements UserService {

	@Resource
	public UserMapper userMapper;
	@Resource
	public UserPasswordMapper userPasswordMapper;
	@Resource
	public UserPhotoMapper userPhotoMapper;
	@Resource
	public UserRecentlyLoginMapper userRecentlyLoginMapper;
	@Autowired
	public FollowService followSerivice;
	
	// 用户创建时默认的主题
	private final static String setSkin = "pf_default.css";
	//用户创建用户时默认的寄语
	private final static String sendWord = "稍稍沉下心来，欣赏自己拥有的和所完成的。";
	@Override
	public boolean existLogin(String userLogin) {
		boolean flag = false;

		if (StringUtils.isBlank(userLogin)) {
			flag = false;
		} else {
			// 判断集合大小
			User userDO = userMapper.selectByuserLogin(userLogin);
			if (userDO == null) {
				flag = true;

			} else {
				flag = false;
			}

		}
		return flag;
	}

	@Override
	@Transactional //开启事务处理
	public boolean addNewUser(String userLogin, String userPassword, Long userCreateType) {
		// 判断数据是否为空
		if (StringUtils.isBlank(userLogin) || StringUtils.isBlank(userPassword)) {
			return false;
		}
		int result = 0;
		// 把数据加入UserModel对象模型
		UserModel userModel = new UserModel();
		userModel.setUserLogin(userLogin);
		// 默认性别：男
		userModel.setUserGender((long) 1);
		userModel.setUserPassword(userPassword);
		userModel.setUserName(userLogin);
		userModel.setUserCreatetype(userCreateType);
		// 创建时间默认为当前时间
		userModel.setUserCreatedate(new DateTime().toDate());
		// 默认主题为pf_default.css
		userModel.setUserTheme(setSkin);
		//默认留言板寄语
		userModel.setUserSendWord(sendWord);
		// 把userModel转化为user
		User user = this.convertFromModel(userModel);
		// //执行数据操作
		result = userMapper.insertSelective(user);
		// 用户信息插入插入数据库是否成功！，再把其他数据先创建好
		if (result > 0) {
			// 保存密码
			userModel.setId(user.getId());
			// 把userModel转化为userPasswordDO
			UserPassword userPasswordDO = convertPasswordFromModel(userModel);
			// 执行数据操作
			userPasswordMapper.insertSelective(userPasswordDO);
			// 保存登录信息
			// 把userModel转化为userRecentlyLoginDO
			UserRecentlyLogin userRecentlyLoginDO = convertRecentlyLoginFromModel(userModel);
			// 执行数据操作
			userRecentlyLoginMapper.insertSelective(userRecentlyLoginDO);
			// 保存头像
			// 把userModel转化为userPhotoDO
			UserPhoto userPhotoDO = convertPhotoFromModel(userModel);
			// 执行数据操作
			userPhotoMapper.insertSelective(userPhotoDO);
			return true;
		} else {
			return false;
		}
	}

	
	@Override
	@Transactional //开启事务处理
	public UserModel userLogin(String userLogin, String userPassword, String ipaddr) {
		
		UserModel userModel = null;
		User userDO = userMapper.selectByuserLogin(userLogin);
		if (userDO == null) {
			return null;
		}
		// 根据user.getId()查询数据密码
		UserPassword userPasswordDO = userPasswordMapper.selectByPrimaryKey(userDO.getId());
		// 判断userPasswordDO密码 是否与userModel的密码一致
		if (StringUtils.equals(userPassword, userPasswordDO.getUserPassword())) {
			

			// 更新登陸信息
			UserRecentlyLogin userRecentlyLogin = new UserRecentlyLogin();
			userRecentlyLogin.setUserId(userDO.getId());
			userRecentlyLogin.setUserLogintime(new DateTime().toDate());
			userRecentlyLogin.setUserLoginip(ipaddr);
			//记录登录时间
			
			int result = userRecentlyLoginMapper.updateByPrimaryKeySelective(userRecentlyLogin);

			userModel = conventFromDataObject(userDO, userPasswordDO, null, null);
		}
		return userModel;
	}

	@Override
	public UserModel selectByUserLogin(String userLogin) {
		User userDO = userMapper.selectByuserLogin(userLogin);
		if (userDO == null) {
			return null;
		}
		// 根据user.getId()查询数据密码
		UserPassword userPasswordDO = userPasswordMapper.selectByPrimaryKey(userDO.getId());
		// 判断userPasswordDO密码 是否与userModel的密码一致

		// 查询头像信息
		UserPhoto userPhotoDO = userPhotoMapper.selectByPrimaryKey(userDO.getId());
		// 查询登录信息
		UserRecentlyLogin userRecentlyLogin = userRecentlyLoginMapper.selectByPrimaryKey(userDO.getId());

		UserModel userModel = conventFromDataObject(userDO, userPasswordDO, userPhotoDO, userRecentlyLogin);

		return userModel;

	}

	@Override
	public UserModel selectByUserId(Integer userId) {
		if(userId == null){
			return null;
		}
		
		User userDO = userMapper.selectByPrimaryKey(userId);
		if (userDO == null) {
			return null;
		}


		// 查询头像信息
		UserPhoto userPhotoDO = userPhotoMapper.selectByPrimaryKey(userDO.getId());

		// 查询登录信息
		UserRecentlyLogin userRecentlyLogin = userRecentlyLoginMapper.selectByPrimaryKey(userDO.getId());
		
		UserModel	userModel = conventFromDataObject(userDO, null, userPhotoDO, userRecentlyLogin);
		//查询关注量和粉丝量
		FollowModel faFollowModel = followSerivice.selectFansandFollowCountByUserid(userDO.getId());
		//粉丝量
		userModel.setFans(faFollowModel.getFans());
		//关注量
		userModel.setFollowCount(faFollowModel.getFollowCount());
		// System.out.println("===========》 用户信息：" + userModel.toString());
		// 返回userModel模型
		return userModel;
	}

	@Override
	@Transactional  //开启事务处理
	public boolean updateByUserPhoto(UserModel userModel) {
		if (userModel == null) {
			return false;
		}
		UserPhoto userPhotoDO = convertPhotoFromModel(userModel);
		int result = userPhotoMapper.updateByPrimaryKeySelective(userPhotoDO);
		// 判断返回SQL记录数是否大于0,为真,反之
		return result > 0 ? true : false;
	}

	@Override
	@Transactional  //开启事务处理
	public boolean updateByUserTheme(UserModel userModel) {
		// 判空处理
		if (userModel == null) {
			return false;
		}
		// 把userModel对象 转为 user
		User userDO = convertFromModel(userModel);
		// 执行数据库操作
		int result = userMapper.updateByUserTheme(userDO);
		// 判断返回执行记录数是否大于0，是为true,反之
		return result > 0 ? true : false;
	}

	@Override
	@Transactional	//开启事务处理
	public boolean updateByUserInfo(UserModel userModel) {
		// 判空
		if (userModel == null) {
			return false;
		}
		// 把userModel对象 转为 user
		User userDO = convertFromModel(userModel);
		// 执行数据库操作
		int result = userMapper.updateByPrimaryKeySelective(userDO);
		// 判断返回执行记录数是否大于0，是为true,反之
		return result > 0 ? true : false;
	}
	
	@Override
	@Transactional	//开启给事务处理
	public boolean updateByUserPassword(UserModel userModel) {
		//效验数据是否为空
		if(userModel == null){
			return false;
		}
		//把UerModel模型数据 ==>>UserPassword模型
		UserPassword userPasswordDO = convertPasswordFromModel(userModel);
		//执行数据库操作
		int result = userPasswordMapper.updateByPrimaryKey(userPasswordDO);
		//判断返回的执行的记录数是否大于0条，是：true，反之
		return result > 0 ? true : false;
	}
	
	@Override
	public UserModel selectUserInfoAndPhotoById(Integer userId) {
		if(userId == null){
			return null;
		}
		
		User userDO = userMapper.selectByPrimaryKey(userId);
		if (userDO == null) {
			return null;
		}

		// 判断userPasswordDO密码 是否与userModel的密码一致

		// 查询头像信息
		UserPhoto userPhotoDO = userPhotoMapper.selectByPrimaryKey(userDO.getId());

		//数据转换 userDO和userPhotoDO =》userModel
		UserModel	userModel = conventFromDataObject(userDO, null, userPhotoDO, null);		
		// 返回userModel模型
		return userModel;
		
	}
	@Override
	@Transactional //开启给事务处理
	public boolean updateSendWordByUserid(String userSendWord,Integer userId) {
		if(userId == null){
			return false;
		}
		int result = userMapper.updateSendWordByUserid(userSendWord,userId);
		return result > 0 ? true : false;
	}

	@Override
	public int selectAllUsersCount() {

		return userMapper.selectAllUsersCount();
	}

	@Override
	public List<EChartsVO> selectNearlyWeekAddUserCount() {
		return userMapper.selectWeekAddUserCount();
	}

	@Override
	public LayUIPageUtil<User> selectAllUsersWithPageByCondition(Integer page, Integer limit, String userName, String telePhone, String email) {

		User condition = new User();
		if (StringUtils.isNotBlank(userName)) {
			condition.setUserName(userName);
		}
		if (StringUtils.isNotBlank(telePhone)) {
			condition.setUserTelphone(telePhone);
		}
		if (StringUtils.isNotBlank(email)) {
			condition.setUserEmail(email);
		}
		Integer count = userMapper.selectAllUsersCountWithPageByCondition(condition);
		Integer offset = (page-1) * limit;
		List<User> list = userMapper.selectAllUsersWithPageByCondition(offset,limit,condition);
		LayUIPageUtil<User> pageUtil = new LayUIPageUtil<>(count,list);
		return pageUtil;
	}


	// ============================================================
	// ================= ===== 工具方法================================

	// 把userModel转换为userRecentlyLoginDO
	private UserRecentlyLogin convertRecentlyLoginFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserRecentlyLogin userRecentlyLoginDO = new UserRecentlyLogin();
		userRecentlyLoginDO.setUserId(userModel.getId());
		userRecentlyLoginDO.setUserLoginip(userModel.getUserLoginip());
		userRecentlyLoginDO.setUserLogintime(userModel.getUserLogintime());
		return userRecentlyLoginDO;
	}

	// 把userModel转换为userPhotoDO
	private UserPhoto convertPhotoFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserPhoto userPhotoDO = new UserPhoto();
		userPhotoDO.setUserPhotoName(userModel.getUserPhotoName());
		userPhotoDO.setUserPhotoimg(userModel.getUserPhotoimg());
		userPhotoDO.setUserId(userModel.getId());
		return userPhotoDO;
	}

	// 把userModel转换为UserPassword
	private UserPassword convertPasswordFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserPassword userPasswordDO = new UserPassword();
		userPasswordDO.setUserPassword(userModel.getUserPassword());
		userPasswordDO.setUserId(userModel.getId());
		return userPasswordDO;
	}

	// 把userModel转换为User
	private User convertFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		User userDO = new User();
		// 利用org.springframework.beans.BeanUtils，把userModel的属性值赋userDO
		BeanUtils.copyProperties(userModel, userDO);

		return userDO;
	}

	/**
	 * 把UserDO，UserPassWordDO对象 转化为UserModel对象
	 * 
	 * @param user
	 * @param userPassword
	 * @return UserModel
	 */
	private UserModel conventFromDataObject(User userDO, UserPassword userPasswordDO, UserPhoto userPhotoDO,
			UserRecentlyLogin userRecentlyLoginDO) {
		if (userDO == null) {
			return null;
		}
		UserModel userModel = new UserModel();
		// 把userModel的字段信息copy给userVO,
		// 使用BeanUtils.copyProperties 要保证 userModel,userVO 的字段类型，和字段名要一致。
		BeanUtils.copyProperties(userDO, userModel);

		if (userPasswordDO != null) {

			userModel.setUserPassword(userPasswordDO.getUserPassword());
		}
		if (userPhotoDO != null) {
			userModel.setUserPhotoName(userPhotoDO.getUserPhotoName());
			userModel.setUserPhotoimg(userPhotoDO.getUserPhotoimg());
		}
		if (userRecentlyLoginDO != null) {
			userModel.setUserLogintime(userRecentlyLoginDO.getUserLogintime());
			userModel.setUserLoginip(userRecentlyLoginDO.getUserLoginip());
		}
		// 返回userModel模型
		return userModel;
	}

	

	

	

}
