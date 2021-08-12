package com.qqspace.controller;

import com.alibaba.fastjson.JSONObject;
import com.qqspace.bean.Admin;
import com.qqspace.bean.User;
import com.qqspace.service.admin.AdminService;
import com.qqspace.service.article.ArticleService;
import com.qqspace.service.model.ArticleModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;
import com.qqspace.tool.LayUIPageUtil;
import com.qqspace.vo.EChartsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ww
 * @date 2021/6/23
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;


    @RequestMapping("/login.html")
    public String doLogin() {
        return "/admin/login";

    }
    @RequestMapping("/welcome.html")
    public String doWelcome(HttpSession session, Model model) {
        int loginAdminId = ((Admin) session.getAttribute(Constants.ADMIN_SESSION)).getId();
        Admin admin = adminService.selectAdminById(loginAdminId);
        model.addAttribute("admin",admin);
        return "/admin/welcome";
    }

    @RequestMapping("/userlist.html")
    public String doUsers(){
        return "/admin/member-list";
    }
    @RequestMapping("/articles.html")
    public String articles(){
        return "/admin/article-list";
    }



    @RequestMapping("/main.html")
    public String doMain(HttpSession session, ModelMap model) {
        int loginAdminId = ((Admin) session.getAttribute(Constants.ADMIN_SESSION)).getId();
        if (loginAdminId <= 0) {
            return "redirect:/admin/login.html";
        }
        Admin admin = adminService.selectAdminById(loginAdminId);
        model.addAttribute("admin",admin);
        return "/admin/index";
    }

    // 登录效验
    @RequestMapping(value = "/dologin.json", method = RequestMethod.POST)
    @ResponseBody
    public String dologin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                          @RequestParam String adminCode, @RequestParam String adminPassword,
                          @RequestParam String valid,
                          Map<String, Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject obj = new JSONObject();
        String _verifyCodeValue = ((String) session.getAttribute("verifyCodeValue")).toUpperCase();
        // 转换成大写
        valid = valid.toUpperCase();
        if (!_verifyCodeValue.equals(valid)) {
            return this.getError(2,"验证码错误");
        }
        // 效验用户名或密码是否为空
        Admin a = new Admin();
        a.setAdminCode(adminCode);
        a.setAdminPassword(adminPassword);
        a = adminService.adminLogin(a);
        if (a == null){
            return this.getError(1,"用户名或密码不正确");
        }
        session.setAttribute(Constants.ADMIN_SESSION, a);
        obj.put("error", 0);
        return obj.toJSONString();
    }

    @RequestMapping(value = "/countData.json", method = RequestMethod.GET)
    @ResponseBody
    public String statisticsData(HttpSession session){
        JSONObject obj = new JSONObject();
        Map<String,Object> map = adminService.selectStatisicsData();
        //打印在线人数
        Integer count =  (Integer) session.getAttribute(Constants.onLineUserCount);
        //打印在线人数
        map.put("lineCount",count == null ? 0: count);
        System.out.println("====================>>>" + map);
        obj.put("countData",map);
        return obj.toJSONString();
    }



    @RequestMapping("datacount.html")
    public String doDataCount() {
        return "/admin/datacount";
    }

    @GetMapping("/getNearlyWeekAddUserCount.json")
    @ResponseBody
    public String getNearlyWeekAddUserCount() {
        JSONObject object = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        List<EChartsVO> addUserCount = userService.selectNearlyWeekAddUserCount();
        addUserCount.sort(EChartsVO::compareTo);
        List<String> dates = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        addUserCount.forEach(userVO -> {
            dates.add(userVO.getDate());
            nums.add(userVO.getCount());
        });
        map.put("dateList",dates);
        map.put("countList",nums);
        object.put("data",map);
        return object.toJSONString();
    }

    @GetMapping("/getNearlyMonthArticleCount.json")
    @ResponseBody
    public String getNearlyMonthArticles(Integer accessRight) {
        JSONObject object = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        List<EChartsVO> list = articleService.selectMonthArticlesByAccessRight(null);
        List<EChartsVO> checkedArticlelist = articleService.selectMonthArticlesByAccessRight(1);
        list.sort(EChartsVO::compareTo);
        List<String> dates = new ArrayList<>();
        List<Integer> allCountList = new ArrayList<>();
        List<Integer> checkedCountList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            dates.add(list.get(i).getDate());
            allCountList.add(list.get(i).getCount());
            checkedCountList.add(checkedArticlelist.get(i).getCount());
        }
        map.put("dateList",dates);
        map.put("allCountList",allCountList);
        map.put("checkedCountList",checkedCountList);
        object.put("data",map);
        return object.toJSONString();
    }


    @RequestMapping("/userList.json")
    @ResponseBody
    public String getUserList(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                              @RequestParam(value = "limit",defaultValue = "10",required = false) Integer limit,
                              @RequestParam(value = "userName",required = false) String userName,
                              @RequestParam(value = "telePhone",required = false) String telePhone,
                              @RequestParam(value = "email",required = false) String email) {
        JSONObject object = new JSONObject();

        LayUIPageUtil<User> pageUtil =  userService.selectAllUsersWithPageByCondition(page,limit,userName,telePhone,email);
        object.put("data",pageUtil);


        return object.getJSONObject("data").toJSONString();

    }
    @RequestMapping("/articleList.json")
    @ResponseBody
    private String getArticleList(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                  @RequestParam(value = "limit",defaultValue = "10",required = false) Integer limit,
                                  @RequestParam(value = "userId", required = false) Integer userId,
                                  @RequestParam(value = "articleTitle",required = false) String articleTitle,
                                  @RequestParam(value = "articleAuthor",required = false) String articleAuthor,
                                  @RequestParam(value = "accessRight",required = false) Integer accessRight){
        JSONObject object = new JSONObject();
        LayUIPageUtil<ArticleModel> pageUtil =  articleService.selectAllArticlesWithPageByCondition(page,limit,
                userId,articleTitle,articleAuthor,accessRight);
        object.put("data",pageUtil);
        return object.getJSONObject("data").toJSONString();

    }

    // =======================================================================
    private String getError(int error,String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", error);
        obj.put("message", message);
        return obj.toJSONString();
    }


}
