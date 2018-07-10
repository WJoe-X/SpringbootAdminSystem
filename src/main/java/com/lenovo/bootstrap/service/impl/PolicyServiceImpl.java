package com.lenovo.bootstrap.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.bootstrap.exception.StorageException;
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

	@Value(value = "${policy.path}")
	private String FILE_PATH;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String JSON = "json";

	/*
	 * private final Path rootLocation;
	 * 
	 * @Autowired public PolicyServiceImpl(StorageProperties properties) {
	 * this.rootLocation = Paths.get(properties.getpLocation()); }
	 */
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

			for (int i = 0; i < filelist.length; i++) {
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
	@Transactional
	public Boolean savePolicyToJson(String json) throws Exception {
		try {
			// client json
			LOGGER.info(" 修改JSON  : {} ",json);
			JSONObject jsonObject = JSONObject.parseObject(json);
			// read device_management file
			String path = FILE_PATH + "/" + jsonObject.getString("name") + "." + JSON;
			File file = ResourceUtils.getFile(path);
			LOGGER.info("-------保存绝对路径--{}", file.getAbsolutePath());
			jsonObject.remove("name");
			
			// device_management file to fileJSON
			if (!file.exists()) {
				return false;
			}
			String result = new String(Files.readAllBytes(Paths.get(path)));
			JSONObject fileJSON = JSONObject.parseObject(result);
			JSONObject gJsonObject = JSONObject.parseObject( fileJSON.get("google/chromeos/device").toString());
			gJsonObject.remove("HomepageLocation");
			gJsonObject.remove("ShowHomeButton");
			gJsonObject.remove("NewTabPageLocation");
			gJsonObject.remove("URLWhitelist");
			gJsonObject.remove("URLBlacklist");
			gJsonObject.putAll(jsonObject);
			LOGGER.info("---------修改添加的 json  :  ｛｝",gJsonObject.toString());
			fileJSON.remove("google/chromeos/device");
			String jsString = gJsonObject.toJSONString();
			fileJSON.put("google/chromeos/device", gJsonObject);
			LOGGER.info("---------修改添加的 文件json  :  ｛｝",fileJSON.toString());
			BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
			writer.write(fileJSON.toString());
			writer.flush();
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
		LOGGER.info("---------读取文件 路径-- ：{}",path);
		File file = ResourceUtils.getFile(path);
		if (!file.exists()) {
			LOGGER.info("---------文件不存在  ：{}",path);
			return null;
		}
		String result = new String(Files.readAllBytes(Paths.get(path)));
		LOGGER.info("----------------{}  文件里的内容     --  {}", policyName, result);
	JSONObject js = 	JSONObject.parseObject(result);
	LOGGER.info("----google/chromeos/device : {}",js.get("google/chromeos/device"));
	JSONObject gJsonObject = JSONObject.parseObject(js.get("google/chromeos/device").toString());
		return gJsonObject.toJSONString();
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
		if (filename == null || filename == "") {
			return null;
		}
		String path = "";
		if (filename.endsWith(".json")) {
			path = FILE_PATH + "/" + filename;
		} else {
			path = FILE_PATH + "/" + filename + "." + JSON;
		}
		try {
			Resource resource = new UrlResource(path);
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageException("Could not read file or file not exist: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageException("error Could not read file: " + filename, e);
		}

	}
}
