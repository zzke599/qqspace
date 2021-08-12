package com.qqspace.service.label;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.Label;
import com.qqspace.dao.LabelMapper;
import com.qqspace.service.article.ArticleService;
import com.qqspace.service.model.ArticleModel;
import com.qqspace.service.model.LabelModel;

/**
* @author zzke
* @ClassName
* @Description 标签信息业务接口实现类
*/
@Component("LableService")
public class LabelServiceImpl implements LabelService {

	@Resource
	private LabelMapper labelMapper;	
	@Autowired
	private ArticleService articleService;
	
	@Override
	@Transactional	//开启事务处理
	public LabelModel addLable(LabelModel labelModel) {
		
		//创建时间
		labelModel.setLabelCreatedate(new Date());
		//数据模型转换 labelModel -> label
		Label label = convertLabelFromLabelModel(labelModel);
		//执行数据操作
		int result = labelMapper.insertSelective(label);
		//判断返回执行记录数是否大于0条
		if(result <= 0){
			return null;
		}
		//把labelid加入labelModel模型
		labelModel.setLabelId(label.getLabelId());
		//返回模型数据
		return labelModel;
	}

	@Override
	public List<LabelModel> getLablesByLabelUserId(Integer labelUserid) {
		//查询数据库
		List<Label> labels = labelMapper.selectLabelsByUserId(labelUserid);
		
		//labels的集合数据传给 labelModels的集合
		List<LabelModel> labelModels = labels.stream().map(label -> {
			LabelModel labelModel = this.convertLabelModelFromLabel(label);
			return labelModel;
		}).collect(Collectors.toList());
		//返回集合
		return labelModels;
	}

	@Override
	@Transactional //开启事务处理
	public boolean updateLableByLableId(LabelModel labelModel) {
		//判空处理
		if (labelModel == null) {
			
			return false;
		}
		//数据转换 labelModel —》Label
		Label label = convertLabelFromLabelModel(labelModel);
		//执行数据库操作
		int result = labelMapper.updateByLidAndUserid(label);
		//判断返回的执行记录数是否大于0，是：true,反之
		return result > 0 ? true : false;
	}
	
	@Override
	@Transactional //开启事务处理
	public boolean delLableByLableId(Integer labelId,Integer labelUserid) {
		//判空
		if (labelId == null || labelUserid == null ) {
			
			return false;
		}
		//执行数据库操作
		int result = labelMapper.deleteByLidAndUserid(labelId,labelUserid);
		//判断返回的执行记录数是否大于0，是：true,反之
		return result > 0 ? true : false;
	}

	@Override
	public LabelModel getLabelandArticlesByLabelId(Integer labelId) {
		if(labelId == null ){			
			return null;
		}
		//查询标签信息
		Label label = labelMapper.selectByPrimaryKey(labelId);
		LabelModel labelModel=convertLabelModelFromLabel(label);
		//查询文章信息
		List<ArticleModel> articleModels = articleService.selectArticleModelByLid(labelId);
		labelModel.setArticleModelList(articleModels);
		return labelModel;
	}
	
	
	
	
	
	// ============================================================
	// ================= ===== 工具方法================================
	
	//将 label 转化到 LabelModel
    private LabelModel convertLabelModelFromLabel(Label label){
    	if (label == null){
            return null;
        }
    	LabelModel labelModel = new LabelModel();

        BeanUtils.copyProperties(label,labelModel);
        
        return labelModel;
    }
	
	//将 LabelModel 转化为 Label
    private Label convertLabelFromLabelModel(LabelModel labelModel){
        if (labelModel == null){
            return null;
        }
        Label  label = new Label();
        BeanUtils.copyProperties(labelModel,label);
        return label;
    }

	

	
}
