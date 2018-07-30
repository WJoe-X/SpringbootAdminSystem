package lib.sixzeroseven.admin.service;
/**
*Description:  设备管理业务层
*@author WJoe
*@time 2018年7月9日 下午2:47:41
*/

import java.util.List;

import com.github.pagehelper.PageInfo;

import lib.sixzeroseven.admin.po.Device;
import lib.sixzeroseven.admin.po.valid.ListVaild;

public interface DeviceService {
	/**
	 * 通过页数查询设备列表
	 * @param listVaild
	 * @return
	 */
	PageInfo<Device> getDevice(ListVaild listVaild);
	
	
	/**
	 * 保存设备信息
	 * @param device
	 * @return
	 */
	int saveDevice(Device device);
	
	/**
	 * 通过id删除设备信息
	 * @param id
	 * @return
	 */
	int deleteDevice(int id);
	
	
	

}
