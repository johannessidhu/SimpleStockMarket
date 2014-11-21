package concurrency;

/**
 * 
 * Runtime exception thrown when additional messages are received for groups that have been terminated. 
 * 
 * */
public class TerminationMessageIgnoredException extends RuntimeException {

	private static final long serialVersionUID = -7753507579933260622L;

	public TerminationMessageIgnoredException(String error) {
		super(error);
	}
}
