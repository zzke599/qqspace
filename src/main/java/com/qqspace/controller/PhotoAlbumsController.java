package com.qqspace.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.qqspace.service.model.PhotoAlbumsModel;
import com.qqspace.service.model.PhotoDetailModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.photoAlbums.PhotoAlbumsService;
import com.qqspace.service.photoDetail.PhotoDetailService;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;
import com.qqspace.tool.PageSupport;
import com.qqspace.tool.PathUtil;



@Controller
@RequestMapping("/user/pic")
public class PhotoAlbumsController {

	@Autowired
	private UserService userService;
	@Autowired
	private PhotoAlbumsService photoAlbumsService;
	@Autowired
	private PhotoDetailService photoDetailService;
	
	
	//跳转相册页面
	@RequestMapping(value="/{userLogin}/listpic.html")
	public String listPic(@PathVariable(value="userLogin")String userLogin,@RequestParam(value="page",defaultValue="1")Integer page,ModelMap model,HttpSession session){
		//登录用户
		int loginUserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
		//访问用户
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		//查询相册信息
		PageSupport<PhotoAlbumsModel> pages = photoAlbumsService.selectByUseid(userid, page);				
		model.addAttribute("page", pages);
		return "listpic";
	}
	//跳转至添加相册页面
	@RequestMapping(value="add.html")
	public String add(){
		
		return "/pic/add";
	}
	//跳转至上传图片页面
	@RequestMapping(value="uploadpic.html")
	public String uploadPic(HttpSession session,ModelMap model){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		//查询相册
		List<PhotoAlbumsModel> photoAlbumsModels = photoAlbumsService.selectAlbumsByUseid(userid);
		
		model.addAttribute("photoAlbumsModels", photoAlbumsModels);
		
		return "/pic/uploadpic";
	}
	
	//跳转至相册详情页
	@RequestMapping(value="/{userLogin}/photodetail/{pid}")
	public String photoDetail(@PathVariable(value="userLogin")String userLogin,@PathVariable(value="pid")Integer paId, ModelMap model,HttpSession session){
		//当前登录id
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		//登录用户
		UserModel loginUserModel = userService.selectByUserId(userid);
		model.addAttribute("loginUser", loginUserModel);
		//所属用户id
		int paUserid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(paUserid);
		model.addAttribute("userModel", userModel);
		//查询用户相册信息信息
		PhotoAlbumsModel photoAlbumsModel =  photoAlbumsService.selectAlbumsByPaIdAndPaUserid(paId,paUserid,userid);
		//为空跳转，404页面
		if(photoAlbumsModel == null){
			
			return "/pic/errorDetail";
		}
		model.addAttribute("photoAlbumsModel", photoAlbumsModel);
		//不为本人跳转博友页面
		if(paUserid != userid){			
			return "/pic/getDetail";
		}
		//本人跳转至有业务的页面
		return "/pic/photoDetail";
	}
	
