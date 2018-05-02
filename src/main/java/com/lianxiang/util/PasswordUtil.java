package com.lianxiang.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Description:密码生成工具类
 * 
 * @author WJoe
 * @time 上午10:13:53
 */
public class PasswordUtil {

	/**
	 * 后台密码生成
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String createAdminPwd(String password, String salt) {
		return new SimpleHash("md5", password, ByteSource.Util.bytes(salt), 2).toHex();
	}

	
	/**
	 * 用户密码生成
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String createCustomPwd(String password, String salt) {
		return new SimpleHash("md5", password, ByteSource.Util.bytes(salt), 1).toHex();
	}

}
