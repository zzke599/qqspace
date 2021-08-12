package com.qqspace.service.commentback;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.CommentBack;
import com.qqspace.dao.CommentBackMapper;
import com.qqspace.service.model.CommentBackModel;
import com.qqspace.service.user.UserService;

/**
* @author zzke
* @ClassName
* @Description 	//文章评论回复的接口实现类
*/
@Component("CommentBackService")
public class CommentBackServiceImpl implements CommentBackService  {

	@Resource
	private CommentBackMapper commentBackMapper;
	@Autowired
	private UserService userService;
	
	
	@Override
	@Transactional	//开启事务管理
	public boolean addBack(CommentBackModel commentBackModel) {
		//数据效验	
		if(commentBackModel == null){
			return false;
		}
		//数据转换
		CommentBack commentBack = this.convertCommentBackModelFromCommentBack(commentBackModel);
		//设置评论回复时间
		commentBack.setCbDate(DateTime.now().toDate());
		//执行数据库操作
		int result = commentBackMapper.insertSelective(commentBack);
		return result > 0 ? true : false;
	}
	
	@Override
	public List<CommentBackModel> selectByCbCommentid(Integer coId) {
		//数据效验
		if(coId == null){
			return null;
		}
		//执行数据库操作
		List<CommentBack> commentBacks = commentBackMapper.selectByCbCommentid(coId);
		//数据集转换
		List<CommentBackModel> commentBackModels = commentBacks.stream().map(commentBack ->{
			//数据转换commentBack =》commentBackModel
			CommentBackModel commentBackModel = this.convertCommentBackFromCommentBackModel(commentBack);
			//回复评论人的信息
			commentBackModel.setUserModel1(userService.selectByUserId(commentBackModel.getCbCommentuserid()));
			//被回复评论人的信息
			commentBackModel.setUserModel2(userService.selectByUserId(commentBackModel.getCbUserid()));
				//返回数据
			return commentBackModel;
		}).collect(Collectors.toList());
		
		return commentBackModels;
	}

	@Override
	public int selectAllCommentBackCount() {

		return commentBackMapper.selectAllCommentBackCount();
	}


	// ============================================================
	// ================= ===== 工具方法================================
				
				//将 comment 转化到 commentBackModel
			    private CommentBackModel convertCommentBackFromCommentBackModel(CommentBack commentBack){
			    	if (commentBack == null){
			            return null;
			        }
			    	CommentBackModel commentBackModel = new CommentBackModel();

			        BeanUtils.copyProperties(commentBack,commentBackModel);
			        
			        return commentBackModel;
			    }
				
				//将 commentBackModel 转化为 commentBack
			    private CommentBack convertCommentBackModelFromCommentBack(CommentBackModel commentBackModel){
			        if (commentBackModel == null){
			            return null;
			        }
			        CommentBack  commentBack = new CommentBack();
			        BeanUtils.copyProperties(commentBackModel,commentBack);
			        return commentBack;
			    }









				
				

}
