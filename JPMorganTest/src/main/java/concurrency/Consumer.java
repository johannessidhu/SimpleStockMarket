package concurrency;

import gateway.Gateway;
import java.util.concurrent.BlockingQueue;
import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;
import message.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dataSructures.MessageStorage;

/**
 * 
 * This class implements the consumer in the "Producer consumer design pattern".
 * 
 * It takes the first Message from the queue and reads its groupID, 
 * Using the groupID, it removes the corresponding list of messages for that group from the messageStorage
 * It processes the individual messages in the retrieved list in a FIFO manner, 
 * If the messages takes from the list is a TERMINATION_MESSAGE or CANCELLATION_MESSAGE the corresponding processes are completed.
 * Otherwise if it is a generic messages it is processed and send to the Gateway.
 * 
 * @author Johannes Sidhu
 * */

public class Consumer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);	
	// POISON added as the very last message in the process, when no more messages are going to be received, to terminate all threads
	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 
	// TERMINATION_MESSAGE for extra credit the very last message in a group.
	private static final String TERMINATION_MESSAGE = "TERMINATION_MESSAGE";
	// CANCELLATION_MESSAGE for extra credit the very last message in a group.
	private static final String CANCELLATION_MESSAGE = "CANCELLATION_MESSAGE";
	private MessageFactory messageFactory = null; 

	protected BlockingQueue<Message> queue = null;
	protected MessageStorage messageStorage = null;
	protected Gateway gateway = null;

	public Consumer(BlockingQueue<Message> queue, MessageStorage messageStorage, Gateway genericGateway) {
		this.queue = queue;
		this.messageStorage = messageStorage;
		this.gateway = genericGateway;
		this.messageFactory = new ConcreteMessageFactory();
	}

	public void run() {
		try {
			infiniteLoop:while (true) {
				Object obj = queue.take();
				Message msg = null;
				if (obj instanceof Message) {
					msg = (Message) obj; 
				} else {
					LOGGER.error("Not a Message type in the queue");
					throw new ClassCastException("Not a Message type in the queue");
				}
				// if POISON then break infinite loop
				if (msg.equals(POISON)) {
					stopRunning();
					break infiniteLoop;
				}

				BlockingQueue<?> listOfMessagesOfID = null; 

				if ((listOfMessagesOfID = messageStorage.removeMessagesFromStorage(msg.getGroupID())) != null) {		
					for (int x = 0; x < listOfMessagesOfID.size(); x++) {
						Object objMsg = listOfMessagesOfID.take();
						Message storageMsg = null;
						if (objMsg instanceof Message) {
							storageMsg = (Message) objMsg; 
						} else {
							LOGGER.error("Not a Message type in the queue");
							throw new ClassCastException("Not a Message type in the queue");
						}

						if (storageMsg.getData().equalsIgnoreCase(TERMINATION_MESSAGE)) {
							processTerminationMessage(storageMsg, listOfMessagesOfID.size());
						} else if (storageMsg.getData().equalsIgnoreCase(CANCELLATION_MESSAGE)) {
							processCancellationMessage(storageMsg);
						} else {
							storageMsg.completed();
							gateway.send(storageMsg);
						}
					}
				}
			}
		LOGGER.info("Has finished processing and terminated successfully.");
		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException " + e);
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("Generic exception caught " + e);
			e.printStackTrace();
		}
	}

	public void stopRunning() {
		try {
			queue.put(POISON);
		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException " + e);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Extra credit: Termination
	 * 
	 * Method to process termination messages
	 * 
	 * Please NOTE: My understanding is that cancellation messages over rule termination messages, since cancellation cancels all messages for a group.
	 * This I understand also includes termination messages which indicate the natural end of a group of messages.
	 * Cancellation has priority over all other messages in the group. 
	 * 
	 * The behavior also includes the scenario where a termination messages is received before a cancellation message, in this case the termination message over rulles
	 * since we do not expect anymore messages regarding the terminated group, i.e. the termination message should be the very last message.
	 * 
	 * */
	private void processTerminationMessage(Message msg, int sizeOFList) {
		if ((msg.getData().equalsIgnoreCase(TERMINATION_MESSAGE)) && (sizeOFList > 0)) {
			messageStorage.addMessageToStorage(msg);
			LOGGER.error("TERMINATION_MESSAGE already received, but new messages received for groupID " + msg.getGroupID() + " added.");
		} else if (msg.getData().equalsIgnoreCase(TERMINATION_MESSAGE)) {
			if (messageStorage.getBlockingQueue(msg.getGroupID()) != null) {
				LOGGER.error("TERMINATION_MESSAGE already received, for " + msg.getGroupID() + ".");
			} else { 
				messageStorage.addMessageToStorage(msg);
				LOGGER.info("Add TERMINATION_MESSAGE to the storage.");				
			}
		}
	}

	/**
	 * Extra credit: Cancellation
	 * 
	 * Method to process termination messages
	 * 
	 * Please NOTE: My understanding is that cancellation messages over rule termination messages, since cancellation cancels all messages for a group.
	 * This I understand also includes termination messages which indicate the natural end of a group of messages.
	 * Cancellation has priority over all other messages in the group. 
	 * 
	 * The behavior also includes the scenario where a termination messages is received before a cancellation message, in this case the termination message over rulles
	 * since we do not expect anymore messages regarding the terminated group, i.e. the termination message should be the very last message. 
	 * 
	 * */
	private void processCancellationMessage(Message msg) {
		if (messageStorage.getBlockingQueue(msg.getGroupID()) != null) {
			queue.removeAll(messageStorage.getBlockingQueue(msg.getGroupID()));
			messageStorage.removeMessagesFromStorage(msg.getGroupID());
		}
		
		messageStorage.addMessageToStorage(messageFactory.createCancellationMessage(msg.getGroupID()));
		LOGGER.info("All messages for groupID " + msg.getGroupID() + " are cancelled.");
	}

}