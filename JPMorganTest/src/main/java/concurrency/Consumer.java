package concurrency;

import gateway.Gateway;
import java.util.concurrent.BlockingQueue;
import message.Message;
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

public class Consumer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);	

	// POISON added as the very last message in the process, when no more messages are going to be received, to terminate all threads
	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	// TERMINATION_MESSAGE for extra credit the very last message in a group.
	private static final String TERMINATION_MESSAGE = "TERMINATION_MESSAGE";

	// CANCELLATION_MESSAGE for extra credit the very last message in a group.
	private static final String CANCELLATION_MESSAGE = "CANCELLATION_MESSAGE";

	//using protected modifier since it allows for classes in the same package to access the shared resources.
	protected BlockingQueue<Message> queue = null;

	protected MessageStorage messageStorage = null;

	protected Gateway gateway = null;

	public Consumer(BlockingQueue<Message> queue, MessageStorage messageStorage, Gateway genericGateway) {
		this.queue = queue;
		this.messageStorage = messageStorage;
		this.gateway = genericGateway;

	}

	public void run() {
		try {

			infiniteLoop:while(true) {

				// Makes sure that the object from the queue is a message
				Object obj = queue.take();
				StringMessage msg = null;
				if(obj instanceof Message) {
					msg = (StringMessage) obj; 
				}else {
					LOGGER.error("Not a Message type in the queue");
					throw new ClassCastException("Not a Message type in the queue");
				}
				// if POISON then break infinite loop
				if(msg.equals(POISON)) {
					stopRunning();
					break infiniteLoop;
				}

				BlockingQueue<?> listOfMessagesOfID = null; 

				// if the messages of the current message in the income queue is not empty
				// i.e. not null then get the list and remove it from the message storage
				if((listOfMessagesOfID = messageStorage.removeMessagesFromStorage(msg.getGroupID())) != null ) {		

					// iterate through the queue of the current groupID and process each message in FIFO order
					for(int x = 0; x < listOfMessagesOfID.size(); x++) {

						Object objMsg = listOfMessagesOfID.take();

						StringMessage strMsg = null;
						if(objMsg instanceof Message) {
							strMsg = (StringMessage) objMsg; 
						}else {
							LOGGER.error("Not a Message type in the queue");
							throw new ClassCastException("Not a Message type in the queue");
						}

						if(strMsg.getStringMessage().equalsIgnoreCase(TERMINATION_MESSAGE)){

							processTerminationMessage(strMsg, listOfMessagesOfID.size());
							
						}else if(strMsg.getStringMessage().equalsIgnoreCase(CANCELLATION_MESSAGE)) {

							processCancellationMessage(strMsg);
							
						}else {
							// if the current message is not a TERMINATION_MESSAGE or CANCELLATION_MESSAGE 
							// call the complete() of the message and send it to the gateway

							strMsg.completed();
							gateway.send(strMsg);

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
	private void processTerminationMessage(StringMessage strMsg, int sizeOFList) {

		// Current message in the list is the Termination message and the size of the list is greater than 0, 
		// which indicates there were additional messages received after the termination message was send for this groupID
		if((strMsg.getStringMessage().equalsIgnoreCase(TERMINATION_MESSAGE)) && (sizeOFList > 0)) {
			// Add a new TERMINATION MESSAGE to the messageStore, since we removed the current termination message from the storage for further use
			messageStorage.addMessageToStorage(strMsg);

			LOGGER.error("TERMINATION_MESSAGE already received, but new messages received for groupID " + strMsg.getGroupID() + " added.");
			// I decided not to throw a new exception, as it stops the thread from processing, instead I log the error
			// throw new TerminationMessageIgnoredException("TERMINATION_MESSAGE already received, but new messages received for groupID " + strMsg.getGroupID() + "added.");

			// else if the termination message is the last in the current List, 
			// then add the Termination message back to the queue, but this time it will at the front and if there are additional messages in the List 
			// (the size of the list will be greater than 1) the above if statement will be true next time and the exception or error will be thrown/logged.
		}else if (strMsg.getStringMessage().equalsIgnoreCase(TERMINATION_MESSAGE)){

			// if a list already exists, i.e. 
			// This covers the case: while the above code was executing new messages were added to the messageStorage from the same groupID
			// therefore either throw an exception or only log the error
			if(messageStorage.getBlockingQueue(strMsg.getGroupID()) != null){
				LOGGER.error("TERMINATION_MESSAGE already received, for " + strMsg.getGroupID() + ".");
				// throw new TerminationMessageIgnoredException("TERMINATION_MESSAGE already received, 
				// but new messages received for groupID " + strMsg.getGroupID());
			}
			else{ // no new message was added in the mean time, so add the termination message to the storage for it's groupID
				messageStorage.addMessageToStorage(strMsg);
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
	private void processCancellationMessage(StringMessage strMsg) {

		//System.err.println(strMsg.getGroupID());
		
		if(messageStorage.getBlockingQueue(strMsg.getGroupID()) != null) {
			// remove all messages with the groupID to be cancelled 
			queue.removeAll(messageStorage.getBlockingQueue(strMsg.getGroupID()));
			// remove all messages from the store
			messageStorage.removeMessagesFromStorage(strMsg.getGroupID());
		}
		
		// add CANCELLATION_MESSAGE to replace the currently taken message to storage for further use
		messageStorage.addMessageToStorage(StringMessage.createCancellationMessage(strMsg.getGroupID()));

		LOGGER.info("All messages for groupID " + strMsg.getGroupID() + " are cancelled.");

	}

}