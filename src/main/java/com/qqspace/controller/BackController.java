package com.qqspace.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;

/**
 * @author zzke
 * @ClassName
 * @Description		用户页面皮肤相关UI层
 */
@Controller
@RequestMapping("/user/back")
public class BackController {

	@Autowired
	private UserService userService;
	
	/**
	 * 转皮肤页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/setskin.html")
	public String setSkin() {

		return "setskin";
	}
	@RequestMapping(value = "/dosetskin.json", method = RequestMethod.POST)
	@ResponseBody
	public String dosetskin(@RequestParam String skinname,HttpSession session) {

		if (StringUtils.isBlank(skinname)) {
			return getError("修改皮肤失败！");
		}
		//skinname追加后缀
		skinname +=".css";
		UserModel userModel = new UserModel();
		Integer userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		userModel.setId(userid);
		userModel.setUserTheme(skinname);

		if (!userService.updateByUserTheme(userModel)) {
			return getError("修改皮肤失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("message", "修改皮肤成功！");
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
