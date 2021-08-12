package com.qqspace.service.article;



import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.qqspace.tool.LayUIPageUtil;
import com.qqspace.vo.EChartsVO;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.Article;
import com.qqspace.bean.Comment;
import com.qqspace.bean.HitsCount;

import com.qqspace.dao.ArticleMapper;
import com.qqspace.dao.CommentBackMapper;
import com.qqspace.dao.CommentLikeCountMapper;
import com.qqspace.dao.CommentMapper;
import com.qqspace.dao.HitsCountMapper;
import com.qqspace.service.follow.FollowService;
import com.qqspace.service.model.ArticleModel;
import com.qqspace.service.model.FollowModel;

import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.PageSupport;





@Component("ArticleService")
public class ArticleServiceImpl implements ArticleService {
	
	@Resource
	private ArticleMapper articleMapper;
	@Autowired
	private UserService userService;
	@Resource
	private HitsCountMapper hitsCountMapper;
	@Autowired
	private FollowService followService;
	@Resource 
	private CommentMapper commentMapper;
	@Resource
	private CommentBackMapper commentBackMapper;
	@Resource
	private CommentLikeCountMapper commentLikeCountMapper;
	
	@Override
	@Transactional
	public boolean addArticleInfo(ArticleModel articleModel) {
		
		//默认当前时间
		articleModel.setArticleCreatedate(DateTime.now().toDate());
		//	默认通过！
		articleModel.setArticleExamine(1);
		if(StringUtils.isNotEmpty(articleModel.getArticleImage())){
		articleModel.setArticleImage("/article"+articleModel.getArticleImage());
		}
		//数据转换 articleModel =》article
		Article article = convertArticleFromArticleModel(articleModel);
		
		//执行数据库操作
		int result = articleMapper.insertSelective(article);
		if(result > 0){
			//获取创建时的主键id
			articleModel.setArticleId(article.getArticleId());
			HitsCount hitsCount =this.convertHitsCountFromModel(articleModel);
			hitsCountMapper.insertSelective(hitsCount);
		}
		return result >0 ? true : false;
	}
	
	@Override
	public List<ArticleModel> selectArticleModelByLid(Integer articleLabelid) {
		if(articleLabelid == null){
			return null;
		}
		List<Article> articles = articleMapper.selectByLabelid(articleLabelid);
		//articles的集合数据传给 articleModels的集合
		
		List<ArticleModel> articleModels = articles.stream().map(article ->{
			//查询访问量
			HitsCount hitsCount =hitsCountMapper.selectByPrimaryKey(article.getArticleId());
			ArticleModel articleModel = this.convertModelFromHitsCount(article,hitsCount);
			
			return articleModel;
		}).collect(Collectors.toList());
		
		return articleModels;
	}

	@Override
	public ArticleModel selectArticleByAidAndUid(Integer articleId,Integer articleUserid) {
		//判空处理
		if(articleId == null || articleUserid == null){
			
			return null;
		}
		//执行数据操作
		Article article = articleMapper.selectByAidAndUid(articleId,articleUserid);
		//查询访问量
		HitsCount hitsCount =hitsCountMapper.selectByPrimaryKey(articleId);
		// 数据转换 article => articleModel
		ArticleModel articleModel = convertModelFromHitsCount(article,hitsCount);
		
		return articleModel;
	}
	
	@Override
	@Transactional //开启事务处理
	public boolean updateArticleInfo(ArticleModel articleModel) {
		//数据转换 articleModel =》article
		Article article = convertArticleFromArticleModel(articleModel);
		//执行数据库操作
		int result = articleMapper.updateByPrimaryKeySelective(article);
		
		return result > 0 ? true : false;
	}

	@Override
	@Transactional	//开启事务处理
	public boolean delArticleById(Integer articleId) {
		//判空
		if(articleId == null){
			return false;
		}
		//执行数据库操作
		
		
			hitsCountMapper.deleteByPrimaryKey(articleId);
			List<Comment> comments = commentMapper.selectByarticleId(articleId);
			for (Comment comment : comments) {
				commentBackMapper.deleteBycbCommentid(comment.getCoId());
				commentLikeCountMapper.deleteByclCommentid(comment.getCoId());
				
			}
			commentMapper.deleteByarticleId(articleId);
	
		int result = articleMapper.deleteByPrimaryKey(articleId);
		return result > 0 ? true : false;
	}
	
	
	
