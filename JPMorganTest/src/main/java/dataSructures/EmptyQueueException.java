package dataSructures;

/**
 * 
 * Runtime exception thrown when one tries to perform operation front() and dequeue() on an empty dataSructures
 * 
 * @author Johannes Sidhu
 * */
public class EmptyQueueException extends RuntimeException {

	private static final long serialVersionUID = -3130477213073240272L;

	public EmptyQueueException(String error) {
		super(error);
	}
}