	//添加相册
	@RequestMapping(value="addpic.json",method=RequestMethod.POST)
	@ResponseBody
	public String addPic(@Valid PhotoAlbumsModel photoAlbumsModel,BindingResult result ,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		photoAlbumsModel.setPaUserid(userid);
		if(result.hasErrors()){
			// 接收BindingResult提示信息
			String defaultMessage = result.getFieldError().getDefaultMessage();
			// 返回提示信息
			return getError(defaultMessage);
		}
		if(!photoAlbumsService.addPhotoAlbums(photoAlbumsModel)){
			return getError("添加相册失败！");
		}
		JSONObject obj = new JSONObject();
		//添加成功！，返回提示
		obj.put("error", 0);
		obj.put("message", "添加成功！");
		return obj.toJSONString();
	}
	//跳转修改相册修改页
	@RequestMapping(value="/edit/{pid}")
	public String edit(@PathVariable(value="pid")Integer paId,HttpSession session,ModelMap model){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		
		PhotoAlbumsModel photoAlbumsModel = photoAlbumsService.selectPicByPaidAndUseid(paId,userid);
		//######出错了，应该跳转到统一的报错页，这里先重定向到相册首页######
		if(photoAlbumsModel == null){
			return "/pic/error";
		}
		//返回数据模型到页面
		model.addAttribute("photoAlbumsModel", photoAlbumsModel);
		return "/pic/edit";
	}
		//修改相册
	@RequestMapping(value="editpic.json",method=RequestMethod.POST)
	@ResponseBody
	public String editPic(@Valid PhotoAlbumsModel photoAlbumsModel,BindingResult result ){
			
			if(result.hasErrors()){
				// 接收BindingResult提示信息
				String defaultMessage = result.getFieldError().getDefaultMessage();
				// 返回提示信息
				return getError(defaultMessage);
			}
			if(!photoAlbumsService.updatePhotoAlbumsByPaId(photoAlbumsModel)){
				return getError("修改失败！");
			}
			JSONObject obj = new JSONObject();
			//添加成功！，返回提示
			obj.put("error", 0);
			obj.put("message", "修改成功！");
			return obj.toJSONString();
	}
	//提交上传图片
	@RequestMapping(value="upload.json")
	@ResponseBody
	public String upload(@RequestParam("paId")Integer paId,
							HttpServletRequest request,HttpServletResponse response,HttpSession session){
		/*System.out.println("============>> 图片准备开始处理。。。。");*/
		MultipartHttpServletRequest murequest = (MultipartHttpServletRequest)request;
		//得到文件map对象
		Map<String,MultipartFile> files = murequest.getFileMap();
		// 文件保存目录路径
		String savePath = PathUtil.getAlbumPath() + File.separator;
		// 文件保存目录URL
		String saveUrl = "/album" + File.separator;
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		JSONObject obj = new JSONObject();
		extMap.put("images", "gif,jpg,jpeg,png,bmp");
		// 最大文件大小10M
		long maxSize = 10485760;

		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			uploadDir.mkdirs();
		}
		// 创建images文件夹
		String dirName = "images";
		savePath += dirName + File.separator;
		saveUrl += dirName + File.separator;
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		//遍历文件上传
		for (MultipartFile file : files.values()) {
			// 加入以用户id为文件夹名称
			Integer userid =((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			savePath += userid + File.separator;
			saveUrl += userid + File.separator;
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			if (file != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String ymd = sdf.format(new Date());
				savePath += ymd + File.separator;
				saveUrl += ymd + File.separator;
				File ymdFile = new File(savePath);
				if (!ymdFile.exists()) {
					ymdFile.mkdirs();
				}
				// 检查文件大小
				//获取原图片名称
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
				String newFileName ="pd"+ df.format(new Date()) + "_" + RandomUtils.nextInt(0, 1000000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					// 上传文件
					file.transferTo(uploadedFile);

				} catch (Exception e) {

					return getError("上传文件失败。");
				}

				// 把信息加入模型中
				PhotoDetailModel photoDetailModel = new PhotoDetailModel();
				photoDetailModel.setPdPaid(paId);
				//图片相对路径
				photoDetailModel.setPdImgurl(saveUrl+newFileName);
				if(!photoDetailService.addphotoDetailInfo(photoDetailModel)){
					return getError("===========>>  数据添加失败！");
				}
				System.out.println("===========>>  数据添加成功！");
		}
		}

		obj.put("error", 0);
		return obj.toJSONString();
	}
	 
	//删除相册
	@RequestMapping(value="/del/{pid}")
	@ResponseBody
	public String delPic(@PathVariable(value="pid")Integer paId,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		if(!photoAlbumsService.delphotpAlbumsAndImg(paId,userid)){
			return getError("删除相册失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	//相册内跳转上传图片URL
	@RequestMapping(value="/uploadpics/{pid}")
	public String uploadpics(@PathVariable(value="pid")Integer paId,HttpSession session,ModelMap model){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		//查询相册
		PhotoAlbumsModel photoAlbumsModel = photoAlbumsService.selectPicByPaidAndUseid(paId, userid);
		if(photoAlbumsModel == null){
			//跳转至报错页面 ，这里先跳转到相册列表页
			return "/pic/error";
		}
		model.addAttribute("pdPaId",paId);
		return "/pic/picuploadImg";
	}
	//查看访问权限
	@RequestMapping(value="/{userLogin}/get/{pid}")
	@ResponseBody
	public String get(@PathVariable(value="userLogin")String userLogin,@PathVariable(value="pid")Integer paId,HttpSession session,ModelMap model){
		//登录用户id
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		//查询相册
		//相册所属用户id，当前先为登录id
		int paUserid = (userService.selectByUserLogin(userLogin)).getId();
		//查询访问权限
		boolean result = photoAlbumsService.selectPicAccessRight(paId,paUserid,userid);
		if(!result){
			return getError(2, "您没有访问权限！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	//设置封面图片
	@RequestMapping(value="/upcover/{pid}",method=RequestMethod.POST)
	@ResponseBody
	public String updateCover(@PathVariable(value="pid")Integer paId,
					@RequestParam(value="mid")Integer pdId,HttpSession session){
			//获取用户id
			Integer userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			PhotoAlbumsModel photoAlbumsModel = new PhotoAlbumsModel();
			photoAlbumsModel.setPaId(paId);
			
			photoAlbumsModel.setPaUserid(userid);
			if(!photoAlbumsService.updateBypaIdAnduserid(photoAlbumsModel,pdId)){
				return getError("设置失败！");
			}
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
		return obj.toJSONString();
	}
	
	//修改描述信息
	@RequestMapping(value="/describe/{mid}",method=RequestMethod.POST)
	@ResponseBody
	public String updateDescribe(@PathVariable(value="mid")Integer pdId,
			@RequestParam(value="pdDescription",required=false)String pdDescription){
		//判断 pdDescription ==null,等于空，统一等于一个空格符
		if(pdDescription.trim() == null && pdDescription.trim() == ""){
			pdDescription =" ";
		}
		/*System.out.println("================>> 描述为"+pdDescription);*/
		//把数据 传给 PhotoDetailModel
		PhotoDetailModel  photoDetailModel  = new PhotoDetailModel();
		photoDetailModel.setPdId(pdId);
		photoDetailModel.setPdDescription(pdDescription);
		//修改操作
		if(!photoDetailService.updatePdDescriptionBypdId(photoDetailModel)){
			return getError("修改失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	//删除单张图片
	@RequestMapping(value="/delPhoto/{mid}",method=RequestMethod.POST)
	@ResponseBody
	public String delPhoto(@PathVariable(value="mid")Integer pdId){
		
		if(pdId == null){
			return getError("删除失败！");
		}
		if(!photoDetailService.delPhotoByPdId(pdId)){
			return getError("删除失败！");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		return obj.toJSONString();
	}
	//批量删除图片
	@RequestMapping(value="/delphotos.json")
	@ResponseBody
	public String delPhotos(Integer[] mids){
		if(mids.length <=0){
			return getError("批量删除失败！");
		}
		if(!photoDetailService.delPhotoByPdIds(mids)){
			return getError("删除失败！");
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
		private String getError(Integer errorCode,String message) {
			JSONObject obj = new JSONObject();
			obj.put("error", errorCode);
			obj.put("message", message);
			return obj.toJSONString();
		}
}
