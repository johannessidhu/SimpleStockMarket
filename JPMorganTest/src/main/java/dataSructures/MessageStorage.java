package dataSructures;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import message.Message;
import message.StringMessage;


/**
 * This data structure is used to actual hold all Messages to be processed and to be potentially send to the Gateway 
 * The reason to use a ConcurrentHashMap is as it allows O(1) for look up and it ensures that each thread can access 
 * sections of the underlying HashMap without blocking, however it blocks if accessing a particular key,value pair
 * if it is already been accessed by another thread
 * 
 * This should be useful since I am using two data structures to implement the priority queue from the specification
 * To allow for cohesion and modularity as such 
 * 
 * I was torn to either use Message (more readable) as the type or E Generic Object (more generic), I decided to make it more readable and use Message
 * @author Johannes Sidhu
 * */



public class MessageStorage{

	// messageStorage: the key is the groupID and the value is a LinkedBlockingQueue 
	// of all the message in that groupID, that still need to be send.
	private ConcurrentHashMap <Long, LinkedBlockingQueue<Message>> messageStorage;

	public MessageStorage() {
		this.messageStorage = new ConcurrentHashMap <Long, LinkedBlockingQueue<Message>>();
	}

	/**
	 * 
	 * Method to add an Message to the storage, add a new queue if the Message's grouId not already has
	 * an entry as a key, otherwise add the message to the back of the queue of messages of the same groupID 
	 * @param Message msg which should be added to the storage
	 *  
	 * */
	public void addMessageToStorage(Message msg) throws IllegalArgumentException{

		StringMessage stringMessage = new StringMessage();

		if(msg instanceof StringMessage) {

			stringMessage = (StringMessage) msg;

		}else {
			throw new IllegalArgumentException("Wrong type of argument passed in");
		}
		
		if(messageStorage.get(stringMessage.getGroupID()) != null){
			messageStorage.get(stringMessage.getGroupID()).add(msg);
		}
		else{ // if the grouID does not already exist as a Key then create a new queue and add the message to it.
			messageStorage.put(stringMessage.getGroupID(), new LinkedBlockingQueue<Message>());
			messageStorage.get(stringMessage.getGroupID()).add(msg);
		}
	}

	/**
	 * 
	 * Method to remove and return the list of Message from the storage, to caller,
	 * if however the groupID does not exist as a key then Return null
	 * @param long groupIDToBeRemoved from storage
	 * @return queue (of type LinkedBlockingQueue) of the grouID or Null if there are no message with the grouID provided
	 * 
	 * */
	public LinkedBlockingQueue<Message> removeMessagesFromStorage(long groupIDToBeRemoved) {

		if(messageStorage.get(groupIDToBeRemoved) != null){
			LinkedBlockingQueue<Message> tempList = messageStorage.get(groupIDToBeRemoved);
			messageStorage.remove(groupIDToBeRemoved);
			return tempList;
		}
		else{ // if the grouID does not exist as a Key then return null
			return null;
		}
	}

	/**
	 * 
	 * Method returns the size of the messageStorage, it is limited to int size.
	 * @return size of the messageStorage
	 * 
	 * */
	public int size() {
		return messageStorage.size();
	}

	/**
	 * 
	 * Method clear() clears out all the contents in the messageStorage.
	 * 
	 * */
	public void clear() {
		messageStorage.clear();
	}

	/**
	 * Method that returns only the reference of the BlockingQueue does not permanently remove it from the storage
	 * @param groupID  
	 * @return the BlockingQueue of a particular groupID, or return null
	 */
	public BlockingQueue<Message> getBlockingQueue(long groupID) {
		return messageStorage.get(groupID);
	}
	
	/**
	 * @return the messageStorage
	 */
	public ConcurrentHashMap <Long, LinkedBlockingQueue<Message>> getMessageStorage() {
		return messageStorage;
	}

	/**
	 * @param messageStorage the messageStorage to set
	 */
	public void setMessageStorage(ConcurrentHashMap <Long, LinkedBlockingQueue<Message>> messageStorage) {
		this.messageStorage = messageStorage;
	}
}
