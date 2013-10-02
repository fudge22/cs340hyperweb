package exceptions;
@SuppressWarnings("serial")
public class WebIDException extends Exception {

	public WebIDException() {
		return;
	}

	public WebIDException(String message) {
		super(message);
		System.out.println("WebID can not be a negative value.");
	}

	public WebIDException(Throwable throwable) {
		super(throwable);
	}

	public WebIDException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
