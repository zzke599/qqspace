package com.qqspace.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.qqspace.tool.exception.CustomException;

/**
* @author zzke
* @ClassName
* @Description   全局异常处理器
*/
public class CustomExceptionResolver implements HandlerExceptionResolver{

	//
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception e) {
		
		// 解析出异常类型
        CustomException customException = null;
        // 若该异常类型是系统自定义的异常，直接取出异常信息在错误页面展示即可。
        if(e instanceof CustomException){
            customException = (CustomException)e;
        }else{
            // 如果不是系统自定义异常，构造一个系统自定义的异常类型，信息为“未知错误”
            customException = new CustomException("未知错误");
        }
        //错误信息
        String message = customException.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        //将错误信息传到页面
        modelAndView.addObject("message",message);
        //指向错误页面
        modelAndView.setViewName("error");
        return modelAndView;
	}

}
