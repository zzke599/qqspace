package com.qqspace.service.photoAlbums;

import java.util.List;

import com.qqspace.service.model.PhotoAlbumsModel;
import com.qqspace.tool.PageSupport;


public interface PhotoAlbumsService {
	/**
	 * 添加相册
	 * @param photoAlbumsModel 相册模型
	 * @return boolean
	 */
	boolean addPhotoAlbums(PhotoAlbumsModel photoAlbumsModel);
	/**
	 * 根据相册id 和登录用户id 查询用户相册信息
	 * @param paId 相册id
	 * @param paUserid 用户id
	 * @return PhotoAlbumsModel
	 */
	PhotoAlbumsModel selectPicByPaidAndUseid(Integer paId, Integer paUserid);
	/**
	 * 根据相册id 修改相册信息
	 * @param photoAlbumsModel 相册模型
	 * @return  boolean
	 */
	boolean updatePhotoAlbumsByPaId(PhotoAlbumsModel photoAlbumsModel);
	/**
	 * 根据用户id 查询用户的相册信息，带分页
	 * @param paUserid 用户id
	 * @return PageSupport<PhotoAlbumsModel>
	 */
	PageSupport<PhotoAlbumsModel> selectByUseid(Integer paUserid,Integer page);
	/**
	 * 根据用户id和相册id 删除用的相册信息以及所属的图片信息
	 * @param paId 相册id
	 * @param paUserid 用户id
	 * @return boolean
	 */
	boolean delphotpAlbumsAndImg(Integer paId, Integer paUserid);
	/**
	 * 根据用户id 查询用户的相册信息，不带分页
	 * @param paUserid 用户id
	 * @return List<PhotoAlbumsModel>
	 */
	List<PhotoAlbumsModel> selectAlbumsByUseid(Integer paUserid);
	/**
	 *根据相册id 和所属用户id 和 登录用户id 查询访问权限
	 * @param paId 相册id
	 * @param paUserid 所属用户id
	 * @param userid 登录用户id
	 * @return boolean
	 */
	boolean selectPicAccessRight(Integer paId, Integer paUserid, Integer userid);
	/**
	 * 根据相册id 和所属用户id 和 登录用户id 查询用户相册详情信息
	 * @param paId  相册id
	 * @param paUserid 所属用户id
	 * @param userid 登录用户id
	 * @return PhotoAlbumsModel 业务层相册模型
	 */
	PhotoAlbumsModel selectAlbumsByPaIdAndPaUserid(Integer paId, Integer paUserid, Integer userid);
	/**
	 * 更新相册的封面图片
	 * @param photoAlbumsModel 相册模型
	 * @return boolean
	 */
	boolean updateBypaIdAnduserid(PhotoAlbumsModel photoAlbumsModel, Integer pdId);
	/**
	 * 根据相册id 查询相册信息
	 * @param paId 相册id
	 * @return PhotoAlbumsModel
	 */
	PhotoAlbumsModel selectByPaId(Integer paId);
	/**
	 *根据相册id， 更新相册的封面图片
	 * @param paId 相册id
	 * @param paCoverimg 相册封面
	 */
	void updateCoverPhotobyNull(Integer paId, String paCoverimg);


}
