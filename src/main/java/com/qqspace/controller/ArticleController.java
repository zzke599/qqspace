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
* @Description 	???????????????UI???
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
	
	
	//???????????????????????????
	@RequestMapping(value="/{userLogin}/article.html")
	public String article(@PathVariable(value="userLogin")String userLogin,@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="label", required=false)Integer labelId, ModelMap model,HttpSession session){
		//??????????????????
		int loginUserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
		//??????????????????
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		//???????????????????????????
		List<LabelModel> labelModels = labelService.getLablesByLabelUserId(userModel.getId());
		model.addAttribute("labelModels", labelModels);
		//???????????????????????????
		PageSupport<ArticleModel> pages = articleService.selectAllByUserId(userid,labelId,page);
		model.addAttribute("page", pages);
		if(labelId != null){
			model.addAttribute("label", labelId);
		}
		return "article";
	}
	//?????????????????????????????????
	@RequestMapping(value="/add.html")
	public String add(ModelMap model,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		List<LabelModel> labelModels = labelService.getLablesByLabelUserId(userModel.getId());
		model.addAttribute("labelModels", labelModels);
		return "/article/add";
	}
	
		// kindeditor????????????????????????,?????????????????????
		@RequestMapping(value = "/upload_img.json")
		@ResponseBody
		public String uploadImg(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws FileUploadException {
			int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			// ????????????????????????
			String savePath = PathUtil.getArticlePath() + File.separator+userid+File.separator;
			// ??????????????????URL
			String saveUrl = request.getContextPath() + File.separator+"article" + File.separator +
					userid+File.separator;
			// ????????????????????????????????????
			HashMap<String, String> extMap = new HashMap<String, String>();
			JSONObject obj = new JSONObject();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,mp4");
			
			// ??????????????????
			long maxSize = 104857600;

			if (!ServletFileUpload.isMultipartContent(request)) {

				return getError("??????????????????");
			}
			// ????????????
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				uploadDir.mkdirs();
			}
			String dirName = request.getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if (!extMap.containsKey(dirName)) {

				return getError("?????????????????????");
			}
			// ???????????????
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
			// ??????request?????????????????????
			Iterator<String> itr = multiRequest.getFileNames();
			while (itr.hasNext()) {
				// ?????????????????? (??????)
				MultipartFile file = multiRequest.getFile(itr.next());

				if (file != null) {
					// ??????????????????
					String fileName = file.getOriginalFilename();
					/*System.out.println("===============>?????????  ??? " + fileName);*/
					if (file.getSize() > maxSize) {

						return getError("?????????????????????????????????");
					}
					// ???????????????
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {

						return getError("????????????????????????????????????????????????\n?????????" + extMap.get(dirName) + "?????????");
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

						return getError("?????????????????????");
					}

					obj.put("error", 0);
					obj.put("url", saveUrl + newFileName);
				}
			}
			return obj.toJSONString();
		}

		// kindeditor????????????????????????????????????????????????
		@RequestMapping(value = "/uploadspace_img.json")
		@ResponseBody
		public String uploadSpaceImg(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws FileUploadException {
			int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			// ????????????????????????
			String rootPath = PathUtil.getArticlePath() + File.separator+userid+File.separator;
			// ??????????????????URL
			String rootUrl =  request.getContextPath() +File.separator+"article" + File.separator +
					userid+File.separator;

			JSONObject obj = new JSONObject();
			// ???????????????
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
			// ??????path???????????????????????????URL
			String path = request.getParameter("path") != null ? request.getParameter("path") : "";
			String currentPath = rootPath + path;
			String currentUrl = rootUrl + path;
			String currentDirPath = path;
			String moveupDirPath = "";
			if (!"".equals(path)) {
				String str = currentDirPath.substring(0, currentDirPath.length() - 1);
				moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
			}

			// ???????????????name or size or type
			String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

			// ???????????????..????????????????????????
			if (path.indexOf("..") >= 0) {

				return getError("Access is not allowed.");
			}
			// ????????????????????????/
			if (!"".equals(path) && !path.endsWith("/")) {

				return getError("Parameter is not valid.");
			}
			// ??????????????????????????????
			File currentPathFile = new File(currentPath);
			if (!currentPathFile.isDirectory()) {

				return getError("Directory does not exist.");
			}

			// ??????????????????????????????
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
			// ????????????????????????
			String savePath = PathUtil.getArticlePath() + File.separator+userid+File.separator;
			// ??????????????????URL
			String saveUrl =  File.separator +userid+File.separator;
			
			// ????????????????????????????????????
			HashMap<String, String> extMap = new HashMap<String, String>();
			JSONObject obj = new JSONObject();
			extMap.put("articleImg", "gif,jpg,jpeg,png,bmp");
		
			String oldFileName = articleImage.getOriginalFilename();//????????????
			/*System.out.println("===============>????????????"+oldFileName);*/
			// ??????????????????
			long maxSize = 11085760;
			// ????????????
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				uploadDir.mkdirs();
			}
			//??????
			String	dirName = "articleImg";
			// ???????????????
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
					// ??????????????????
					if (articleImage.getSize() > maxSize) {

						return getError("?????????????????????????????????");
					}
					// ???????????????
					String fileExt = oldFileName.substring(oldFileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {

						return getError("????????????????????????????????????????????????\n?????????" + extMap.get(dirName) + "?????????");
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_" + RandomUtils.nextInt(0, 1000000) + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						articleImage.transferTo(uploadedFile);
						
						if(StringUtils.isNotEmpty(imgurl)){
							//?????????????????????
							String filePath = PathUtil.getArticlePath()+imgurl;
							OperationFileUtil.deleteFile(filePath);
						}
					} catch (Exception e) {

						return getError("?????????????????????");
					}

					obj.put("error", 0);
					obj.put("url", saveUrl + newFileName);
				}
		
			
			return obj.toJSONString();
			
		}
		
		//?????????
		@RequestMapping(value="/front",method=RequestMethod.POST)
		@ResponseBody
		public String front(@Valid ArticleModel articleModel ,BindingResult result,HttpSession session){
			
			/*System.out.println("========>>?????????????????????  "+articleModel.toString());*/
			int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
			String userLogin =  ((UserModel)session.getAttribute(Constants.USER_SESSION)).getUserLogin();
			articleModel.setArticleUserid(userid);
			
			if(result.hasErrors()){
				// ??????BindingResult????????????
				String defaultMessage = result.getFieldError().getDefaultMessage();
				// ??????????????????
				return getError(defaultMessage);
			}
			if(!articleService.addArticleInfo(articleModel)){
				return getError("???????????????");
			}
			JSONObject obj = new JSONObject();
			
			obj.put("error", 0);
			obj.put("userLogin", userLogin);
			return obj.toJSONString();
		}
	//????????????????????????
	@RequestMapping(value="/edit/{aid}")
	public String  edit(@PathVariable(value="aid") Integer articleId,Model model,HttpSession session){
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		/*	System.out.println("????????????????????????     ??????ID???"+articleId);*/
		
		//????????????
		ArticleModel articleModel = articleService.selectArticleByAidAndUid(articleId,userid);
		
		//??????????????????
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		if(articleModel == null){
			
			return "/article/error.html";
		}
		model.addAttribute("articleModel", articleModel);
		
		//???????????????????????????
		List<LabelModel> labelModels = labelService.getLablesByLabelUserId(userModel.getId());
		model.addAttribute("labelModels", labelModels);
		
		return "article/edit";
	}
	//????????????????????????
	@RequestMapping(value="/change.json",method=RequestMethod.POST)
	@ResponseBody
	public String change (@Valid ArticleModel articleModel,BindingResult result ,HttpSession session){
		
		
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		String userLogin =  ((UserModel)session.getAttribute(Constants.USER_SESSION)).getUserLogin();
		//
		ArticleModel _articleModel = new ArticleModel();
		//???????????????
		_articleModel = articleService.selectArticleByAidAndUid(articleModel.getArticleId(), userid);
		
		JSONObject obj = new JSONObject();
		//?????????????????????????????????????????????!
		if(_articleModel.getArticleTitle().equals(articleModel.getArticleTitle())  &&
			_articleModel.getArticleImage().equals(articleModel.getArticleImage())  &&
			_articleModel.getArticleIntro().equals( articleModel.getArticleIntro() )&&
			_articleModel.getArticleContent() .equals( articleModel.getArticleContent() )&&
			_articleModel.getArticleLabelid() .equals( articleModel.getArticleLabelid()) &&
			_articleModel.getArticlePush().equals( articleModel.getArticlePush() )&&
			_articleModel.getArticleAccessright() .equals(articleModel.getArticleAccessright())
				){
			//?????????????????????????????????
			obj.put("error", 0);
			obj.put("userLogin", userLogin);
			return obj.toJSONString();
		}
		if(result.hasErrors()){
			// ??????BindingResult????????????
			String defaultMessage = result.getFieldError().getDefaultMessage();
			// ??????????????????
			return getError(defaultMessage);
		}
		//??????????????????????????????
		if(!_articleModel.getArticleImage().equals(articleModel.getArticleImage()) ){
			//??????????????????
			articleModel.setArticleImage("/article"+articleModel.getArticleImage());			
		}
	
		//?????????????????????????????????
		if(!articleService.updateArticleInfo(articleModel)){
			return getError("???????????????");
		}
		//???????????????????????????
		String	filePath = _articleModel.getArticleImage();		
		//??????/article ???????????????
		filePath = filePath.replaceFirst("\\/article", PathUtil.getArticlePath());		
		OperationFileUtil.deleteFile(filePath);
		//???????????????????????????
		obj.put("error", 0);		
		obj.put("userLogin", userLogin);
		return obj.toJSONString();
		
	}
	//????????????
	@RequestMapping(value="/del/{tid}",method=RequestMethod.POST)
	@ResponseBody
	public String del(@PathVariable(value="tid") Integer articleId,HttpSession session){
		
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		ArticleModel articleModel = articleService.selectArticleByAidAndUid(articleId, userid);
		if(articleModel == null){
			
			return  getError("????????????!");
		}
		if(!articleService.delArticleById(articleId)){
			
			return getError("?????????????????????");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);	
		return obj.toJSONString();
	}
	
	//??????????????????????????? - start 
	@RequestMapping(value="/{userLogin}/get/{aid}",method=RequestMethod.POST)
	@ResponseBody
	public String get(@PathVariable(value="userLogin")String userLogin,@PathVariable(value="aid")Integer articleId,HttpSession session){
				//????????????id
				int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
				
				//??????????????????id?????????????????????id
				int paUserid = (userService.selectByUserLogin(userLogin)).getId();
				//??????????????????
				boolean result = articleService.selectgetAccessRight(articleId,paUserid,userid);
				if(result == false){		
					return getError(2,"????????????????????????");
				}
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				return obj.toJSONString();
	}
	
	
	//?????????????????????????????????????????????????????????????????????????????????????????????
	@RequestMapping(value="/{userLogin}/about/{aid}",method=RequestMethod.GET)
	public String about(@PathVariable(value="userLogin")String userLogin,@PathVariable(value="aid")Integer articleId,ModelMap model, HttpSession session){
		//??????????????????
		int loginUserid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		UserModel loginUserModel = userService.selectByUserId(loginUserid);
		model.addAttribute("loginUser", loginUserModel);
	
		//??????????????????id
		int userid = ((UserModel)session.getAttribute(Constants.USER_SESSION)).getId();
		//?????????????????????id
		int paUserid = (userService.selectByUserLogin(userLogin)).getId();
		//??????????????????	
		UserModel userModel = userService.selectByUserId(userid);
		model.addAttribute("userModel", userModel);
		//??????????????????????????????
		ArticleModel articleModel =  articleService.selectAboutByUserIdAndPaUserid(articleId,paUserid,userid);
		if(articleModel == null){
			//???????????????
			return "aboutError.html";
		}
		model.addAttribute("articleModel", articleModel);
		//????????????
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
	
	//?????????????????????
	@RequestMapping(value="/{userLogin}/articleinfo.json",method=RequestMethod.POST)
	@ResponseBody
	public String articleInfo(@PathVariable(value="userLogin")String userLogin,
							  @RequestParam(value="page",defaultValue="1")Integer page,HttpSession session){
		//???????????????id
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		PageSupport<ArticleModel> pages = articleService.selectByJurOrUserOrAtt(page,userid);
		if(pages.getList().size() == 0){
			return getError("???????????????");
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("info", pages);
		
		return obj.toJSONString();
	}
	
	//???????????????
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
	
	//??????????????????
	@RequestMapping(value="/retopush.json")
	@ResponseBody
	public String RecommendToPush(@RequestParam(value="userLogin")String userLogin){
		//???????????????id
		int userid = (userService.selectByUserLogin(userLogin)).getId();
		List<ArticleModel> articleModels = articleService.selectArticleTOPTwelveByUserId(userid);
		
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("articleInfo", articleModels);
		return obj.toJSONString();
	}
	
	
	
	
	// ======================== ???????????? ====================================
	/**
	 * json???????????????
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
