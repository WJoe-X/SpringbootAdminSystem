package lib.sixzeroseven.admin.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Description:Policy的json格式的文件属性
 * 
 * @author WJoe
 * @time 2018年6月20日 上午10:14:04
 */
public class PolicyPropertyVo {

	private String name;

	
	
	
	private String updatedDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}