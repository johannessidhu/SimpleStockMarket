package resourceScheduler;

import java.util.ArrayList;
import java.util.HashMap;

import message.Message;

public class Scheduler {

	// messageStorage: the key is the groupID and the value is an arrayList 
	// of all the message in that groupID, that still need to be send
	private HashMap<Integer, ArrayList<Message>> messageStorage;
	
	//private Queue fifoQueue
	
	/**
	 * Forwarding
	 *  The class you write must forward Messages via the Gateway interface when resources are available:
	 * 		For a single resource, when one message is received, that message is sent to the gateway
	 * 		For two resources, when two messages are received, both messages are sent to the gateway
	 *
	 * */
	public void forward(Message msg) {

	}

	/**
	 * Queuing
	 * 	When no resources are available, messages should not be sent to the Gateway
	 * 	For a single resource, when two messages are received, only the first message is sent to the gateway
	 * 
	 * */
	public void queue(Message msg) {

		// important the message being passed in must be added to both the queue structure and the storage
		
	}
	
	/**
	 * Responding
	 * As messages are completed, if there are queued messages, they should be processed
	 * Same as the queuing above, but after the first message is completed, the second message is sent to the gateway
	 * 
	 * */
	public void respond() {
		
	}
	
	/**
	 * Prioritising 
	 * 
	 * If there are messages belonging to multiple groups in the dataSructures, as resources become available, we want to prioritise messages from groups already started.
	 * 
	 * */
	public void prioritise() {
		
	} 
	
	
}
