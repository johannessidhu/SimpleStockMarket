package concurrency;

/**
 * 
 * Runtime exception thrown when additional messages are received for groups that have been terminated. 
 * 
 * However, I decided not to throw exceptions as it stops the process instead I am logging the incidents by using Logger.error()
 * */
public class TerminationMessageIgnoredException extends RuntimeException {

	private static final long serialVersionUID = -7753507579933260622L;

	/**
	 * Constructor for TerminationMessageIgnoredException
	 * @param error
	 */
	public TerminationMessageIgnoredException(String error) {
		super(error);
	}
}
