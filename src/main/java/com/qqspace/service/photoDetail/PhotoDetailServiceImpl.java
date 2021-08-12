package com.qqspace.service.photoDetail;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qqspace.bean.PhotoDetail;
import com.qqspace.dao.PhotoDetailMapper;
import com.qqspace.service.model.PhotoAlbumsModel;
import com.qqspace.service.model.PhotoDetailModel;
import com.qqspace.service.photoAlbums.PhotoAlbumsService;
import com.qqspace.tool.OperationFileUtil;
import com.qqspace.tool.PathUtil;


/**
* @author zzke
* @ClassName
* @Description 相册图片业务接口时间类型
*/
@Component("PhotoDetailService")
public class PhotoDetailServiceImpl implements PhotoDetailService {
	
	@Resource
	private PhotoDetailMapper photoDetailMapper;
	@Autowired
	private PhotoAlbumsService photoAlbumsService;
	
	@Override
	@Transactional //开启事务管理
	public boolean addphotoDetailInfo(PhotoDetailModel photoDetailModel) {
		//数据转换 photoDetailModel  =》 photoDetail
		PhotoDetail photoDetail = this.convertPhotoDetailFromPhotoDetailModel(photoDetailModel);
		//上传时间为当前时间
		photoDetail.setPdUploaddate(DateTime.now().toDate());
		//执行数据库操作
		int result = photoDetailMapper.insertSelective(photoDetail);
		
		return result > 0 ? true : false;
	}
	
	@Override
	@Transactional	//开启事务管理
	public boolean delphotoDetailsByPdPaid(Integer pdPaid) {
		//先查询相册图片路径删除本地图片，再删除数据库中的相册图片信息
		//执行数据库操作
		List<PhotoDetail> photoDetails = photoDetailMapper.selectByPdPaid(pdPaid);
		//photoDetails 不为空执行删除本地图片
		//执行数据库操作
		if(photoDetails.size() != 0){
			//遍历图片信息，删除
		for (PhotoDetail photoDetail : photoDetails) {
			//原来的图片信息URL
			String	filePath = photoDetail.getPdImgurl();		
			//替换/article 为绝对路径
			filePath = filePath.replaceFirst("\\/album", PathUtil.getAlbumPath());		
			//删除本地图片
			OperationFileUtil.deleteFile(filePath);
		}	
		//执行数据库操作
		int result = photoDetailMapper.deleteByPdPaid(pdPaid);
		return result > 0 ? true : false;
		}
			
			//没图片 返回 true
			return true;
	
	}
	
	@Override
	public List<PhotoDetailModel> selectByPdPaid(Integer pdPaid) {
		if(pdPaid == null){
			return null;
		}
		List<PhotoDetail> photoDetails = photoDetailMapper.selectByPdPaid(pdPaid);
		
		List<PhotoDetailModel> photoDetailModels =photoDetails.stream().map(photoDetail -> {
			//数据转换  photoDetail  =》photoDetailModel
			PhotoDetailModel  photoDetailModel = this.convertPhotoDetailModelFromPhotoDetail(photoDetail);
			
			return photoDetailModel;
		}).collect(Collectors.toList());
		
		return photoDetailModels;
	}
	
