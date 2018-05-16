package com.lenovo.bootstrap.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.mapper.PictureMapper;
import com.lenovo.bootstrap.po.Admin;
import com.lenovo.bootstrap.po.Picture;
import com.lenovo.bootstrap.po.valid.ListVaild;

/**
 * Description:图片处理业务
 * 
 * @author WJoe
 * @time 2018年5月14日 下午2:17:31
 */

public interface PictureService {

	/**
	 * 保存到文件夹
	 * 
	 * @param file
	 */

	public void store(MultipartFile file);

	public Path load(String filename);

	public Resource loadAsResource(String filename);

	/**
	 * 保存到数据库
	 * 
	 * @param picture
	 */
	public void save(Picture picture);

	public PageInfo<Picture> getAllList(ListVaild listVaild);

	/**
	 * 从数据库中删除图片信息
	 * 
	 * @param id
	 * @return
	 */
	public int deleteFromDB(Integer id);

	/**
	 * 查询图片信息
	 * 
	 * @param id
	 * @return
	 */
	public Picture findById(Integer id);

	/**
	 * 从文件中删除图片
	 * 
	 * @param originPicUrl
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteFromFile(String originPicUrl) throws Exception;

	/**
	 * 修改图片
	 * 
	 * @param id
	 * @param picName
	 * @return
	 */
	public int modify(Integer id, String picUrl);

}
