package dataSructures;

import message.Message;


/**
 * This interface is no longer used, superseded by the use of the java.util.concurrent.BlockingQueue
 */

/**
 * 
 * Interface for Queue ADT 
 * Goodrich, Michael T., and Roberto Tamassia. Data structures and algorithms in Java. John Wiley & Sons, 2008.
 * From Page 206 with slight modifications
 * 
 * I was torn to either use Message (more readable) as the type or E Generic Object (more generic), I decided to make it more readable and use Message
 *  
 * */

public interface Queue {
	
	/**
	 * Returns the number of elements in the dataSructures
	 * @return number of elements in the dataSructures
	 * */
	public int size();
	
	/**
	 * Return whether the dataSructures is empty
	 * @return true if the dataSructures is empty.
	 * 
	 * */
	public boolean isEmpty();
	
	/**
	 * Inspects the element at the front of the dataSructures
	 * @return element at the front of the dataSructures
	 * @exception EmptyQueueException if the dataSructures is empty
	 * 
	 * */
	public Message front() throws EmptyQueueException;
	
	/**
	 * Inserts an element at the rear of the dataSructures
	 * @param element new element to be inserted
	 * 
	 * */
	public void enqueue(Message msg);
	
	/**
	 * Removes the element at the front of the dataSructures
	 * @return element removed
	 * @exception EmptyQueueException if the dataSructures is empty
	 * 
	 * */
	public Message dequeue() throws EmptyQueueException;
		
}
