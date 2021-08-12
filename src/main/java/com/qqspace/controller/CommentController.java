package com.qqspace.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qqspace.service.comment.CommentLikeCountService;
import com.qqspace.service.comment.CommentService;
import com.qqspace.service.commentback.CommentBackService;
import com.qqspace.service.model.CommentBackModel;
import com.qqspace.service.model.CommentModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.tool.Constants;
import com.qqspace.tool.PageSupport;

/**
* @author zzke
* @ClassName
* @Description 	//文章评论UI 层
*/
@Controller
@RequestMapping("/user/com")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentBackService commentBackService;
	@Autowired
	private CommentLikeCountService commentLikeCountService;
	
	
	//添加评论信息
	@RequestMapping(value="/{aid}/addcomment.json",method=RequestMethod.POST)
	@ResponseBody
	public String addComment(@PathVariable(value="aid")Integer coArticleid,
			@RequestParam(value="content")String coContent,HttpSession session){
		//接收评论信息
		CommentModel commentModel  = new CommentModel();
		//添加文章id
		commentModel.setCoArticleid(coArticleid);
		//添加评论信息
		commentModel.setCoContent(coContent);
		//添加用户id
		commentModel.setCoUserid(((UserModel)session.getAttribute(Constants.USER_SESSION)).getId());
		
		
		if(!commentService.addCommentInfo(commentModel)){
			return getError("添加文章评论失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		
		return obj.toJSONString();
	}
	
	//添加评论回复信息
	@RequestMapping(value="/addcommentback.json",method=RequestMethod.POST)
	@ResponseBody
	public String addCommentBack(@RequestParam(value="cbCommentid")Integer cbCommentid,
			@RequestParam(value="cbContent")String cbContent,
			@RequestParam(value="cbUserid")Integer cbUserid,HttpSession session){
		//创建对象模型，并传入数据
		CommentBackModel commentBackModel = new CommentBackModel();
		//评论id
		commentBackModel.setCbCommentid(cbCommentid);
		//评论回复人id
		Integer cbCommentuserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		commentBackModel.setCbCommentuserid(cbCommentuserid);
		//评论内容
		commentBackModel.setCbContent(cbContent);
		//被回复人id
		commentBackModel.setCbUserid(cbUserid);
		
		if(!commentBackService.addBack(commentBackModel)){
			return getError("回复失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	
	//点赞和取消点赞
	@RequestMapping(value="/onlike.json",method=RequestMethod.POST)
	@ResponseBody
	public String onLike(@RequestParam(value="clCommentid")Integer clCommentid,HttpSession session){
		int clDianzanid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		if(!commentLikeCountService.onLike(clCommentid,clDianzanid)){
			
			return getError("操作失败！");
		}
		
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	//加载文章详情的评论，带分页
	@RequestMapping(value="/load.json")
	@ResponseBody
	public String loadCommentData(@RequestParam(value="coArticleid")Integer coArticleid,
			@RequestParam(value="page",defaultValue="1")Integer page,HttpSession session){
		
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		
		PageSupport<CommentModel>pageSupport = commentService.selectByCoArticleid(coArticleid,userid,page);
		
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		//返回模型数据
		obj.put("info", pageSupport);
		return obj.toJSONString();
	}
	
	// ======================== 工具方法 ====================================	
	/**
	 * json的返回消息
	 * 
	 * @param message
	 * @return
	 */
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
	
}
