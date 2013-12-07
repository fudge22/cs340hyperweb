package exceptions;
@SuppressWarnings("serial")
public class VisitorException extends Exception {

	public VisitorException() {
		return;
	}

	public VisitorException(String message) {
		super(message);
	}

	public VisitorException(Throwable throwable) {
		super(throwable);
	}

	public VisitorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
