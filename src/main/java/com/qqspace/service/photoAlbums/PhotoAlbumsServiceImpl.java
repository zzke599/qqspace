package com.qqspace.service.photoAlbums;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.PhotoAlbums;
import com.qqspace.dao.PhotoAlbumsMapper;
import com.qqspace.service.follow.FollowService;
import com.qqspace.service.model.FollowModel;
import com.qqspace.service.model.PhotoAlbumsModel;
import com.qqspace.service.model.PhotoDetailModel;
import com.qqspace.service.photoDetail.PhotoDetailService;
import com.qqspace.tool.PageSupport;

/**
* @author zzke
* @ClassName
* @Description 相册业务接口实现类
*/
@Component("PhotoAlbumsServiceI")
public class PhotoAlbumsServiceImpl implements PhotoAlbumsService{
	@Resource
	private PhotoAlbumsMapper photoAlbumsMapper;
	@Autowired
	private PhotoDetailService photoDetailService;
	@Autowired
	private FollowService followService;
	
	
	@Override
	@Transactional	//开启事务管理
	public boolean addPhotoAlbums(PhotoAlbumsModel photoAlbumsModel) {
		//数据转换 photoAlbumsModel =》photoAlbums
		PhotoAlbums photoAlbums = this.convertPhotoAlbumsFromPhotoAlbumsModel(photoAlbumsModel);
		//设置添加时间为当前系统时间
		photoAlbums.setPaCreatedate(DateTime.now().toDate());
		//执行数据库操作
		int result = photoAlbumsMapper.insertSelective(photoAlbums);
		
		return result >0 ? true : false;
	}
	
	@Override
	public PhotoAlbumsModel selectPicByPaidAndUseid(Integer paId, Integer paUserid) {
		//判空处理
		if(paId == null && paUserid == null){
			return null;
		}
		//执行数据库操作
		PhotoAlbums photoAlbums = photoAlbumsMapper.selectByPaidAndUseid(paId,paUserid);
		//数据转换 photoAlbums  =》  photoAlbumsModel
		PhotoAlbumsModel photoAlbumsModel =this.convertPhotoAlbumsModelFromPhotoAlbums(photoAlbums); 
		//返回模型数据
		return photoAlbumsModel;
	}
	
	@Override
	@Transactional //开启事务管理
	public boolean updatePhotoAlbumsByPaId(PhotoAlbumsModel photoAlbumsModel) {
			
			//数据转换  photoAlbumsModel =》photoAlbums
			PhotoAlbums photoAlbums = this.convertPhotoAlbumsFromPhotoAlbumsModel(photoAlbumsModel);
			//查询下文章信息
			PhotoAlbums  photoAlbumsBefore = photoAlbumsMapper.selectByPrimaryKey(photoAlbums.getPaId());
			//判断文章是否修改了信息
			if(photoAlbumsBefore.getPaName().equals(photoAlbums.getPaName())&& 
				photoAlbumsBefore.getPaDescription().equals(photoAlbums.getPaDescription()) && 
				photoAlbumsBefore.getPaJurisdiction().equals(photoAlbums.getPaJurisdiction()) 
					){
				//数据没有更改，也为true
				return true;
			}
			//执行修改操作
			int result = photoAlbumsMapper.updateByPrimaryKeySelective(photoAlbums);
			
			return result > 0 ? true : false;
	}

	@Override
	public PageSupport<PhotoAlbumsModel> selectByUseid(Integer paUserid,Integer page) {
		if(paUserid == null){
			return null;
		}
		PageSupport<PhotoAlbumsModel> pageSupport = new PageSupport<PhotoAlbumsModel>();
		//每页容量
		pageSupport.setPageSize(12);
		//查询总条数
		int count = photoAlbumsMapper.selectCountByPaUserid(paUserid);		
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
		//执行数据库操作
		//每页显示的数据集合 
		List<PhotoAlbums> photoAlbumsList = photoAlbumsMapper.selectByPaUserid(paUserid,begin,recordSize);
		
		//photoAlbumsList的集合数据传给 photoAlbumsModels的集合	
		List<PhotoAlbumsModel> photoAlbumsModels =photoAlbumsList.stream().map(photoAlbums -> {
			//数据转换  photoAlbums  =》photoAlbumsModel
			PhotoAlbumsModel  photoAlbumsModel = this.convertPhotoAlbumsModelFromPhotoAlbums(photoAlbums);
			//查询附属图片信息
			photoAlbumsModel.setPhotoDetailModels(photoDetailService.selectByPdPaid(photoAlbumsModel.getPaId()));
			//附属的图片总数
			photoAlbumsModel.setPhotoNums(photoAlbumsModel.getPhotoDetailModels().size());
			//放回数据
			return photoAlbumsModel;
		}).collect(Collectors.toList());
		//把数据放入PageSupport.list
		pageSupport.setList(photoAlbumsModels);
		//返回分页模型
		return pageSupport;
	}
	
	
	@Override
	@Transactional	//开启事务管理
	public boolean delphotpAlbumsAndImg(Integer paId, Integer paUserid) {
		//数据效验
		if(paId == null || paUserid == null){
			return false;
		}
		//查询下是否相册属于该用户
		PhotoAlbumsModel photoAlbumsModel = this.selectPicByPaidAndUseid(paId, paUserid);
		if(photoAlbumsModel == null){
			return false;
		}
		//执行删除相册的附属图片
		if(!photoDetailService.delphotoDetailsByPdPaid(paId)){
			return false;
		}
		//执行数据库操作
		int result = photoAlbumsMapper.deleteByPrimaryKey(paId);
		return result >0  ? true : false;
	}
	
	
	@Override
	public List<PhotoAlbumsModel> selectAlbumsByUseid(Integer paUserid) {
		if(paUserid == null){
			return null;
		}
		List<PhotoAlbums> photoAlbumsList = photoAlbumsMapper.selectAlbumsByPaUserid(paUserid);
		List<PhotoAlbumsModel> photoAlbumsModels =photoAlbumsList.stream().map(photoAlbums -> {
			//数据转换  photoAlbums  =》photoAlbumsModel
			PhotoAlbumsModel  photoAlbumsModel = this.convertPhotoAlbumsModelFromPhotoAlbums(photoAlbums);		
			//返回数据
			return photoAlbumsModel;
		}).collect(Collectors.toList());
		
		return photoAlbumsModels;
	}
	
