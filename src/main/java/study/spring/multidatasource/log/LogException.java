package study.spring.multidatasource.log;

public class LogException extends RuntimeException {

	private static final long serialVersionUID = -8037143107889907160L;

	public LogException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogException(String message) {
		super(message);
	}

}
