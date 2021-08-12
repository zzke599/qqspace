package com.qqspace.service.label;

import java.util.List;
import com.qqspace.service.model.LabelModel;

/**
* @author zzke
* @ClassName
* @Description 	标签信息业务接口
*/
public interface LabelService {
	
	/**
	 * 	新增标签
	 * @param lableModel
	 * @return boolean
	 */
	LabelModel addLable(LabelModel labelModel);
	
	/**
	 * 根据用户id查询标签，该用户的标签
	 * @param lableUserId
	 * @return List<LableModel>
	 */
	 List<LabelModel> getLablesByLabelUserId(Integer labelUserid);
	 /**
	  * 更新标签细信息
	  * @param lableModel
	  * @return
	  */
	 boolean updateLableByLableId(LabelModel labelModel);
	 /**
	  * 删除标签，判断标签下是否存在文章，存在则不能删除，反之
	  * @param lableId,labelUserid
	  * @return
	  */
	 boolean delLableByLableId(Integer labelId,Integer labelUserid);
	 /**
	  * 根据标签id 查询该标签下的信息和所有文章信息
	  * @return
	  */
	 LabelModel getLabelandArticlesByLabelId(Integer labelId);
}
