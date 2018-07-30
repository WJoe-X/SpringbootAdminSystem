package lib.sixzeroseven.admin.exception;

/**
 * Description:
 * 
 * @author WJoe
 * @time 2018年5月14日 下午2:58:44
 */
public class StorageException extends RuntimeException {

	/**
	 * @date 2018年5月14日
	 */
	private static final long serialVersionUID = 1L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

}
