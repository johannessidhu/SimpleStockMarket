package concurrency;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.Message;
import message.StringMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dataSructures.MessageStorage;


public class Consumer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);	

	protected BlockingQueue queue = null;

	protected MessageStorage messageStorage = null;

	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	public Consumer(BlockingQueue queue, MessageStorage messageStorage) {
		this.queue = queue;
		this.messageStorage = messageStorage;
	}

	public void run() {
		try {
			Random rnd = new Random();

			whileLoop:while(true) {

				// make sure the object from the queue is a message
				// Queue could only hold the grouID instead of the message object, makes it lightweight, since I am actually only using the groupID
				Object obj = queue.take();
				StringMessage msg = null;
				if(obj instanceof Message) {
					msg = (StringMessage) obj; 
				}else {
					throw new Exception("Not a Message type in the queue");
				}
				if(msg.equals(POISON)) {
					queue.put(POISON);
					System.out.println("Done " + Thread.currentThread().getName());
					break whileLoop;
				}

				// Get the BlockingQueue from the storage.
				LinkedBlockingQueue listOfMessagesOfID = null; 
				
				// if the messages of the current message in the income queue is not empty i.e. not null then get the list and remove it from the storage
				if((listOfMessagesOfID = messageStorage.removeMessagesFromStorage(msg.getGroupID())) != null ) {		

					// Process the BlockingQueuq
					// call the completed() method of Messages
					for(Object objMsg : listOfMessagesOfID) {
						StringMessage strMsg = null;
						if(objMsg instanceof Message) {
							strMsg = (StringMessage) objMsg; 
						}else {
							throw new Exception("Not a Message type in the queue");
						}
						LOGGER.error(Thread.currentThread().getName() + " " +  strMsg.completed());	
					}
				}

			//	Thread.sleep(rnd.nextInt(100));


			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopRunning() {
		try {
			queue.put(POISON);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}