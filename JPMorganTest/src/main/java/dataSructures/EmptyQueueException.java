package dataSructures;

/**
 * 
 * Runtime exception thrown when one tries to perform operation front() and dequeue() on an empty dataSructures
 * 
 * */
public class EmptyQueueException extends RuntimeException {

	public EmptyQueueException(String error) {
		super(error);
	}
}
