package com.lenovo.bootstrap.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.exception.StorageException;
import com.lenovo.bootstrap.mapper.PictureMapper;
import com.lenovo.bootstrap.po.Picture;
import com.lenovo.bootstrap.po.PictureExample;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.property.StorageProperties;
import com.lenovo.bootstrap.service.PictureService;
import com.lenovo.bootstrap.util.CamelCaseUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 2018年5月14日 下午2:38:39
 */

@Service
public class PictureServiceImpl implements PictureService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PictureServiceImpl.class);

	@Autowired
	PictureMapper pictureMapper;

	private final Path rootLocation;

	@Autowired
	public PictureServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);

	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageException("Could not read file: " + filename, e);
		}

	}

	@Override
	@Transactional
	public void store(MultipartFile file) {
		this.init();
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				LOGGER.info("------------------保存图片的路径-------"+ this.rootLocation.resolve(filename));
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

	}

	@Override
	@Transactional
	public void save(Picture picture) {
		picture.setCreateAt(new Date());
		picture.setUpdateAt(new Date());
		picture.setFixedPicUrl(picture.getOriginPicUrl());
		this.pictureMapper.insert(picture);

	}

	@Override
	public PageInfo<Picture> getAllList(ListVaild listVaild) {
		PictureExample example = new PictureExample();
		LOGGER.info("从数据库查询 adminAllList ");

		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort() + " " + listVaild.getOrder()));
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
		List<Picture> list = this.pictureMapper.selectByExample(example);

		return new PageInfo<>(list);

	}

	@Override
	public int deleteFromDB(Integer id) {
		return this.pictureMapper.deleteByPrimaryKey(id);

	}

	@Override
	public Picture findById(Integer id) {
		return this.pictureMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean deleteFromFile(String originPicUrl) throws Exception {

		return Files.deleteIfExists(this.rootLocation.resolve(originPicUrl));

	}

	private void init() {
		try {
			Files.createDirectories(this.rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}

	}

	@Override
	public int modify(Integer id, String picName) {
		Picture picture = new Picture();
		picture.setId(id);
		picture.setOriginPicUrl(picName);
		picture.setFixedPicUrl(picName);
		picture.setUpdateAt(new Date());
		return this.pictureMapper.updateByPrimaryKeySelective(picture);

	}

}