	@Override
	public PhotoDetailModel selectByPdId(Integer pdId) {
		//数据效验
		if(pdId == null){
			return null;
		}
		//执行数据库操作
		PhotoDetail photoDetail = photoDetailMapper.selectByPrimaryKey(pdId);
		//数据转换  photoDetail =》  photoDetailModel
		PhotoDetailModel photoDetailModel = this.convertPhotoDetailModelFromPhotoDetail(photoDetail);
		
		return photoDetailModel;
	}
	
	
	@Override
	@Transactional // 开启事务处理
	public boolean updatePdDescriptionBypdId(PhotoDetailModel photoDetailModel) {
		//数据转换  photoDetailModel =》 photoDetail
		PhotoDetail photoDetail =this.convertPhotoDetailFromPhotoDetailModel(photoDetailModel);
		System.out.println("================>> 描述为"+photoDetail.getPdDescription());
		//执行数据库操作
		int result = photoDetailMapper.updatePdDescriptionBypdId(photoDetail);
		
		return  result > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean delPhotoByPdId(Integer pdId) {
		//数据效验
		if(pdId == null){
			return false;
		}
		//查询它的图URL
		PhotoDetail photoDetail = photoDetailMapper.selectByPrimaryKey(pdId);
		if(photoDetail == null){
			return false;
		}
		//查询下相册封面图片
		PhotoAlbumsModel photoAlbumsModel = photoAlbumsService.selectByPaId(photoDetail.getPdPaid());
		
		//不为空且封面图片和删除的图片一致
		if(photoAlbumsModel != null && photoAlbumsModel.getPaCoverimg().equals(photoDetail.getPdImgurl())){
			//封面图片设置为空
			String paCoverimg ="";
			photoAlbumsService.updateCoverPhotobyNull(photoAlbumsModel.getPaId(), paCoverimg);
		}
		//原来的图片信息URL
		String	filePath = photoDetail.getPdImgurl();		
		//替换/article 为绝对路径
		filePath = filePath.replaceFirst("\\/album", PathUtil.getAlbumPath());		
		//删除本地图片
		OperationFileUtil.deleteFile(filePath);
		//执行数据库操作
		int result = photoDetailMapper.deleteByPrimaryKey(pdId);
		
		return result > 0 ? true : false;
	}
	//批量删除图片
	@Override
	@Transactional //开启事务管理
	public boolean delPhotoByPdIds(Integer[] pdIds) {
		if(pdIds.length == 0){
			return false;
		}
		for (Integer pdId : pdIds) {
			//查询它的图URL
			PhotoDetail photoDetail = photoDetailMapper.selectByPrimaryKey(pdId);
			if(photoDetail == null){
				return false;
			}
			//查询下相册封面图片
			PhotoAlbumsModel photoAlbumsModel = photoAlbumsService.selectByPaId(photoDetail.getPdPaid());
			
			//不为空且封面图片和删除的图片一致
			if(photoAlbumsModel != null && photoAlbumsModel.getPaCoverimg().equals(photoDetail.getPdImgurl())){
				//封面图片设置为空
				String paCoverimg ="";
				photoAlbumsService.updateCoverPhotobyNull(photoAlbumsModel.getPaId(), paCoverimg);
			}
			//原来的图片信息URL
			String	filePath = photoDetail.getPdImgurl();		
			//替换/article 为绝对路径
			filePath = filePath.replaceFirst("\\/album", PathUtil.getAlbumPath());		
			//删除本地图片
			OperationFileUtil.deleteFile(filePath);
			//执行数据库操作
			int result = photoDetailMapper.deleteByPrimaryKey(pdId);
			if(result == 0){
				return false;
			}
		}
		
		return true;
	}
	// ============================================================
	// ================= ===== 工具方法================================
		
		//将 photoDetail 转化到 PhotoDetailModel
	    private PhotoDetailModel convertPhotoDetailModelFromPhotoDetail(PhotoDetail photoDetail){
	    	if (photoDetail == null){
	            return null;
	        }
	    	PhotoDetailModel photoDetailModel = new PhotoDetailModel();

	        BeanUtils.copyProperties(photoDetail,photoDetailModel);
	        
	        return photoDetailModel;
	    }
		
		//将 PhotoDetailModel 转化为 PhotoDetail
	    private PhotoDetail convertPhotoDetailFromPhotoDetailModel(PhotoDetailModel photoDetailModel){
	        if (photoDetailModel == null){
	            return null;
	        }
	        PhotoDetail  photoDetail = new PhotoDetail();
	        BeanUtils.copyProperties(photoDetailModel,photoDetail);
	        return photoDetail;
	    }

	

	

		
		

		











		

		

}
