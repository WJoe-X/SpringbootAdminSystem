package com.lianxiang.util;

import java.util.UUID;

/**
*Description:UUID工具类
*@author WJoe
*@time 上午10:11:00
*/
public class UuidUtil {
	
	    /**
	     * 获得一个UUID
	     *
	     * @return String UUID
	     */
	    public static String getUUID() {
	        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	    }

	    /**
	     * 获得指定数目的UUID
	     *
	     * @param number int 需要获得的UUID数量
	     * @return String[] UUID数组
	     */
	    public static String[] getUUID(int number) {
	        if (number < 1) {
	            return null;
	        }
	        String[] ss = new String[number];
	        for (int i = 0; i < number; i++) {
	            ss[i] = getUUID();
	        }
	        return ss;
	    }
	}


