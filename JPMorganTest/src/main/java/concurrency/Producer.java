package concurrency;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dataSructures.MessageStorage;
import message.Message;
import message.StringMessage;

import java.util.Random;

/**
 * 
 * Class represents the income channel for the messages
 * It randomly creates messages and adds them to a BlockingQueue
 * It also utilizes a random thread sleep to simulate the random throughput of messages
 * 
 * */
public class Producer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);	

	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	protected BlockingQueue queue = null;
	
	protected MessageStorage messageStorage = null;

	public Producer(BlockingQueue queue, MessageStorage messageStorage) {
		this.queue = queue;
		this.messageStorage = messageStorage;
	}

	public void run() {
		try {
			
			Random rnd = new Random();
			for(int x = 0; x <= 100; x++) {
				
				//Message tempMessage = (StringMessage) StringMessage.generateRandomMessage(10);
				StringMessage tempMessage = (StringMessage) StringMessage.generateRandomMessage(10);
				
				tempMessage.setGroupID(x % 2);
				tempMessage.setStringMessage("msg"+x);
				queue.put(tempMessage);
				messageStorage.addMessageToStorage(tempMessage);
				LOGGER.info(Thread.currentThread().getName() + " put: " + tempMessage);
				//Thread.sleep(rnd.nextInt(100));
			}
			queue.put(POISON);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}