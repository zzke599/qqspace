package com.qqspace.service.comment;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.qqspace.bean.Comment;

import com.qqspace.dao.CommentMapper;
import com.qqspace.service.commentback.CommentBackService;
import com.qqspace.service.model.CommentModel;

import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.PageSupport;

/**
* @author zzke
* @ClassName
* @Description //文章评论的业务接口实现类
*/
@Component("CommentService")
public class CommentServiceImpl implements CommentService{

	@Resource
	private CommentMapper commentMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentBackService commentBackService;
	
	
	@Autowired
	private CommentLikeCountService commentLikeCountService;
	
	
	@Override
	@Transactional //开启事务管理
	public boolean addCommentInfo(CommentModel commentModel) {
		//数据效验
		if(commentModel == null){
			return false;
		}
		//数据转换  commentModel =》 comment
		Comment comment = this.convertCommentModelFromComment(commentModel);
		//设置为当前时间
		comment.setCoCreatedate(DateTime.now().toDate());
		//执行数据库操作
		int result = commentMapper.insertSelective(comment);
		
		return result > 0 ? true : false;
	}
	
	
	
	@Override
	public PageSupport<CommentModel> selectByCoArticleid(Integer coArticleid, Integer userid, Integer page) {
		//数据效验
		if(coArticleid == null || userid == null || page == null ){
			return null;
		}
		//创建分页模型
		PageSupport<CommentModel> pageSupport = new PageSupport<CommentModel>();
		//查询总条数
		int count = commentMapper.selectCountByCoArticleid(coArticleid);
		//当前页
		pageSupport.setCurrentPageNo(page);
		//每页容量
		pageSupport.setPageSize(8);
		//总条数
		pageSupport.setTotalCount(count);
		
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
		List<Comment> comments =commentMapper.selectByCoArticleid(coArticleid,begin,recordSize);
		//数据集合转换 comments =》 commentModels
		List<CommentModel> commentModels = comments.stream().map(comment -> {
			CommentModel commentModel = this.convertCommentFromCommentModel(comment);
			//查询评论用户信息
			UserModel userModel = userService.selectByUserId(commentModel.getCoUserid());
			commentModel.setUserModel(userModel);
			//查询底下的评论信息
			commentModel.setCommentBackModels(commentBackService.selectByCbCommentid(commentModel.getCoId()));
			//查询我是否过给评论点赞
			commentModel.setExLike(commentLikeCountService.exLike(commentModel.getCoId(),userid));
			//返回模型数据
			return commentModel;
		}).collect(Collectors.toList());
		
		//把数据添加分页的数据集合中
		pageSupport.setList(commentModels);
		
		return pageSupport;
	}

	@Override
	public Integer selectAllCommentCount() {

		return commentMapper.selectCountByCoArticleid(null);
	}


	// ============================================================
			// ================= ===== 工具方法================================
			
			//将 comment 转化到 commentModel
		    private CommentModel convertCommentFromCommentModel(Comment comment){
		    	if (comment == null){
		            return null;
		        }
		    	CommentModel commentModel = new CommentModel();

		        BeanUtils.copyProperties(comment,commentModel);
		        
		        return commentModel;
		    }
			
			//将 CommentModel 转化为 Comment
		    private Comment convertCommentModelFromComment(CommentModel commentModel){
		        if (commentModel == null){
		            return null;
		        }
		        Comment  comment = new Comment();
		        BeanUtils.copyProperties(commentModel,comment);
		        return comment;
		    }


		
			
	
	
	
}
