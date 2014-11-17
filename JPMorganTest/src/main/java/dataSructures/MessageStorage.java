package dataSructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.NullArgumentException;

import message.Message;
import message.StringMessage;


/**
 * This data structure is used to actual hold all messages(objects) for the scheduler
 * The reason to use a HashMap is as it allows O(1) for look up 
 * this should be useful since I am using two data structures to implement the priority queue from the specification
 * To allow for cohesion and modularity as such
 * I am not using a ConcurrentHashMap because, I am using LinkedBlockingQueue as the value, 
 * which ensures that each thread can access the HashMap without blocking but if accessing a queue the synchronization is taken care of by the ArrayBlockingQueue.
 * 
 * I am torn to either use Message (more specific) as the type or E Generic Object (more generic but increases complexity with type casting)
 * 
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
	 * Method to add an Message to the storage, add a new ArrayList if the Message's grouId not already has
	 * an entry as a key, otherwise add the message to the back of the arrayList of messages of the same groupID 
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
		else{ // if the grouID does not already exist as a Key then add it to it and create a new list and add the message to it.
			messageStorage.put(stringMessage.getGroupID(), new LinkedBlockingQueue<Message>());
			messageStorage.get(stringMessage.getGroupID()).add(msg);
		}
	}

	/**
	 * 
	 * Method to remove and return the list of Message from the storage, to caller,
	 * if however the groupID does not exist as a key then Return null
	 * @param long groupIDToBeRemoved from storage
	 * @return ArrayList<Message> of the grouID or Null if there are no messsage with the grouID provided
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
