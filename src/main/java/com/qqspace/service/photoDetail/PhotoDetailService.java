package com.qqspace.service.photoDetail;

import java.util.List;

import com.qqspace.service.model.PhotoDetailModel;

/**
* @author zzke
* @ClassName
* @Description 相册图片业务接口
*/
public interface PhotoDetailService {
	/**
	 * 添加相册图片
	 * @param photoDetailModel
	 * @return boolean
	 */
	boolean addphotoDetailInfo(PhotoDetailModel photoDetailModel);
	/**
	 * 根据相册id 删除图片
	 * @param pdPaid
	 * @return boolean
	 */
	boolean delphotoDetailsByPdPaid(Integer pdPaid);
	/**
	 * 根据相册id 查询图片
	 * @param pdPaid
	 * @return List<PhotoDetailModel>
	 */
	List<PhotoDetailModel> selectByPdPaid(Integer pdPaid);
	/**
	 * 根据图片id 查询图片信息
	 * @param pdId
	 * @return PhotoDetailModel
	 */
	PhotoDetailModel selectByPdId(Integer pdId);
	/**
	 * 	根据 图片id，修改图片的描述信息
	 * @param photoDetailModel
	 * @return boolean
	 */
	boolean updatePdDescriptionBypdId(PhotoDetailModel photoDetailModel);
	/**
	 * 根据 图片id，删除图片
	 * @param pdId
	 * @return boolean
	 */
	boolean delPhotoByPdId(Integer pdId);
	/**
	 * 根据 图片id，批量删除图片
	 * @param pdIds 数组
	 * @return boolean
	 */
	boolean delPhotoByPdIds(Integer[] pdIds);
}
