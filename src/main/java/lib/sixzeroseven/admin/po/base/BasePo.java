package lib.sixzeroseven.admin.po.base;

/**
 * Description:查询列表所需要的基础信息
 * 
 * @author WJoe
 * @time 下午3:17:35
 */
public class BasePo {

	private Integer offset = 0;

	private Integer limit = 10;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
