package com.qqspace.service.article;



import java.util.List;

import com.qqspace.service.model.ArticleModel;
import com.qqspace.tool.LayUIPageUtil;
import com.qqspace.tool.PageSupport;
import com.qqspace.vo.EChartsVO;


/**
* @author zzke
* @ClassName
* @Description 	文章信息业务接口
*/

public interface ArticleService {
	/**
	 * 新增文章
	 * @param articleModel
	 * @return
	 */
	boolean addArticleInfo(ArticleModel articleModel);
	/**
	 * 根据标签id查询所有文章
	 * @param articleLabelid
	 * @return List<ArticleModel>
	 */
	List<ArticleModel> selectArticleModelByLid(Integer articleLabelid);
	/**
	 * 根据用户id和文章id查询文章信息
	 * @param  articleId,articleUserid
	 * @return ArticleModel
	 */
	ArticleModel selectArticleByAidAndUid(Integer articleId,Integer articleUserid);
	/**
	 * 根据文章id更新文章信息
	 * @param articleModel
	 * @return boolean
	 */
	boolean updateArticleInfo(ArticleModel articleModel);
	/**
	 *  根据 articleId 删除文章
	 * @param articleId
	 * @return boolean
	 */
	boolean delArticleById(Integer articleId);
	/**
	 * 根据文章id,用户的Id，查询用户文章信息
	 * @param articleId，articleUserid
	 * @return ArticleModel
	 */
	ArticleModel selectArticlePerByAid(Integer articleId,Integer articleUserid);
	/**
	 * 查询用户自己的文章和它关注人的文章，公开的文章
	 * @param page 当前页
	 * @param userid 登录用户id
	 * @return PageSupport<ArticleModel>
	 */
	PageSupport<ArticleModel> selectByJurOrUserOrAtt(Integer page, Integer userid);
	/**
	 * 自增点击量
	 * @param articleId
	 * @return boolean
	 */
	boolean updateHistCount(Integer articleId);
	/**
	 * 根据用户id 查询用户的所有文章信息，带分页
	 * @param userid
	 * @param labelId 
	 * @param page 
	 * @return PageSupport<ArticleModel>
	 */
	PageSupport<ArticleModel> selectAllByUserId(Integer userid,Integer labelId, Integer page);
	/**
	 * 查询访问查看文章的权限
	 * @param articleId
	 * @param userid
	 * @return boolean
	 */
	boolean selectgetAccessRight(Integer articleId, Integer paUserid, Integer userid);
	/**
	 * 访问查看文章
	 * @param articleId
	 * @param paUserid
	 * @param userid
	 * @return ArticleModel
	 */
	ArticleModel selectAboutByUserIdAndPaUserid(Integer articleId, Integer paUserid, Integer userid);
	/**
	 * 根据用户id 查询该用户的文章和权限为公开的文章和它的博友文章的访问量最高的12条文章信息
	 * @param articleUserid
	 * @return List<ArticleModel> 
	 */
	List<ArticleModel> selectArticleTOPTwelveByUserId(Integer articleUserid);

	/**
	 * 查询所有文章数
	 * @return
	 */
    Integer selectArticlesCountByAccessright(Integer articleAccessright);

	/**
	 *
	 * @param accessRight
	 * @return
	 */
	List<EChartsVO> selectMonthArticlesByAccessRight(Integer accessRight);

    LayUIPageUtil<ArticleModel> selectAllArticlesWithPageByCondition(Integer page, Integer limit, Integer userId, String articleTitle, String articleAuthor, Integer accessRight);
}
