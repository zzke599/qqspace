package com.qqspace.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qqspace.service.follow.FollowService;
import com.qqspace.service.model.FollowModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;
import com.qqspace.tool.PageSupport;

/**
* @author zzke
* @ClassName
* @Description 关注相关UI 层
*/
@Controller
@RequestMapping("/user/att")
public class FollowController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;

	//进入关注页
	@RequestMapping(value="/{userLogin}/focus.html")
	public String focus(@PathVariable(value="userLogin")String userLogin,
                        @RequestParam(value="page",defaultValue="1")Integer page,
                        HttpSession session,ModelMap model){
		//登录用户
		int loginUserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
		//访问用户
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		//查询关注
		FollowModel followModel =followService.selectByUserIdAndBeUserId(loginUserid, userid);
		boolean exflag= false;
		//不等于空表示关注过
		if(followModel != null){
			exflag =true;
		}
		model.addAttribute("exflag", exflag);		
		//查询关注信息
		PageSupport<FollowModel> pages = followService.selectByFoUserid(userid,page);
		model.addAttribute("page", pages);
		return "focus";
	}
	
	//添加关注
	@RequestMapping(value="/add/{uid}")
	@ResponseBody
	public String addFollow(@PathVariable(value="uid")Integer beUserid,HttpSession session){
		//关注人id
		Integer userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		
		if(!followService.addFollow(userid,beUserid)){
			return getError("关注失败!");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
		
	}
	
	//取消关注
	@RequestMapping(value="/del/{uid}")
	@ResponseBody
	public String delFollow(@PathVariable(value="uid")Integer beUserid,HttpSession session){
			
		//关注人id
		Integer userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
				
		if(!followService.delFollow(userid,beUserid)){
				return getError("取消失败!");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
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
