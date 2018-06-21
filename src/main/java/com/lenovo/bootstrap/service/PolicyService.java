package com.lenovo.bootstrap.service;
/**
*Description:政策服务
*@author WJoe
*@time 2018年6月20日 上午10:12:19
*/

import java.util.List;

import com.lenovo.bootstrap.vo.PolicyPropertyVo;

public interface PolicyService {

	/**
	 * 获取政策的json文件列表
	 * 
	 * @return
	 */
	List<PolicyPropertyVo> getPolicyProperty() throws Exception;

	/**
	 * 保存政策到json文件
	 * @param json
	 * @return
	 */
	Boolean savePolicyToJson(String json);

}
