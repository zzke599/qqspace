package com.qqspace.service.comment;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.CommentLikeCount;
import com.qqspace.dao.CommentLikeCountMapper;
import com.qqspace.service.model.CommentLikeCountModel;

/**
* @author zzke
* @ClassName
* @Description //文章评论点赞的业务接口实现类
*/
@Component("CommentLikeCountService")
public class CommentLikeCountServiceImpl implements CommentLikeCountService  {

	@Resource
	private CommentLikeCountMapper commentLikeCountMapper;
	
	
	
	@Override
	@Transactional
	public boolean onLike(Integer clCommentid, Integer clDianzanid) {
		//数据效验
		if(clCommentid == null || clDianzanid == null){
			
			return false;
		}
		//查询是否点赞
		CommentLikeCount commentLikeCount =commentLikeCountMapper.selectExByCommentidAndDianzanid(clCommentid,clDianzanid);
			//空，执行添加点赞，反之，删除点赞
		int result = 0;
		
		if(commentLikeCount == null){
			CommentLikeCount commentLikeCount2 = new CommentLikeCount();
			commentLikeCount2.setClCommentid(clCommentid);
			commentLikeCount2.setClDianzanid(clDianzanid);
			commentLikeCount2.setClDate(DateTime.now().toDate());
			result = commentLikeCountMapper.insert(commentLikeCount2);
		}else{
			result = commentLikeCountMapper.deleteByCommentidAndDianzanid(clCommentid,clDianzanid);
		}
		
		return result > 0 ? true : false;
	}
	
	@Override
	public boolean exLike(Integer clCommentid, Integer clDianzanid) {
		//数据效验
		if(clCommentid == null || clDianzanid == null){
					
				return false;
			}
		//执行数据库操作
		int result = commentLikeCountMapper.selectExLike(clCommentid,clDianzanid);
		
		return result > 0 ? true : false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// ============================================================
	// ================= ===== 工具方法================================
	
	//将 CommentLikeCount 转化到 CommentLikeCountModel
    private CommentLikeCountModel convertCommentLikeCountFromCommentLikeCountModel(CommentLikeCount commentLikeCount){
    	if (commentLikeCount == null){
            return null;
        }
    	CommentLikeCountModel commentLikeCountModel = new CommentLikeCountModel();

        BeanUtils.copyProperties(commentLikeCount,commentLikeCountModel);
        
        return commentLikeCountModel;
    }
	
	//将 CommentLikeCountModel 转化为 commentLikeCount
    private CommentLikeCount convertCommentLikeCountModelFromCommentLikeCount(CommentLikeCountModel commentLikeCountModel){
        if (commentLikeCountModel == null){
            return null;
        }
        CommentLikeCount  commentLikeCount = new CommentLikeCount();
        BeanUtils.copyProperties(commentLikeCountModel,commentLikeCount);
        return commentLikeCount;
    }














	

	
}