	@Override
	public PageSupport<ArticleModel> selectByJurOrUserOrAtt(Integer page, Integer userid) {
		//创建分页对象
		PageSupport<ArticleModel> pageSupport = new PageSupport<ArticleModel>();
				//查询总条数
				int count = articleMapper.selectByArticleCount(userid);
				//每页容量
				pageSupport.setPageSize(6);
				pageSupport.setTotalCount(count);
				pageSupport.setCurrentPageNo(page);
						
				//从哪开始
				int begin=(pageSupport.getCurrentPageNo()-1)*pageSupport.getPageSize();
				//查多少条记录
				int recordSize = pageSupport.getPageSize();
				List<Article> articles =articleMapper.selectByArticleUserid(userid,begin,recordSize);
				List<ArticleModel> articleModels = articles.stream().map(article ->{
					//查询访问量
					HitsCount hitsCount =hitsCountMapper.selectByPrimaryKey(article.getArticleId());
					ArticleModel articleModel = this.convertModelFromHitsCount(article,hitsCount);
					//查询文章发表者的信息
					UserModel userModel = userService.selectByUserId(article.getArticleUserid());
					articleModel.setUserModel(userModel);
					//是否关注过 默认false;
					boolean foId = false;
					//查询关注信息，有关注过返回true,反之
					if(followService.selectByUserIdAndBeUserId(userid, article.getArticleUserid()) != null){
						foId = true;
					}
					articleModel.setFoId(foId);
					return articleModel;
				}).collect(Collectors.toList());
				pageSupport.setList(articleModels);
				return pageSupport;
	}
	
	@Override
	@Transactional
	public boolean updateHistCount(Integer articleId) {
		//数据效验
		if (articleId == null) {
			
			return false;
		}
		int result = hitsCountMapper.updateAutoCountByArticleId(articleId);
		return  result > 0 ? true : false;
	}
	
	@Override
	public PageSupport<ArticleModel> selectAllByUserId(Integer userid,Integer labelId,Integer page) {
		if(userid == null || page == null){
			return null;
		}
		
		//创建分页对象
		PageSupport<ArticleModel> pageSupport = new PageSupport<ArticleModel>();
		//查询总条数
		int count = articleMapper.selectAllCountByUserid(userid,labelId);
		//每页容量
		pageSupport.setPageSize(6);
		pageSupport.setTotalCount(count);
		pageSupport.setCurrentPageNo(page);
		//判断当前页是否大于总页数，是否小于1。
		if(page < 1){
			//当前页数=1
			pageSupport.setCurrentPageNo(1);
		}
		if(page > pageSupport.getTotalPageCount()){
			pageSupport.setCurrentPageNo(pageSupport.getTotalPageCount());
		}	
		//从哪开始
		int begin=(pageSupport.getCurrentPageNo()-1)*pageSupport.getPageSize();
		//查多少条记录
		int recordSize = pageSupport.getPageSize();
		List<Article> articles =articleMapper.selectAllByUserid(userid,labelId, begin, recordSize);
		List<ArticleModel> articleModels = articles.stream().map(article ->{
			//查询访问量
			HitsCount hitsCount =hitsCountMapper.selectByPrimaryKey(article.getArticleId());
			ArticleModel articleModel = this.convertModelFromHitsCount(article,hitsCount);
			
			return articleModel;
		}).collect(Collectors.toList());
		pageSupport.setList(articleModels);
		return pageSupport;
	}
	
	@Override
	public boolean selectgetAccessRight(Integer articleId, Integer paUserid, Integer userid) {
		//数据效验
				if(articleId == null || paUserid == null || userid == null){
					return false;
				}
				//执行数据操作
				ArticleModel articleModel = this.selectArticlePerByAid(articleId, paUserid);
				if(articleModel == null){
					return false;
				}
				//公开，返回true
				if(articleModel.getArticleAccessright().equals(1)){
					return true;
				}
				//博友，判断是否为博友/*相互关注*/是为true，反之
				if(articleModel.getArticleAccessright().equals(2)){
					/*这里先返回true，根据关注信息查询*/
					//需要补充
					if(!userid.equals(paUserid)){		//判断不是本人访问 
					 FollowModel followModel = followService.selectByUserIdAndBeUserId(userid, paUserid);
					return followModel ==null ? false : true;
					}
					return true;
				}
				//判断是否为本人
				if(articleModel.getArticleAccessright().equals(3) ){
					
					return paUserid == userid ;
				}
				return false;
	}
	
