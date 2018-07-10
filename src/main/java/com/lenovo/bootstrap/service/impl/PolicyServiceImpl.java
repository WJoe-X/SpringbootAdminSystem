package com.lenovo.bootstrap.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
			JSONObject googleChromeosDevice = JSONObject.parseObject( fileJSON.get("google/chromeos/device").toString());
			googleChromeosDevice.remove("HomepageLocation");
			googleChromeosDevice.remove("ShowHomeButton");
			googleChromeosDevice.remove("NewTabPageLocation");
			googleChromeosDevice.remove("URLWhitelist");
			googleChromeosDevice.remove("URLBlacklist");
			googleChromeosDevice.remove("device_wallpaper_image");
			JSONObject deviceallpaperImage = JSONObject.parseObject( jsonObject.get("device_wallpaper_image").toString());
			//得到device_wallpaper_image 下的url 字段的值
			String url = deviceallpaperImage.get("url").toString();
			String stream = readFileByUrl(url);
			String strShare256 = getSHA256StrJava(stream);
			LOGGER.info("strSha256   : {}",strShare256);
			deviceallpaperImage.put("hash", strShare256);
			jsonObject.remove("device_wallpaper_image");
			googleChromeosDevice.putAll(jsonObject);
			googleChromeosDevice.put("device_wallpaper_image", deviceallpaperImage.toJSONString());
			LOGGER.info("---------修改添加的 json  : {}",googleChromeosDevice.toString());
			fileJSON.remove("google/chromeos/device");
			//String jsString = gJsonObject.toJSONString();
			fileJSON.put("google/chromeos/device", googleChromeosDevice);
			LOGGER.info("---------修改添加的 文件json  :  {}",fileJSON.toString());
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
	
	private static String readFileByUrl(String urlStr) {
        String res=null;
        try {
            URL url = new URL(urlStr);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();  
            res = readInputStream(inputStream);
        } catch (Exception e) {
            LOGGER.error("通过url地址获取文本内容失败 Exception：" + e);
        }
        return res;
    }
	
	/**
     * 从输入流中获取字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        System.out.println(new String(bos.toByteArray(),"utf-8"));
        return new String(bos.toByteArray(),"utf-8");
    }
	
	/**
     *  利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    private static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
        	LOGGER.error("通过url地址获取文本内容,加密后报文失败 Exception：" + e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	LOGGER.error("通过url地址获取文本内容,加密后报文失败 Exception：" + e);
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
