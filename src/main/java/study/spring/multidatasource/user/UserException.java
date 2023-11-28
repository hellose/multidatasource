package study.spring.multidatasource.user;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = -8037143107889907160L;

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(String message) {
		super(message);
	}

}
