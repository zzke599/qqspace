package com.qqspace.controller;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
import com.alibaba.fastjson.JSONObject;
import com.qqspace.service.article.ArticleService;
import com.qqspace.service.label.LabelService;
import com.qqspace.service.model.ArticleModel;
import com.qqspace.service.model.LabelModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;
import com.qqspace.tool.ConverVideoUtils;
import com.qqspace.tool.OperationFileUtil;
import com.qqspace.tool.PageSupport;
import com.qqspace.tool.PathUtil;
import com.qqspace.tool.kindeditor.NameComparator;
import com.qqspace.tool.kindeditor.SizeComparator;
import com.qqspace.tool.kindeditor.TypeComparator;



/**
* @author zzke
* @ClassName
* @Description 	文章的相关UI层
*/
@Controller
@RequestMapping(value="/user/atc")
public class ArticleController {
	@Autowired
	private UserService userService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private ArticleService articleService;
	
	
	//跳转到文章信息页面
	@RequestMapping(value="/{userLogin}/article.html")
	public String article(@PathVariable(value="userLogin")String userLogin,@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="label", required=false)Integer labelId, ModelMap model,HttpSession session){
		//登录用户信息
		int loginUserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
		//访问用户信息
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		//访问用户的标签信息
		List<LabelModel> labelModels = labelService.getLablesByLabelUserId(userModel.getId());
		model.addAttribute("labelModels", labelModels);
		//访问用户的文章信息
		PageSupport<ArticleModel> pages = articleService.selectAllByUserId(userid,labelId,page);
		model.addAttribute("page", pages);
		if(labelId != null){
			model.addAttribute("label", labelId);
		}
		return "article";
	}
	//跳转到添加文章信息页面
	@RequestMapping(value="/add.html")
	public String add(ModelMap model,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		List<LabelModel> labelModels = labelService.getLablesByLabelUserId(userModel.getId());
		model.addAttribute("labelModels", labelModels);
		return "/article/add";
	}
	
		// kindeditor编辑器的上传图片,视频，音频文件
		@RequestMapping(value = "/upload_img.json")
		@ResponseBody
		public String uploadImg(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws FileUploadException {
			int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			// 文件保存目录路径
			String savePath = PathUtil.getArticlePath() + File.separator+userid+File.separator;
			// 文件保存目录URL
			String saveUrl = request.getContextPath() + File.separator+"article" + File.separator +
					userid+File.separator;
			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			JSONObject obj = new JSONObject();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,mp4");
			
			// 最大文件大小
			long maxSize = 104857600;

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
					/*System.out.println("===============>文件名  ： " + fileName);*/
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
						String path = savePath+newFileName;
						ConverVideoUtils converVideoUtils = new ConverVideoUtils(path);
						newFileName = converVideoUtils.beginConver();
					} catch (Exception e) {

						return getError("上传文件失败。");
					}

					obj.put("error", 0);
					obj.put("url", saveUrl + newFileName);
				}
			}
			return obj.toJSONString();
		}

		// kindeditor编辑器的图片，音频，视频文件空间
		@RequestMapping(value = "/uploadspace_img.json")
		@ResponseBody
		public String uploadSpaceImg(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws FileUploadException {
			int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			// 文件保存目录路径
			String rootPath = PathUtil.getArticlePath() + File.separator+userid+File.separator;
			// 文件保存目录URL
			String rootUrl =  request.getContextPath() +File.separator+"article" + File.separator +
					userid+File.separator;

			JSONObject obj = new JSONObject();
			// 图片扩展名
			String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" ,"swf","flv","mp3","wav","wma","wmv","mid","avi","mpg","asf","mp4"};
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

		@RequestMapping(value="/upload.json",method=RequestMethod.POST)
		@ResponseBody
		public String uploadArticleImage(@RequestParam(value="image",required=false) MultipartFile articleImage,
				@RequestParam(value="imgurl",required=false) String imgurl,
					HttpServletResponse response, HttpServletRequest request,HttpSession session){
			Integer userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			// 文件保存目录路径
			String savePath = PathUtil.getArticlePath() + File.separator+userid+File.separator;
			// 文件保存目录URL
			String saveUrl =  File.separator +userid+File.separator;
			
			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			JSONObject obj = new JSONObject();
			extMap.put("articleImg", "gif,jpg,jpeg,png,bmp");
		
			String oldFileName = articleImage.getOriginalFilename();//原文件名
			/*System.out.println("===============>文件名："+oldFileName);*/
			// 最大文件大小
			long maxSize = 11085760;
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				uploadDir.mkdirs();
			}
			//路径
			String	dirName = "articleImg";
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
			
				if (articleImage != null) {
					// 检查文件大小
					if (articleImage.getSize() > maxSize) {

						return getError("上传文件大小超过限制。");
					}
					// 检查扩展名
					String fileExt = oldFileName.substring(oldFileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {

						return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_" + RandomUtils.nextInt(0, 1000000) + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						articleImage.transferTo(uploadedFile);
						
						if(StringUtils.isNotEmpty(imgurl)){
							//文件的绝对路径
							String filePath = PathUtil.getArticlePath()+imgurl;
							OperationFileUtil.deleteFile(filePath);
						}
					} catch (Exception e) {

						return getError("上传文件失败。");
					}

					obj.put("error", 0);
					obj.put("url", saveUrl + newFileName);
				}
		
			
			return obj.toJSONString();
			
		}
		
		//写文章
		@RequestMapping(value="/front",method=RequestMethod.POST)
		@ResponseBody
		public String front(@Valid ArticleModel articleModel ,BindingResult result,HttpSession session){
			
			/*System.out.println("========>>传进来的数据：  "+articleModel.toString());*/
			int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			String userLogin =  ((UserModel)session.getAttribute(Constants.USER_SESSION)).getUserLogin();
			articleModel.setArticleUserid(userid);
			
			if(result.hasErrors()){
				// 接收BindingResult提示信息
				String defaultMessage = result.getFieldError().getDefaultMessage();
				// 返回提示信息
				return getError(defaultMessage);
			}
			if(!articleService.addArticleInfo(articleModel)){
				return getError("提交失败！");
			}
			JSONObject obj = new JSONObject();
			
			obj.put("error", 0);
			obj.put("userLogin", userLogin);
			return obj.toJSONString();
		}
	//跳转至修改文章页
	@RequestMapping(value="/edit/{aid}")
	public String  edit(@PathVariable(value="aid") Integer articleId,Model model,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		/*	System.out.println("我进来修改文章了     文章ID："+articleId);*/
		
		//查询文章
		ArticleModel articleModel = articleService.selectArticleByAidAndUid(articleId,userid);
		
		//查询用户信息
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		if(articleModel == null){
			
			return "/article/error.html";
		}
		model.addAttribute("articleModel", articleModel);
		
		//查询用户的标签信息
		List<LabelModel> labelModels = labelService.getLablesByLabelUserId(userModel.getId());
		model.addAttribute("labelModels", labelModels);
		
		return "article/edit";
	}
	//修改保存文章信息
	@RequestMapping(value="/change.json",method=RequestMethod.POST)
	@ResponseBody
	public String change (@Valid ArticleModel articleModel,BindingResult result ,HttpSession session){
		
		
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		String userLogin =  ((UserModel)session.getAttribute(Constants.USER_SESSION)).getUserLogin();
		//
		ArticleModel _articleModel = new ArticleModel();
		//查询该文章
		_articleModel = articleService.selectArticleByAidAndUid(articleModel.getArticleId(), userid);
		
		JSONObject obj = new JSONObject();
		//没有修改任何信息，直接返回成功!
		if(_articleModel.getArticleTitle().equals(articleModel.getArticleTitle())  &&
			_articleModel.getArticleImage().equals(articleModel.getArticleImage())  &&
			_articleModel.getArticleIntro().equals( articleModel.getArticleIntro() )&&
			_articleModel.getArticleContent() .equals( articleModel.getArticleContent() )&&
			_articleModel.getArticleLabelid() .equals( articleModel.getArticleLabelid()) &&
			_articleModel.getArticlePush().equals( articleModel.getArticlePush() )&&
			_articleModel.getArticleAccessright() .equals(articleModel.getArticleAccessright())
				){
			//没有修改，也判为成功！
			obj.put("error", 0);
			obj.put("userLogin", userLogin);
			return obj.toJSONString();
		}
		if(result.hasErrors()){
			// 接收BindingResult提示信息
			String defaultMessage = result.getFieldError().getDefaultMessage();
			// 返回提示信息
			return getError(defaultMessage);
		}
		//判断图片是否有修改过
		if(!_articleModel.getArticleImage().equals(articleModel.getArticleImage()) ){
			//重新拼接路径
			articleModel.setArticleImage("/article"+articleModel.getArticleImage());			
		}
	
		//判读修改不成功！提示！
		if(!articleService.updateArticleInfo(articleModel)){
			return getError("提交失败！");
		}
		//删除原来的图片信息
		String	filePath = _articleModel.getArticleImage();		
		//替换/article 为绝对路径
		filePath = filePath.replaceFirst("\\/article", PathUtil.getArticlePath());		
		OperationFileUtil.deleteFile(filePath);
		//真正修改提交成功！
		obj.put("error", 0);		
		obj.put("userLogin", userLogin);
		return obj.toJSONString();
		
	}
	//删除文章
	@RequestMapping(value="/del/{tid}",method=RequestMethod.POST)
	@ResponseBody
	public String del(@PathVariable(value="tid") Integer articleId,HttpSession session){
		
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		ArticleModel articleModel = articleService.selectArticleByAidAndUid(articleId, userid);
		if(articleModel == null){
			
			return  getError("删除失败!");
		}
		if(!articleService.delArticleById(articleId)){
			
			return getError("删除文章失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);	
		return obj.toJSONString();
	}
	
	//查看文章的访问权限 - start 
	@RequestMapping(value="/{userLogin}/get/{aid}",method=RequestMethod.POST)
	@ResponseBody
	public String get(@PathVariable(value="userLogin")String userLogin,@PathVariable(value="aid")Integer articleId,HttpSession session){
				//登录用户id
				int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
				
				//文章所属用户id，当前先为登录id
				int paUserid = (userService.selectByUserLogin(userLogin)).getId();
				//查询访问权限
				boolean result = articleService.selectgetAccessRight(articleId,paUserid,userid);
				if(result == false){		
					return getError(2,"您没有访问权限！");
				}
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				return obj.toJSONString();
	}
	
	
	//查看文章详情，进入这里需要利用异步判断是否有权限访问给文章内容
	@RequestMapping(value="/{userLogin}/about/{aid}",method=RequestMethod.GET)
	public String about(@PathVariable(value="userLogin")String userLogin,@PathVariable(value="aid")Integer articleId,ModelMap model, HttpSession session){
		//登录用户信息
		int loginUserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
	
		//获取当前用户id
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		//当前文章发表人id
		int paUserid = (userService.selectByUserLogin(userLogin)).getId();
		//访问用户信息	
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		//查询用户文章信息信息
		ArticleModel articleModel =  articleService.selectAboutByUserIdAndPaUserid(articleId,paUserid,userid);
		if(articleModel == null){
			//报错页面，
			return "aboutError.html";
		}
		model.addAttribute("articleModel", articleModel);
		//相关推荐
		List<ArticleModel> articleModels = articleService.selectArticleTOPTwelveByUserId(paUserid);
		if(articleModels.size() == 0) {
			model.addAttribute("recomment", null);

		} else {
			List<ArticleModel> list = new ArrayList<>();
			for (int i = 0; i < 2; i++) {
				list.add(articleModels.get(RandomUtils.nextInt(0, articleModels.size())));
			}
			model.addAttribute("recomment", list);
		}

		return "about";
		
		
	}
	
	//主页的文章数据
	@RequestMapping(value="/{userLogin}/articleinfo.json",method=RequestMethod.POST)
	@ResponseBody
	public String articleInfo(@PathVariable(value="userLogin")String userLogin,
							  @RequestParam(value="page",defaultValue="1")Integer page,HttpSession session){
		//访问用户的id
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		PageSupport<ArticleModel> pages = articleService.selectByJurOrUserOrAtt(page,userid);
		if(pages.getList().size() == 0){
			return getError("没有数据了");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("info", pages);
		
		return obj.toJSONString();
	}
	
	//增加浏览量
	@RequestMapping(value="autohits.json",method=RequestMethod.POST)
	@ResponseBody
	public String autoHits(@RequestParam(value="aid") Integer articleId){
		if (!articleService.updateHistCount(articleId)) {
			return getError("out");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	
	//文章推荐推送
	@RequestMapping(value="/retopush.json")
	@ResponseBody
	public String RecommendToPush(@RequestParam(value="userLogin")String userLogin){
		//访问博客的id
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		List<ArticleModel> articleModels = articleService.selectArticleTOPTwelveByUserId(userid);
		
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("articleInfo", articleModels);
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
	private String getError(Integer errorCode,String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", errorCode);
		obj.put("message", message);
		return obj.toJSONString();
	}
}
