package exceptions;

@SuppressWarnings("serial")
public class HyperWebException extends Exception {
	
	public HyperWebException() {
		return;
	}

	public HyperWebException(String message) {
		super(message);
	}

	public HyperWebException(Throwable throwable) {
		super(throwable);
	}

	public HyperWebException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