	@Override
	public boolean selectPicAccessRight(Integer paId, Integer paUserid, Integer userid) {
		//数据效验
		if(paId == null || paUserid == null || userid == null){
			return false;
		}
		//执行数据操作
		PhotoAlbumsModel photoAlbumsModel = this.selectPicByPaidAndUseid(paId,paUserid);
		if(photoAlbumsModel == null){
			return false;
		}
		//公开，返回true
		if(photoAlbumsModel.getPaJurisdiction() == 1){
			return true;
		}else if(photoAlbumsModel.getPaJurisdiction() == 2){
			/*这里先返回true，根据关注信息查询*/
			if(!userid.equals(paUserid)){		//判断 不是为本人操作
				FollowModel followModel = followService.selectByUserIdAndBeUserId(userid, paUserid);
				return followModel ==null ? false : true;
			}
		}else {
			return paUserid.equals(userid);
		}
		return false;
	}
	
	@Override
	public PhotoAlbumsModel selectAlbumsByPaIdAndPaUserid(Integer paId, Integer paUserid, Integer userid) {
				//数据效验
				if(paId == null || paUserid == null || userid == null){
					return null;
				}
				//执行数据操作
				PhotoAlbumsModel photoAlbumsModel = this.selectPicByPaidAndUseid(paId,paUserid);
				//判空
				if(photoAlbumsModel == null){
					
					return null;
				}
				//查询图片
				photoAlbumsModel.setPhotoDetailModels(photoDetailService.selectByPdPaid(photoAlbumsModel.getPaId()));
				//公开
				if(photoAlbumsModel.getPaJurisdiction() == 1){
					 return photoAlbumsModel;
				}else if(photoAlbumsModel.getPaJurisdiction()  == 2) {
					//博友
					return photoAlbumsModel;
				}else {
					if(paUserid.equals(userid)) {
						return photoAlbumsModel;
					}
				}
		return null;
	}
	
	@Override
	@Transactional //开启事务处理
	public boolean updateBypaIdAnduserid(PhotoAlbumsModel photoAlbumsModel, Integer pdId) {
	
		//根据 图片id 获取图片信息 
		PhotoDetailModel photoDetailModel =  photoDetailService.selectByPdId(pdId);
		if (photoDetailModel == null) {
			
		
			return false;
		}
		//把图片URL 传入  photoAlbumsModel
		photoAlbumsModel.setPaCoverimg(photoDetailModel.getPdImgurl());
		//数据转换  photoAlbumsModel   =》  photoAlbums
		PhotoAlbums photoAlbums = this.convertPhotoAlbumsFromPhotoAlbumsModel(photoAlbumsModel);
		//执行数据库操作
		int result = photoAlbumsMapper.updateBypaIdAnduserid(photoAlbums);
		
		return result > 0 ? true : false;
	}
	
	@Override
	public PhotoAlbumsModel selectByPaId(Integer paId) {
		if(paId == null){
			return null;
		}
		//执行数据库操作
		PhotoAlbums photoAlbums = photoAlbumsMapper.selectByPrimaryKey(paId);
		//数据转换  photoAlbums =》 photoAlbumsModel
		PhotoAlbumsModel photoAlbumsModel =this.convertPhotoAlbumsModelFromPhotoAlbums(photoAlbums);
		return photoAlbumsModel;
	}
	
	@Override
	@Transactional  //开启事务管理
	public void updateCoverPhotobyNull(Integer paId, String paCoverimg) {
		if(paId != null){
			//执行数据库操作
			int result = photoAlbumsMapper.updateCoverPhotobyNull(paId,paCoverimg);	
		}
		
	}
	

	
	// ============================================================
	// ================= ===== 工具方法================================
		
		//将 photoAlbums 转化到 photoAlbumsModel
	    private PhotoAlbumsModel convertPhotoAlbumsModelFromPhotoAlbums(PhotoAlbums photoAlbums){
	    	if (photoAlbums == null){
	            return null;
	        }
	    	PhotoAlbumsModel photoAlbumsModel = new PhotoAlbumsModel();

	        BeanUtils.copyProperties(photoAlbums,photoAlbumsModel);
	        
	        return photoAlbumsModel;
	    }
		
		//将 photoAlbumsModel 转化为 photoAlbums
	    private PhotoAlbums convertPhotoAlbumsFromPhotoAlbumsModel(PhotoAlbumsModel photoAlbumsModel){
	        if (photoAlbumsModel == null){
	            return null;
	        }
	        PhotoAlbums  photoAlbums = new PhotoAlbums();
	        BeanUtils.copyProperties(photoAlbumsModel,photoAlbums);
	        return photoAlbums;
	    }

		

		

		

		

	
		

		

		

		














		

	

}
