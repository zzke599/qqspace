package com.qqspace.service.message;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.Message;
import com.qqspace.dao.MessageMapper;
import com.qqspace.service.answering.AnsweringService;
import com.qqspace.service.model.AnsweringModel;
import com.qqspace.service.model.MessageModel;
import com.qqspace.service.model.UserModel;
import com.qqspace.service.user.UserService;
import com.qqspace.tool.PageSupport;

/**
* @author zzke
* @ClassName
* @Description 留言的业务接口实现类
*/
@Component("MessageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageMapper messageMapper;
	@Autowired
	private AnsweringService answeringService;
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional	//开启事务管理
	public MessageModel addMessage(MessageModel messageModel) {
		//数据效验
		if(messageModel == null){
			return null;
		}
		Message message = this.convertMessageModelFromMessage(messageModel);
		//创建留言时间
		message.setMeDate(DateTime.now().toDate());
		//执行数据库语句
		int result = messageMapper.insertSelective(message);
		//查询插入的留言信息
		//判断是否插入成功!
		if(result == 0){
			return null;
		}
		Message _message = messageMapper.selectByMeId(message.getMeId());
		//数据转换  _message =》 messageModel
		MessageModel  _messageModel = this.convertMessageFromMessageModel(_message);
		//返回数据模型
		return _messageModel;
	}
	
	@Override
	public PageSupport<MessageModel> selectByMeUserid(Integer meUserid,Integer page) {
		if(meUserid == null || page == null){
			return null ;
		}
		PageSupport<MessageModel> pageSupport = new PageSupport<MessageModel>();
			    //每页容量
				pageSupport.setPageSize(6);
				//查询总条数
				int count = messageMapper.selectCountByUserId(meUserid);		
				//总条数
				pageSupport.setTotalCount(count);
				//当前页
				pageSupport.setCurrentPageNo(page);
				//判断当前页是否大于总页数，是否小于1。
				if(page < 1){
					//当前页数=1
					pageSupport.setCurrentPageNo(1);
				}
				if(page > pageSupport.getTotalPageCount()){
					//当前页 = 总页数
					pageSupport.setCurrentPageNo(pageSupport.getTotalPageCount());
				}
				
				//从哪开始
				int begin=(pageSupport.getCurrentPageNo()-1)*pageSupport.getPageSize();
				//查多少条记录
				int recordSize = pageSupport.getPageSize();
				//执行数据库操作
				//每页显示的数据集合 
				List<Message> messages = messageMapper.selectByMeUserid(meUserid,begin,recordSize);
				
				
				//messages的集合数据传给 messageModels的集合	
				List<MessageModel> messageModels =messages.stream().map(message -> {
					//数据转换  message  =》messageModel
					MessageModel  messageModel = this.convertMessageFromMessageModel(message);
					//查询留言者用户信息 
					UserModel userModel = userService.selectByUserId(message.getMeLeaveid());
					messageModel.setUserModel(userModel);
					//查询底下的留言回复信息
					List<AnsweringModel> answeringModels = answeringService.selectByAnMessageid(messageModel.getMeId());
					messageModel.setAnsweringModels(answeringModels);
					//返回数据
					return messageModel;
				}).collect(Collectors.toList());
				//把数据放入PageSupport.list
				pageSupport.setList(messageModels);

				return pageSupport;
	}
	
	
	@Override
	@Transactional //开启事务管理
	public boolean delMessageByMeIdAndMeUserid(Integer meId, Integer meUserid) {
		//数据效验
		if(meId == null || meUserid == null){
			return false;
		}
		//表示删除留言返回的消息，默认为true，因为如果没有留言会去执行。
		boolean answeringResult  =true;
		//先查询下底下是否有留言回复,数组长度不等于0，表示以下由留言回复信息，需删除，反之
		if(answeringService.selectByAnMessageid(meId).size() !=0){
			answeringResult = answeringService.deleteByAnMessageid(meId);
		}
		//判断留言是否为空还是是否删除成功，没有或删除成功返回true，反之
		if(!answeringResult){
			return false;
		}
		//执行数据库操作
		int messageResultCount = messageMapper.deleteByPrimaryKey(meId);
		
		
		return messageResultCount > 0 ? true : false;
	}

	
	
	
	
	
	
	
	
	
		// ============================================================
		// ================= ===== 工具方法================================
		
		//将 Message 转化到 MessageModel
	    private MessageModel convertMessageFromMessageModel(Message message){
	    	if (message == null){
	            return null;
	        }
	    	MessageModel messageModel = new MessageModel();

	        BeanUtils.copyProperties(message,messageModel);
	        
	        return messageModel;
	    }
		
		//将 MessageModel 转化为 Message
	    private Message convertMessageModelFromMessage(MessageModel messageModel){
	        if (messageModel == null){
	            return null;
	        }
	        Message message = new Message();
	        BeanUtils.copyProperties(messageModel,message);
	        return message;
	    }

		













		

	
}