	@Override
	public ArticleModel selectArticlePerByAid(Integer articleId,Integer articleUserid) {
		//判空
		if(articleId == null || articleUserid == null){
			 return null;
		}
		//执行数据库操作
		Article article = articleMapper.selectByPrimaryKey(articleId);
		//判断查询结果是否为空
		if(article == null){
			return null;
		}
		ArticleModel articleModel = null;
		//查询发文章人的信息
		UserModel userModel = userService.selectUserInfoAndPhotoById(article.getArticleUserid());
		//查询访问量
		HitsCount hitsCount =hitsCountMapper.selectByPrimaryKey(articleId);
		articleModel = convertModelFromHitsCount(article,hitsCount);
		articleModel.setUserModel(userModel);
		
		return articleModel;
	}  
	
	@Override
	public List<ArticleModel> selectArticleTOPTwelveByUserId(Integer articleUserid) {
		if(articleUserid == null){
			return null;
		}
		List<Article>articles = articleMapper.selectArticleTOPTwelveByUserId(articleUserid);
		List<ArticleModel> articleModels = articles.stream().map(article ->{
			
			ArticleModel articleModel = this.convertModelFromHitsCount(article,null);
			//查询作者的信息
			UserModel userModel = userService.selectByUserId(articleModel.getArticleUserid());
			articleModel.setUserModel(userModel);
			return articleModel;
		}).collect(Collectors.toList());
		
		return articleModels;
	}

	@Override
	public Integer selectArticlesCountByAccessright(Integer articleAccessright) {
		return articleMapper.selectArticlesCountByAccessright(articleAccessright);
	}

	@Override
	public List<EChartsVO> selectMonthArticlesByAccessRight(Integer accessRight) {

		return articleMapper.selectMonthArticlesByAccessRight(accessRight);
	}

	@Override
	public LayUIPageUtil<ArticleModel> selectAllArticlesWithPageByCondition(Integer page, Integer limit, Integer userId, String articleTitle, String articleAuthor, Integer accessRight) {
		//查询总条数
		int count = articleMapper.selectAllCountByCondition(userId,articleTitle,articleAuthor,accessRight);
		int offset = (page - 1) * limit;
		List<Article> articles =articleMapper.selectAllByCondition(offset,limit,userId,articleTitle, articleAuthor, accessRight);
		List<ArticleModel> articleModels = articles.stream().map(article ->{
			//查询访问量
			HitsCount hitsCount =hitsCountMapper.selectByPrimaryKey(article.getArticleId());
			ArticleModel articleModel = this.convertModelFromHitsCount(article,hitsCount);
			return articleModel;
		}).collect(Collectors.toList());
		//创建分页对象
		LayUIPageUtil<ArticleModel> pageUtil = new LayUIPageUtil<ArticleModel>(count,articleModels);

		return pageUtil;
	}


	@Override
	public ArticleModel selectAboutByUserIdAndPaUserid(Integer articleId, Integer paUserid, Integer userid) {
		//数据效验
		if(articleId == null || paUserid == null || userid == null){
			return null;
		}
		//执行数据操作
		ArticleModel articleModel = this.selectArticlePerByAid(articleId,paUserid);
		//判空
		if(articleModel == null){
			
			return null;
		}
		//公开
		if(articleModel.getArticleAccessright().equals(1)){
			 return articleModel;
		}
		//博友
		if(articleModel.getArticleAccessright().equals(2)){
			//判断是否为博友，这里先返回数据
			//补充;
			 return articleModel;
		}
		if(articleModel.getArticleAccessright().equals(3)){
			 if(paUserid ==userid)
			 return articleModel;
		}
		return null;
	
	}
	
	// ============================================================
	// ================= ===== 工具方法================================

	
    //将Article和 HitsCount 转化到 ArticleModel
    private ArticleModel convertModelFromHitsCount(Article article,HitsCount hitsCount){
    	 if (article == null){
             return null;
         }
    	ArticleModel articleModel = new ArticleModel();
    	
        BeanUtils.copyProperties(article,articleModel);
        if (hitsCount != null) {
        	articleModel.setHiCount(hitsCount.getHiCount());
        }
        return articleModel;
    }
	//将 ArticleModel 转化为 Article
    private Article  convertArticleFromArticleModel(ArticleModel articleModel){
        if (articleModel == null){
            return null;
        }
        Article  article = new Article();
        BeanUtils.copyProperties(articleModel,article);
        return article;
    }
    // 把ArticleModel转换为HitsCount
 	private HitsCount convertHitsCountFromModel(ArticleModel articleModel) {
 		if (articleModel == null) {
 			return null;
 		}
 		HitsCount hitsCount = new HitsCount();		
 		hitsCount.setHiArticleid(articleModel.getArticleId());
 		hitsCount.setHiCount(articleModel.getHiCount());
 		return hitsCount;
 	}

	

	

	

	

	
	

	
	

	













	
	
}
