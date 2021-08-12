package com.qqspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @author zzke
* @ClassName
* @Description 
*/

@Controller
public class MessageController {
	//不支持手机访问的跳转接口
	@RequestMapping("/mobile.html")
	public String doMobile(){
		
		return "/mobile/mobile";
	}
}
