package dataSructures;

import java.util.ArrayList;

import message.Message;

/**
 * 
 * Class implements the interface for Queue ADT using an ArrayList
 * This will be used to hold the actual dataSructures coming in and keep the simple FIFO
 * 
 * I am torn to either use Message (more specific) as the type or E Generic Object (more generic but increases complexity with type casting)
 *  
 * */

public class FifoQueue implements Queue {

	private ArrayList<Message> queueArrayList;

	public FifoQueue() {
		this.queueArrayList = new ArrayList<Message>();
	}

	/**
	 * @param queueArrayList
	 */
	public FifoQueue(ArrayList<Message> queueArrayList) {
		this.queueArrayList = new ArrayList<Message>(queueArrayList.size());
		for(Message msg: queueArrayList) 
			this.queueArrayList.add((Message) msg);	
	}

	/**
	 * Returns the number of elements in the dataSructures
	 * @return number of elements in the dataSructures
	 * */
	public int size() {
		return this.queueArrayList.size();
	}

	/**
	 * Return whether the dataSructures is empty
	 * @return true if the dataSructures is empty.
	 * 
	 * */
	public boolean isEmpty() {
		return this.queueArrayList.isEmpty();
	}

	/**
	 * Inspects the element at the front of the dataSructures
	 * @return element at the front of the dataSructures
	 * @exception EmptyQueueException if the dataSructures is empty
	 * 
	 * */
	public Message front() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException("Queue is empty");
		}
		return this.queueArrayList.get(0);
	}

	/**
	 * Inserts an element at the rear of the dataSructures
	 * @param msg new element to be inserted
	 * 
	 * */
	public void enqueue(Message msg) throws IllegalArgumentException  {
		
		if(msg == null) {
			throw new IllegalArgumentException ();
		}
		
		this.queueArrayList.add(msg);
	}

	/**
	 * Removes the element at the front of the dataSructures
	 * @return element removed
	 * @exception EmptyQueueException if the dataSructures is empty
	 * 
	 * */
	public Message dequeue() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException("Queue is empty");
		}

		Message tempMessage = this.queueArrayList.get(0);
		this.queueArrayList.remove(0);
		return tempMessage;

	}

}
