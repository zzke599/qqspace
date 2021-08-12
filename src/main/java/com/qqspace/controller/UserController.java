package com.qqspace.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qqspace.service.follow.FollowService;
import com.qqspace.service.model.FollowModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;
import com.qqspace.tool.IpUtils;
import com.qqspace.tool.OperationFileUtil;
import com.qqspace.tool.PathUtil;
import com.qqspace.tool.kindeditor.NameComparator;
import com.qqspace.tool.kindeditor.SizeComparator;
import com.qqspace.tool.kindeditor.TypeComparator;

/**
 * @author zzke
 * @ClassName UserController
 * @Description 用户相关UI 层
 */
@Controller
@RequestMapping(value = "/user")
public class
UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;
	// 转登陆页
	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 1. 首先判断session 中 有没有用户信息
		String path = request.getServletContext().getContextPath();
		HttpSession session = request.getSession();
		// 判断session是否有usersession对象，有跳转至首页
		if(session.getAttribute(Constants.USER_SESSION) != null) {
			return "redirect:/user/"+((UserModel)session
					.getAttribute(Constants.USER_SESSION))
					.getUserLogin()+"/main.html";
		}
		// 第二步 判断cookie 中是否存在
		Cookie autoCookie = null;
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			// 遍历cookie
			for (Cookie cookie : cookies) {
				// 判断cookie 中是否有autologin 标示符的cookie
				if ("autologin".equals(cookie.getName())) {
					autoCookie = cookie;// 赋值
				}
			}
			// 判断cookie 中是否有
			if (autoCookie == null) {
				return "login";
			}
			// 获取cookie 的值
			String value = autoCookie.getValue();
			// 拆分
			String temp[] = value.split(":");
			// 判断长度 对否等于自己拼凑的长度
			if (temp.length != 3) {
				return "login";
			}
			String name = temp[0];
			String time = temp[1];
			String pass = temp[2];
			// 判断是否失效
			if (Long.valueOf(time) <= System.currentTimeMillis()) {
				return "login";
			}
			// 根据cookie中的用户名去查询
			UserModel userModel = userService.userLogin(name, pass,null);
			/* System.out.println("-------- 重新查询" + userModel); */
			// 判断该用户是否存在！
			if (userModel != null) {
				// 保存session的用户信息
				session.setAttribute(Constants.USER_SESSION, userModel);
				// 跳转至首页
				return "redirect:/user/" + name + "/main.html";
			}
		}
		return "login";
	}

	// 转注册页
	@RequestMapping(value = "/register.html")
	public String register(ModelMap model) {
		return "register";
	}
	// 效验用户名是否合法
	@RequestMapping(value = "/existlogin.json", method = RequestMethod.POST)
	@ResponseBody
	public Object existLogin(@RequestParam(value = "userLogin", required = false) String userLogin) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isBlank(userLogin)) {
			resultMap.put("userLogin", "notnull");
		} else {
			boolean flag = userService.existLogin(userLogin);
			if (flag) {
				resultMap.put("userLogin", "noexist");
				System.out.println("noexist");
			} else {
				resultMap.put("userLogin", "exist");
			}

		}
		return JSONArray.toJSONString(resultMap);
	}

	// 创建用户
	@RequestMapping(value = "/doregister.json", method = RequestMethod.POST)
	@ResponseBody
	public Object doRegister(@RequestParam String userLogin, @RequestParam String userPassword,
			@RequestParam String vaild, HttpSession session)
					throws NoSuchAlgorithmException, UnsupportedEncodingException {
		HashMap<String, String> successmap = new HashMap<String, String>();

		String _verifyCodeValue = ((String) session.getAttribute("verifyCodeValue")).toUpperCase();
		// 效验验证码
		if (!vaild.equals(_verifyCodeValue)) {
			successmap.put("error", "errorCode");
			return JSONArray.toJSONString(successmap);
		} else {
			// 效验账号是否存在
			if (!userService.existLogin(userLogin)) {
				successmap.put("error", "errorExlogin");
				return JSONArray.toJSONString(successmap);
			}
			Long userCreateType = (long) this.getUserCreateType(userLogin);
			// 调用addNewUser方法
			boolean result = userService.addNewUser(userLogin, Constants.EncodeByMd5(userPassword), userCreateType);
			// 效验是否添加成功！
			if (result) {
				successmap.put("success", "succeed");
				System.out.println("=====>>>> 账号：  " + userLogin + " 密码：" + userPassword);
			} else {
				successmap.put("error", "failure");
			}
		}
		return JSONArray.toJSONString(successmap);
	}

	// 登录效验
	@RequestMapping(value = "/dologin.json", method = RequestMethod.POST)
	@ResponseBody
	public String dologin(HttpServletRequest request,
						  HttpServletResponse response,
						  HttpSession session,
						  @RequestParam String userLogin, @RequestParam String userPassword,
						  @RequestParam(value = "autologin", required = false) boolean autologin,
						  @RequestParam String valid,
						  Map<String, Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		JSONObject obj = new JSONObject();
		String _verifyCodeValue = ((String) session.getAttribute("verifyCodeValue")).toUpperCase();
		// 转换成大写
		valid = valid.toUpperCase();
		if (!_verifyCodeValue.equals(valid)) {
			return this.getError(2,"验证码错误");
		}
		//记录登录ip
		String ipaddr = IpUtils.getIpAddress(request);
		UserModel userModel = userService.userLogin(userLogin, Constants.EncodeByMd5(userPassword),ipaddr);
		if (userModel == null) {
			return this.getError("用户名或密码不正确");
		}
		if (autologin == true) {
			// 声明 cookie
			Cookie autoCookie = null;
			// 获取所有的 cookie
			Cookie cookies[] = request.getCookies();
			// 遍历cookie
			for (Cookie cookie : cookies) {
				// 判断是否存在自动登录记录
				if ("autologin".equals(cookie.getName())) {
					autoCookie = cookie;// 赋值
					// 当cookie 存在的时候 我需要重新设置值
					// 将时间转为毫秒Constants.USER_COOKIE_TIME*1000
					long time = System.currentTimeMillis() + Constants.USER_COOKIE_TIME * 1000;
					String newValue = userLogin + ":" + time + ":" + Constants.EncodeByMd5(userPassword);
					cookie.setValue(newValue);
				} else {
					// 不在创建
					// 将时间转为毫秒Constants.USER_COOKIE_TIME*1000
					long time = System.currentTimeMillis() + Constants.USER_COOKIE_TIME * 1000;
					String cookieValue = userLogin + ":" + time + ":" + Constants.EncodeByMd5(userPassword);
					autoCookie = new Cookie("autologin", cookieValue);
				}
			}
			// 保存cookie
			autoCookie.setMaxAge(Constants.USER_COOKIE_TIME);
			autoCookie.setPath("/");
			response.addCookie(autoCookie);// 添加里边去了
		}
		// 把相关信息保存到session
		session.setAttribute(Constants.USER_SESSION, userModel);
		// 在线用户统计
		Integer i = (Integer) session.getAttribute(Constants.onLineUserCount);
		if (i == null)
			i = 0;
		i++;
		session.setAttribute(Constants.onLineUserCount,i);
		obj.put("error", 0);
		obj.put("message", userLogin);
		return obj.toJSONString();
	}

	// 登录成功后跳转，进入系统
	@RequestMapping(value = "/{userLogin}/main.html")
	public String main(@PathVariable(value = "userLogin") String userLogin, HttpSession session, Model model) {
		// 登录用户
		int loginUserid = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
		// 访问用户
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		// 查询关注
		FollowModel followModel = followService.selectByUserIdAndBeUserId(loginUserid, userid);
		boolean exflag = false;
		// 不等于空表示关注过
		if (followModel != null) {
			exflag = true;
		}
		model.addAttribute("exflag", exflag);
		model.addAttribute("userModel", userModel);
		return "index";
	}

	// 退出系统
	@RequestMapping(value = "/logout.html")
	public String logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {

		// 清空Cookie操作
		Cookie[] cookies = request.getCookies();
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = new Cookie("autologin", null);
				cookie.setMaxAge(0);
				cookie.setPath("/");// 根据你创建cookie的路径进行填写
				response.addCookie(cookie);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 清空session操作
		session.removeAttribute(Constants.USER_SESSION);
		Integer i = (Integer) session.getAttribute(Constants.onLineUserCount);
		if (i == null)
			i = 0;
		i--;
		session.setAttribute(Constants.onLineUserCount,i);
		System.out.println("退出系统。。。");
		return "redirect:/user/login.html";
	}
	// 转个人信息页
	@RequestMapping(value = "/{userLogin}/userInfo.html")
	public String userInfo(@PathVariable(value = "userLogin") String userLogin, ModelMap model, HttpSession session) {
		// 登录用户
		int loginUserid = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
		// 访问用户
		Integer userid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		return "userInfo";
	}
	// 转修改个人信息页
	@RequestMapping(value = "/edit.html")
	public String useredit(ModelMap model, HttpSession session) {
		Integer userid = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);

		return "/user/edit";
	}

	// 转修改密码页
	@RequestMapping(value = "/updatePwd.html")
	public String updatePwd() {
		return "/user/updatePwd";
	}



    // 操作更新用戶信息
    @RequestMapping(value = "/doedit.json", method = RequestMethod.POST)
    @ResponseBody
    public String doEdit(@Valid UserModel userModel, BindingResult result, Model model) {
        Date date = new Date();
        Integer age = date.getYear() - userModel.getUserBirthday().getYear();
        userModel.setUserAge(age);
        if (result.hasErrors()) {
            // 接收BindingResult提示信息
            String defaultMessage = result.getFieldError().getDefaultMessage();
            // 返回提示信息
            return getError(defaultMessage);
        }
        if (!userService.updateByUserInfo(userModel)) {
            return getError("修改个人信息失败！");
        }
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        return obj.toJSONString();
    }

    // 验证密码和session中的是否匹配
    @RequestMapping(value = "/expwd.json", method = RequestMethod.POST)
    @ResponseBody
    public String expwd(@RequestParam(value = "userPassword", required = false) String userPassword,
                        HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject obj = new JSONObject();
        // 获取Session中的加密密码
        String sessionPwd = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getUserPassword();
        // 效验是否为空
        if (StringUtils.isEmpty(userPassword)) {
            return getError("  密码不正确！");
        }
        // 效验是否密码相符
        if (!Constants.EncodeByMd5(userPassword).equals(sessionPwd)) {
            return getError("  密码不正确！");
        }
        obj.put("error", 0);
        return obj.toJSONString();
    }

    // 验证密码和session中的是否匹配
    @RequestMapping(value = "/updatepwd.json", method = RequestMethod.POST)
    @ResponseBody
    public String updatePwd(@RequestParam(value = "nowUserPassword", required = false) String nowUserPassword,
                            HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject obj = new JSONObject();
        // 效验密码是否为空
        if (StringUtils.isEmpty(nowUserPassword)) {
            return getError("修改失败！");
        }
        if(nowUserPassword.length() < 7 || nowUserPassword.length() > 17){
            return getError("修改失败，密码长度在7-16位");
        }
        // 把数据加入UserModel模型
        Integer userid = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getId();
        UserModel userModel = new UserModel();

        userModel.setId(userid);
        userModel.setUserPassword(Constants.EncodeByMd5(nowUserPassword));
        // 判断业务是否处理不成功
        if (!userService.updateByUserPassword(userModel)) {
            return getError(" 修改失败！");
        }
        // 清空Cookie操作
        Cookie[] cookies = request.getCookies();
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = new Cookie("autologin", null);
                cookie.setMaxAge(0);
                cookie.setPath("/");// 根据你创建cookie的路径进行填写
                response.addCookie(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 清空session操作
        session.removeAttribute(Constants.USER_SESSION);
        obj.put("error", 0);
        return obj.toJSONString();
    }






    // kindeditor编辑器的上传图片文件
    @RequestMapping(value = "/upload_img.json")
    @ResponseBody
    public String uploadImg(HttpServletResponse response, HttpServletRequest request) throws FileUploadException {

        // 文件保存目录路径
        String savePath = PathUtil.getKindEdtorPath() + File.separator;
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + File.separator + "kindEdtor" + File.separator;
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        JSONObject obj = new JSONObject();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        // 最大文件大小
        long maxSize = 10485760;

        if (!ServletFileUpload.isMultipartContent(request)) {

            return getError("请选择文件。");
        }
        // 检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            uploadDir.mkdirs();
        }
        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        if (!extMap.containsKey(dirName)) {

            return getError("目录名不正确。");
        }
        // 创建文件夹
        savePath += dirName + File.separator;
        saveUrl += dirName + File.separator;
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + File.separator;
        saveUrl += ymd + File.separator;
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        // 取得request中的所有文件名
        Iterator<String> itr = multiRequest.getFileNames();
        while (itr.hasNext()) {
            // 取得上传文件 (遍历)
            MultipartFile file = multiRequest.getFile(itr.next());

            if (file != null) {
                // 检查文件大小
                String fileName = file.getOriginalFilename();
                /* System.out.println("===============>文件名  ： " + fileName); */
                if (file.getSize() > maxSize) {
                    return getError("上传文件大小超过限制。");
                }
                // 检查扩展名
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {

                    return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = df.format(new Date()) + "_" + RandomUtils.nextInt(0, 1000000) + "." + fileExt;
                try {
                    File uploadedFile = new File(savePath, newFileName);
                    file.transferTo(uploadedFile);
                } catch (Exception e) {
                    return getError("上传文件失败。");
                }
                obj.put("error", 0);
                obj.put("url", saveUrl + newFileName);
            }
        }
        return obj.toJSONString();
    }

    // kindeditor编辑器的图片文件空间
	@RequestMapping(value = "/uploadspace_img.json")
	@ResponseBody
	public String uploadSpaceImg(HttpServletResponse response, HttpServletRequest request) throws FileUploadException {

		// 文件保存目录路径
		String rootPath = PathUtil.getKindEdtorPath() + File.separator;
		// 文件保存目录URL
		String rootUrl = request.getContextPath() + File.separator + "kindEdtor" + File.separator;

		JSONObject obj = new JSONObject();
		// 图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };
		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if (!Arrays.<String> asList(new String[] { "image", "flash", "media", "file" }).contains(dirName)) {

				return getError("Invalid Directory name.");
			}
			rootPath += dirName + File.separator;
			rootUrl += dirName + File.separator;
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		// 根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {

			return getError("Access is not allowed.");
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {

			return getError("Parameter is not valid.");
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {

			return getError("Directory does not exist.");
		}

		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}

		obj.put("moveup_dir_path", moveupDirPath);
		obj.put("current_dir_path", currentDirPath);
		obj.put("current_url", currentUrl);
		obj.put("total_count", fileList.size());
		obj.put("file_list", fileList);

		return obj.toJSONString();
	}

	// 用户上传个人头像
	@RequestMapping(value = "/douploadphoto.json")
	@ResponseBody
	public String uploadPhoto(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		// 文件保存目录路径
		String savePath = PathUtil.getUserPath() + File.separator;
		// 文件保存目录URL
		String saveUrl = "/user" + File.separator;
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		JSONObject obj = new JSONObject();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		// 最大文件大小10M
		long maxSize = 10485760;

		if (!ServletFileUpload.isMultipartContent(request)) {

			return getError("请选择文件。");
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			uploadDir.mkdirs();
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {

			return getError("目录名不正确。");
		}
		// 创建文件夹
		savePath += dirName + File.separator;
		saveUrl += dirName + File.separator;
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 加入以用户id为文件夹名称
		Integer userid = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getId();
		savePath += userid + File.separator;
		saveUrl += userid + File.separator;
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// 取得request中的所有文件名
		Iterator<String> itr = multiRequest.getFileNames();
		while (itr.hasNext()) {
			// 取得上传文件 (遍历)
			MultipartFile file = multiRequest.getFile(itr.next());
			if (file != null) {
				// 检查文件大小
				String fileName = file.getOriginalFilename();
				/* System.out.println("===============>文件名  ： " + fileName); */
				if (file.getSize() > maxSize) {

					return getError("上传文件大小超过限制。");
				}
				// 检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {

					return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + RandomUtils.nextInt(0, 1000000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					// 上传文件
					file.transferTo(uploadedFile);

				} catch (Exception e) {

					return getError("上传文件失败。");
				}
				// 删除之前的头像图片
				UserModel userModel = userService.selectByUserId(userid);
				if (StringUtils.isNotBlank(userModel.getUserPhotoimg())) {
					String photoName = userModel.getUserPhotoName();
					OperationFileUtil.deleteFile(savePath + photoName);
				} else {
					System.out.println("没有头像！");
				}
				// 把信息加入模型中
				UserModel _userModel = new UserModel();
				_userModel.setId(userid);
				_userModel.setUserPhotoName(newFileName);
				_userModel.setUserPhotoimg(saveUrl + newFileName);
				// 修改数据库中的头像信息
				if (userService.updateByUserPhoto(_userModel)) {
					// 成功后返回前端error为0
					obj.put("error", 0);
					// 返回前端url的相对路径
					obj.put("url", saveUrl + newFileName);
					System.out.println("===========>头像路径：" + saveUrl + newFileName);
				}
			}
		}
		return obj.toJSONString();
	}


	// 修改留言寄语
	@RequestMapping(value = "upword.json", method = RequestMethod.POST)
	@ResponseBody
	public String upword(@RequestParam(value = "text", required = false) String userSendWord, HttpSession session) {
		int userid = ((UserModel) session.getAttribute(Constants.USER_SESSION)).getId();
		if (userSendWord == null || userSendWord == "") {
			userSendWord = "";
		}
		if (!userService.updateSendWordByUserid(userSendWord, userid)) {
			return getError("修改留言寄语失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}











	// ======================== 工具方法 ====================================

	// 获取用户创建类型
	private int getUserCreateType(String str) {
		// 默认登录类型为0,账号创建
		int flag = 0;
		if (StringUtils.isNotBlank(str)) {
			String telRegex = "[1][3456789]\\d{9}";
			String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			String first = "^[a-zA-Z]{1}";
			// 判断是否为手机号
			if (str.matches(telRegex)) {
				flag = 1;
				System.out.println("==========>>手机号注册");
			}else if (str.matches(regEx1)) {
				flag = 2;
				System.out.println("==========>>邮箱号注册");
			}else if (str.matches(first)) {
				flag = 3;
				System.out.println("==========>>用户名注册");
			}
		}
		return flag;
	}

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
	private String getError(int error,String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", error);
		obj.put("message", message);
		return obj.toJSONString();
	}
}