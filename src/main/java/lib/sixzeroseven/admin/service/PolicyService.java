package lib.sixzeroseven.admin.service;
/**
*Description:政策服务
*@author WJoe
*@time 2018年6月20日 上午10:12:19
*/

import java.util.List;

import org.springframework.core.io.Resource;

import lib.sixzeroseven.admin.vo.PolicyPropertyVo;

public interface PolicyService {

	/**
	 * 获取政策的json文件列表
	 * 
	 * @return
	 */
	List<PolicyPropertyVo> getPolicyProperty() throws Exception;

	/**
	 * 保存政策到json文件
	 * 
	 * @param json
	 * @return
	 */
	Boolean savePolicyToJson(String json) throws Exception;

	/**
	 * 以json文件名为参数读取文件内容
	 * 
	 * @param policyName
	 * @return
	 */
	String getPolicy(String policyName) throws Exception;

	
	/**
	 * 删除策略
	 * @param name
	 * @return
	 */
	Boolean deletePolicy(String name) throws Exception;

	/**
	 * 加载文件
	 * @param filename
	 * @return
	 */
	Resource loadAsResource(String filename);

}
