package concurrency;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class CopyOfProducer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(CopyOfProducer.class);	

	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	protected BlockingQueue queue = null;

	public CopyOfProducer(BlockingQueue queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			
			Random rnd = new Random();
			for(int x = 0; x <= 10; x++) {
				
				//Message tempMessage = (StringMessage) StringMessage.generateRandomMessage(10);
				StringMessage tempMessage = (StringMessage) StringMessage.generateRandomMessage(10);
				
				tempMessage.setGroupID(x);
				tempMessage.setStringMessage("msg"+x);
				queue.put(tempMessage);
				LOGGER.info(Thread.currentThread().getName() + " put: " + tempMessage);
				Thread.sleep(rnd.nextInt(100));
			}
			queue.put(POISON);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}