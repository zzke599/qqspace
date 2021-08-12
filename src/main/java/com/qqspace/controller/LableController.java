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
import com.qqspace.service.article.ArticleService;
import com.qqspace.service.label.LabelService;
import com.qqspace.service.model.LabelModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.tool.Constants;

/**
* @author zzke
* @ClassName
* @Description 标签的相关UI层
*/
@Controller
@RequestMapping(value="/user/lab")
public class LableController {
	@Autowired
	private LabelService labelService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value="/add")
	@ResponseBody
	public String addLabel(@RequestParam String labelName,HttpSession session){
		
		LabelModel _labelModel = new LabelModel();
		//用户ID
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		_labelModel.setLabelUserid(userid);
		_labelModel.setLabelName(labelName);
		LabelModel labelModel = labelService.addLable(_labelModel);
		if(labelModel == null){
			return this.getError("添加失败!");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("labelModel", labelModel);		
		return obj.toJSONString();
	}
	
	
	@RequestMapping(value="/del/{tid}",method=RequestMethod.POST)
	@ResponseBody
	public String delLabel(@PathVariable Integer tid,HttpSession session){
		JSONObject obj =  new JSONObject();
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		if(articleService.selectArticleModelByLid(tid).size() ==0){
			if(!labelService.delLableByLableId(tid,userid)){
				return this.getError("删除失败！");
			}		
			
			obj.put("error", 0);
		}else{
			return this.getError("标签存在文章，无法删除！");
		}
		return obj.toJSONString();
	}

	
	@RequestMapping(value="/change/{tid}",method=RequestMethod.POST)
	@ResponseBody
	public String change(@PathVariable Integer tid,@RequestParam String labelName ,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		LabelModel labelModel = new LabelModel();
		labelModel.setLabelId(tid);
		labelModel.setLabelName(labelName);
		labelModel.setLabelUserid(userid);
		if(!labelService.updateLableByLableId(labelModel)){
			return this.getError("修改失败！");
		}		
		JSONObject obj =  new JSONObject();
		obj.put("error", 0);
		obj.put("labelName", labelName);
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
