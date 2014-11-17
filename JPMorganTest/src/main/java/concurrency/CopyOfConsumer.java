package concurrency;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import message.Message;
import message.StringMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CopyOfConsumer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(CopyOfConsumer.class);	

	protected BlockingQueue queue = null;

	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	public CopyOfConsumer(BlockingQueue queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			Random rnd = new Random();

			whileLoop:while(true) {
				// make sure the object from the queue is a message
				Object obj = queue.take();
				Message msg = null;
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
				LOGGER.error(Thread.currentThread().getName() + " took: "+ msg);	
				Thread.sleep(rnd.nextInt(100));


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