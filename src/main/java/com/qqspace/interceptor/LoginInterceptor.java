package com.qqspace.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qqspace.tool.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qqspace.service.user.UserService;

/**
 * @author zzke
 * @ClassName
 * @Description user的拦截器，登录拦截
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Resource
	public UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		/*System.out.println("我是preHandle，我进来了");*/
		// 1. 首先判断session 中 有没有用户信息
		HttpSession session = request.getSession();
		String URI = request.getRequestURI();
			if(session.getAttribute(Constants.ADMIN_SESSION) == null  && URI.contains("/admin")) {
				request.getRequestDispatcher("/admin/login.html").forward(request, response);
				return false;
			}
			if(session.getAttribute(Constants.USER_SESSION) == null && !URI.contains("/admin")) {
				request.getRequestDispatcher("/user/login.html").forward(request, response);
				return false;
			}
		return true;

	}
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
		// System.out.println("我是postHandle，我进来了");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
		// System.out.println("我是afterCompletion，我进来了");
	}

}
