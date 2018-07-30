package lib.sixzeroseven.admin.enums;

/**
 * Description:登录类型
 * 
 * @author WJoe
 * @time 下午1:38:40
 */
public enum LoginEnum {

	CUSTOMER("1"), ADMIN("2");

	private String type;

	private LoginEnum(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type.toString();
	}

}
