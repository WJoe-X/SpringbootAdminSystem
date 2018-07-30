package lib.sixzeroseven.admin.po.valid;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午2:07:33
 */
public class ListVaild {

	@Min(value=0)
	private Integer pageNumber;

    @Range(min=1,max=30)
	private Integer PageSize;

	@NotEmpty
	private String order;

	@NotEmpty
	private String sort;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return PageSize;
	}

	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "ListVaild [pageNumber=" + pageNumber + ", PageSize=" + PageSize + ", order=" + order + ", sort=" + sort
				+ "]";
	}

}
