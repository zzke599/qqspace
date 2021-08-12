package com.qqspace.service.follow;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.Follow;
import com.qqspace.dao.FollowMapper;
import com.qqspace.service.model.FollowModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.PageSupport;


/**
* @author zzke
* @ClassName
* @Description  关注信息的业务实现类
*/

@Component("FollowSerivice")
public class FollowServiceImpl implements FollowService {

	
	@Resource
	private FollowMapper followMapper;
	@Autowired
	private UserService userService;
	
	@Override
	public FollowModel selectByUserIdAndBeUserId(Integer foUserid, Integer foBeuserid) {
		
		//数据效验
		if(foUserid == null || foBeuserid == null){
				return null;
			}
		//执行数据库操作
		Follow follow = followMapper.selectByUserIdAndBeUserId(foUserid,foBeuserid);
		//数据转换 follow =》 followModel
		FollowModel followModel = this.convertFollowModelFromLabel(follow);
		
		return followModel;
	}
	@Override
	@Transactional //开启事务管理
	public boolean addFollow(Integer foUserid, Integer foBeuserid) {
		//数据效验
		if(foUserid == null || foBeuserid == null){
			return false;
		}
		//查询数据是否存在
		if(this.selectByUserIdAndBeUserId(foUserid, foBeuserid) != null){
		
			return false;
		}
		//创建关注模型并添加数据
		Follow follow = new Follow();
		follow.setFoUserid(foUserid);
		follow.setFoBeuserid(foBeuserid);
		follow.setFoCreatedate(DateTime.now().toDate());
		//执行数据操作
		int result = followMapper.insertSelective(follow);
		
		return result > 0 ? true : false;
	}
	
	@Override
	@Transactional
	public boolean delFollow(Integer foUserid, Integer foBeuserid) {
		//数据效验
		if(foUserid == null || foBeuserid == null){
				return false;
			}
		
		//执行数据操作
		int result = followMapper.delByUseridAndBeUserid(foUserid,foBeuserid);
		
		return result > 0 ? true : false;
	}

	@Override
	public PageSupport<FollowModel> selectByFoUserid(Integer foUserid, Integer page) {
		//数据效验
		if(foUserid == null || page == null){
			return null;
		}
		
		//创建分页对象
		PageSupport<FollowModel> pageSupport = new PageSupport<FollowModel>();
		
		//查询总记录数
		int totalCount = followMapper.selectCountByFoUserid(foUserid);
		//每页容量
		pageSupport.setPageSize(14);
		//当前页
		pageSupport.setCurrentPageNo(page);
		pageSupport.setTotalCount(totalCount);
		
		//判断当前页是否大于总页数，是否小于1。
		if(page < 1){
			//当前页数=1
			pageSupport.setCurrentPageNo(1);
		}
		if(page > pageSupport.getTotalPageCount()){
			//当前页数=总页数
			pageSupport.setCurrentPageNo(pageSupport.getTotalPageCount());
		}
		//从哪开始
		int begin=(pageSupport.getCurrentPageNo()-1)*pageSupport.getPageSize();
		//查多少条记录
		int recordSize = pageSupport.getPageSize();
		//执行数据库操作
		//每页显示的数据集合 
		//执行数据操作
		List<Follow> follows = followMapper.selectByFoUserid(foUserid,begin,recordSize);
		//follows的集合数据传给 followModels的集合	
		List<FollowModel> followModels = follows.stream().map(follow -> {
			//数据转换  follow  =》followModel
			FollowModel followModel = this.convertFollowModelFromLabel(follow);
			//查询被关注人的信息
			UserModel userModel = userService.selectByUserId(follow.getFoBeuserid());
			followModel.setUserModel(userModel);
			//返回模型
			return followModel;
		}).collect(Collectors.toList());
		
		//把数据放入PageSupport.list
		pageSupport.setList(followModels);
		
		return pageSupport;
	}
	
	@Override
	public FollowModel selectFansandFollowCountByUserid(Integer userid) {
		//数据效验
		if(userid == null){
			return null;
		}
		//执行数据库操作
		Follow follow = followMapper.selectFansandFollowCountByUserid(userid);
		
		//数据转换 follow =》 followModel
		FollowModel followModel = this.convertFollowModelFromLabel(follow);
		
		return followModel;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
		// ============================================================
		// ================= ===== 工具方法================================
		
		//将 follow 转化到 FollowModel
	    private FollowModel convertFollowModelFromLabel(Follow follow){
	    	if (follow == null){
	            return null;
	        }
	    	FollowModel followModel = new FollowModel();

	        BeanUtils.copyProperties(follow,followModel);
	        
	        return followModel;
	    }
		
		//将 LabelModel 转化为 Label
	    private Follow convertLabelFromLabelModel(FollowModel followModel){
	        if (followModel == null){
	            return null;
	        }
	        Follow  follow = new Follow();
	        BeanUtils.copyProperties(followModel,follow);
	        return follow;
	    }
		
		
		
















		

		
}
