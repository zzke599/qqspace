package com.qqspace.service.answering;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.Answering;
import com.qqspace.dao.AnsweringMapper;
import com.qqspace.service.model.AnsweringModel;

/**
* @author zzke
* @ClassName
* @Description 留言回复的业务接口实现类
*/
@Component("AnsweringService")
public class AnsweringServiceImpl implements AnsweringService {
	
	@Resource
	private AnsweringMapper answeringMapper;
	
	@Override
	@Transactional // 开启事务管理
	public AnsweringModel addMessageAnswering(AnsweringModel answeringModel) {
		//数据效验
		if(answeringModel == null){
			return null;
		}
		//数据转换  answering =》  answeringModel
		Answering answering = this.convertMessageModelFromMessage(answeringModel);
		//创建留言回复时间
		answering.setAnDate(DateTime.now().toDate());
		//执行数据库操作，是否成功！
		int result = answeringMapper.insertSelective(answering);
		if(result == 0){
			return null;
		}
		//查询留言回复信息
		Answering _answering = answeringMapper.selectByAnId(answering.getAnId());
		//数据转换 _answering =》  _answeringModel
		AnsweringModel _answeringModel = this.convertAnsweringFromAnsweringModel(_answering);
		
		return _answeringModel;
	}
	
	@Override
	public List<AnsweringModel> selectByAnMessageid(Integer anMessageid) {
		//数据效验
		if(anMessageid == null){
			return null;
		}
		//执行数据操作
		List<Answering> answerings = answeringMapper.selectByAnMessageId(anMessageid);
		
		List<AnsweringModel> answeringModels = answerings.stream().map(answering ->{
			
				AnsweringModel answeringModel = this.convertAnsweringFromAnsweringModel(answering);
			
				return answeringModel;
		}).collect(Collectors.toList());
		
		return answeringModels;
	}
	
	
	@Override
	@Transactional //开启事务管理
	public boolean deleteByAnMessageid(Integer anMessageid) {
		//数据效验
		if(anMessageid == null){
			return false;
		}
		//执行数据库操作
		int result = answeringMapper.deleteByAnMessageid(anMessageid);
		return result > 0 ? true : false;
	}
	
	@Override
	@Transactional //开启事务管理
	public boolean delAnsweredByAnId(Integer anId) {
		//数据效验
		if(anId == null){
			return false;
		}
		//执行数据库操作
		int result = answeringMapper.deleteByPrimaryKey(anId);
		
		return result > 0 ? true : false;
	}
	
	
	// ============================================================
	// ================= ===== 工具方法================================
	//将 Answering 转化到 AnsweringModel
    private AnsweringModel convertAnsweringFromAnsweringModel(Answering answering){
    	if (answering == null){
            return null;
        }
    	AnsweringModel  answeringModel= new AnsweringModel();

        BeanUtils.copyProperties(answering,answeringModel);
        
        return answeringModel;
    }
	
	//将 AnsweringModel 转化为 Answering
    private Answering convertMessageModelFromMessage(AnsweringModel answeringModel){
        if (answeringModel == null){
            return null;
        }
        Answering answering = new Answering();
        BeanUtils.copyProperties(answeringModel,answering);
        return answering;
    }

}
