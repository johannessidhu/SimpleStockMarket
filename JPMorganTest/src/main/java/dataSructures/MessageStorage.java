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

	private ConcurrentHashMap <Long, LinkedBlockingQueue<Message>> messageStorage;

	public MessageStorage() {
		this.messageStorage = new ConcurrentHashMap <Long, LinkedBlockingQueue<Message>>();
	}

	public void addMessageToStorage(Message msg) throws IllegalArgumentException{
		StringMessage stringMessage = new StringMessage();
		if (msg instanceof StringMessage) {
			stringMessage = (StringMessage) msg;
		} else {
			throw new IllegalArgumentException("Wrong type of argument passed in");
		}

		if (messageStorage.get(stringMessage.getGroupID()) != null) {
			messageStorage.get(stringMessage.getGroupID()).add(msg);
		} else {
			messageStorage.put(stringMessage.getGroupID(), new LinkedBlockingQueue<Message>());
			messageStorage.get(stringMessage.getGroupID()).add(msg);
		}
	}

	public LinkedBlockingQueue<Message> removeMessagesFromStorage(long groupIDToBeRemoved) {
		if (messageStorage.get(groupIDToBeRemoved) != null) {
			LinkedBlockingQueue<Message> tempList = messageStorage.get(groupIDToBeRemoved);
			messageStorage.remove(groupIDToBeRemoved);
			return tempList;
		} else {
			return null;
		}
	}

	public int size() {
		return messageStorage.size();
	}

	public void clear() {
		messageStorage.clear();
	}

	public BlockingQueue<Message> getBlockingQueue(long groupID) {
		return messageStorage.get(groupID);
	}

}