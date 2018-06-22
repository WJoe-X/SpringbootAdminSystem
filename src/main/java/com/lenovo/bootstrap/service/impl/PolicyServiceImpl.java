package com.lenovo.bootstrap.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.bootstrap.exception.StorageException;
import com.lenovo.bootstrap.property.StorageProperties;
import com.lenovo.bootstrap.service.PolicyService;
import com.lenovo.bootstrap.vo.PolicyPropertyVo;

/**
 * Description:
 * 
 * @author WJoe
 * @time 2018年6月20日 上午10:19:37
 */
@Service
public class PolicyServiceImpl implements PolicyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyServiceImpl.class);
	private static final String FILE_PATH = "policy";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String JSON = "json";
 
	/*private final Path rootLocation;
	
	@Autowired
	public PolicyServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getpLocation());
	}*/
	@Override
	public List<PolicyPropertyVo> getPolicyProperty() throws Exception {
		File file = ResourceUtils.getFile(FILE_PATH);
		if (!file.exists()) {
			file.mkdir();
		}
		LOGGER.info(file.getAbsolutePath());
		if (!file.isDirectory()) { // 通过isDirectory()判断当前路径是不是文件夹
			LOGGER.info("--------------｛｝      这不是一个文件夹-------------", file.getName());
		} else if (file.isDirectory()) {
			// 获取json格式的文件列表
			File[] filelist = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(JSON);
				}
			});
			List<PolicyPropertyVo> policyPropertyVos = new ArrayList<>();
			
			for (int i=0; i<filelist.length;i++) {
				PolicyPropertyVo policyPropertyVo = new PolicyPropertyVo();
				policyPropertyVo.setName(filelist[i].getName().substring(0, filelist[i].getName().lastIndexOf(".")));
				policyPropertyVo.setUpdatedDate(sdf.format(filelist[i].lastModified()));
				policyPropertyVos.add(policyPropertyVo);
			}
			return policyPropertyVos;
		}
		return null;
	}

	@Override
	public Boolean savePolicyToJson(String json) throws Exception {
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			
			String path = FILE_PATH + "/" + jsonObject.getString("name") + "." + JSON;
			File file = ResourceUtils.getFile(path);
			LOGGER.info("-------保存绝对路径--{}", file.getAbsolutePath());
			jsonObject.remove("name");
			String jsString =jsonObject.toJSONString();
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			writer.write(jsString);
			writer.close();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;

	}

	@Override
	public String getPolicy(String policyName) throws Exception {
		String path = FILE_PATH + "/" + policyName + "." + JSON;
		File file = ResourceUtils.getFile(path);
		if (!file.exists()) {
			return null;
		}
		String result = new String(Files.readAllBytes(Paths.get(path)));
		LOGGER.info("----------------{}  文件里的内容     --  {}", policyName, result);
		return result;
	}

	@Override
	public Boolean deletePolicy(String name) throws Exception {
		String path = FILE_PATH + "/" + name + "." + JSON;
		File file = ResourceUtils.getFile(path);
		if (!file.exists()) {
			return true;
		}
		return file.delete();
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			String path = FILE_PATH + "/" + filename + "." + JSON;
			Path file = Paths.get(path);
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
}
