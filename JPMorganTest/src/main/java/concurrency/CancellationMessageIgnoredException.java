package concurrency;

/**
 * 
 * Runtime exception thrown when additional messages are received for groups that have been cancelled. 
 * However, I decided not to throw exceptions as it stops the process instead I am logging the incidents by using Logger.error()
 * 
 * */
public class CancellationMessageIgnoredException extends RuntimeException {

	private static final long serialVersionUID = -6919839264211953593L;

	public CancellationMessageIgnoredException(String error) {
		super(error);
	}

}
