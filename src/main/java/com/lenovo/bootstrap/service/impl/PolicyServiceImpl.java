package com.lenovo.bootstrap.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
			PolicyPropertyVo policyPropertyVo = new PolicyPropertyVo();
			for (File jsonfile : filelist) {
				policyPropertyVo.setName(jsonfile.getName().substring(0, jsonfile.getName().lastIndexOf(".")));
				policyPropertyVo.setUpdatedDate(sdf.format(jsonfile.lastModified()));
				policyPropertyVos.add(policyPropertyVo);

			}
			return policyPropertyVos;
		}
		return null;
	}

	@Override
	public Boolean savePolicyToJson(String json) {
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			String path = FILE_PATH + "/" + jsonObject.getString("name") + "." + JSON;
			File file = ResourceUtils.getFile(path);
			LOGGER.info("-------保存绝对路径--{}", file.getAbsolutePath());
			
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			writer.write(json);
			writer.close();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;

	}
}
