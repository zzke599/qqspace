package com.qqspace.service.admin;

import com.qqspace.bean.Admin;
import com.qqspace.dao.AdminMapper;
import com.qqspace.service.article.ArticleService;
import com.qqspace.service.comment.CommentService;
import com.qqspace.service.commentback.CommentBackService;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Ww
 * @date 2021/6/23
 */
@Service("AdminService")
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    public CommentBackService commentBackService;

    @Override
    @Transactional// 开启事务管理
    public Admin adminLogin(Admin admin) {
        //数据效验
        if(admin == null){
            return null;
        }
        try {
            admin.setAdminPassword(Constants.EncodeByMd5(admin.getAdminPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Admin dbAdmin = adminMapper.selectByNameAndPassword(admin);
        if (dbAdmin != null &&
                StringUtils.equals(admin.getAdminPassword(),dbAdmin.getAdminPassword())) {

            return dbAdmin;
        }
        return null;
    }

    @Override
    public Admin selectAdminById(int loginAdminId) {

        return adminMapper.selectByPrimaryKey(loginAdminId);
    }

    @Override
    public Map<String, Object> selectStatisicsData() {
        Map<String,Object> map = new HashMap<>();
        // 用户总数
        int userCount = userService.selectAllUsersCount();
        map.put("userCount", userCount);
        // 1表示审核通过的文章
        int accessRight = 1;
        int allArticleCount = articleService.selectArticlesCountByAccessright(null);
        // 文章总数
        map.put("allArticlesCount",allArticleCount);
        int auditArticlesCount = articleService.selectArticlesCountByAccessright(-1);
        // 待审核文章数量
        map.put("auditArticlesCount",auditArticlesCount);
        int commentCount = commentService.selectAllCommentCount();
        // 评论总数
        map.put("commentCount",commentCount);
        // 回复评论总数
        int commentBackCount = commentBackService.selectAllCommentBackCount();
        map.put("commentBackCount",commentBackCount);

        return map;
    }
}
