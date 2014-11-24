package schedulerTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;
import message.StringMessage;
import dataSructures.MessageStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for TestProducer class
 */
public class ProducerTest {

	private BlockingQueue<Message> queue;
	private MessageStorage messageStorage;

	private TestProducer producer;
	
	private Message msg1; 
	private Message msg2; 
	private Message msg3; 
	private Message msg4;
	private Message terminationMessage;
	
	private MessageFactory messageFactory = null; 


	@Before
	public void setUp() throws Exception {

		
		messageFactory = new ConcreteMessageFactory(); 

		// From the requirements/specification document
		msg1 = new StringMessage(2, "msg1");
		msg2 = new StringMessage(1, "msg2");
		msg3 = new StringMessage(2, "msg3");
		msg4 = new StringMessage(3, "msg4");
		
		terminationMessage = messageFactory.createTerminationMessage(3);
		
		queue = new LinkedBlockingQueue<Message>();
		messageStorage = new MessageStorage();

		producer = new TestProducer(queue, messageStorage);

	}
	
	/**
	 * Test the scheduling of the messages  
	 * Expected take sequence (FIFO): msg1 -> msg2 -> msg3 -> msg4
	 * @throws InterruptedException 
	 * 
	 * */
	@Test
	public void testProducerFromSpecSheetQueue() throws InterruptedException{

		// Run producer
		producer.run();
		
		assertEquals(msg1, queue.take());
		assertEquals(msg2, queue.take());		
		assertEquals(msg3, queue.take());
		assertEquals(terminationMessage, queue.take());
		assertEquals(msg4, queue.take());
		
	}

	/**
	 * Test the adding of the messages to the messageStorage  
	 * @throws InterruptedException 
	 * 
	 * */
	@Test
	public void testProducerFromSpecSheetMessageStorage() throws InterruptedException{

		// Run producer
		producer.run();
		
		// take from messageStorage
		assertEquals(msg1, messageStorage.removeMessagesFromStorage(2).take()); // first message in the FIFO queue for groupID 2
		assertEquals(msg2, messageStorage.removeMessagesFromStorage(1).take());		
		assertEquals(terminationMessage, messageStorage.removeMessagesFromStorage(3).take());
		
	}
	
	@After
	public void tearDown() {
		queue = null;
		messageStorage = null;
		producer = null;
		messageFactory = null;
	}

}


/**
 * This nested class is a slightly modified version of the concurrency.Producer class
 * The difference being it adds the predefined messages for the tests to the BlockingQueue
 * Instead of randomly generating the Messages, all other logic is identical with concurrency.Producer
 * 
 * */
class TestProducer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(TestProducer.class);	
	
	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	private MessageFactory messageFactory = null; 
	
	protected BlockingQueue<Message> queue = null;

	protected MessageStorage messageStorage = null;

	protected TestProducer(BlockingQueue<Message> queue, MessageStorage messageStorage) {
		this.queue = queue;
		this.messageStorage = messageStorage;
	}

	/**
	 * run() method for the producer which simulates the adding of messages to the queue and MessageStorage
	 * This method generated random messages and adds them to the afore mentioned data structures 
	 * with groupIDs from 0 to Long.MAX_VALUE
	 * 
	 * */
	public void run() {
		try {

			messageFactory = new ConcreteMessageFactory();
			
			Message msg1 = new StringMessage(2, "msg1");
			Message msg2 = new StringMessage(1, "msg2");
			Message msg3 = new StringMessage(2, "msg3");
			Message msg4 = new StringMessage(3, "msg4");

			Message terminationMessage = messageFactory.createTerminationMessage(3);
			
			// add the Message to the queue
			queue.put(msg1);
			// add the new Message to the messageStorage
			messageStorage.addMessageToStorage(msg1);
			// Log the information 
			LOGGER.info("Added: " + msg1);
			// simulate a random intervals of messages being added to the queue 

			// add the Message to the queue
			queue.put(msg2);
			// add the new Message to the messageStorage
			messageStorage.addMessageToStorage(msg2);
			// Log the information 
			LOGGER.info("Added: " + msg2);
			// simulate a random intervals of messages being added to the queue 

			// add the Message to the queue
			queue.put(msg3);
			// add the new Message to the messageStorage
			messageStorage.addMessageToStorage(msg3);
			// Log the information 
			LOGGER.info("Added: " + msg3);
			// simulate a random intervals of messages being added to the queue 

			// Extra credit for Termination Message for a particular groupID - this if statement turns a newMessage into a termination message
			// add the Message to the queue
			queue.put(terminationMessage);
			// add the new Message to the messageStorage
			messageStorage.addMessageToStorage(terminationMessage);
			// Log the information 
			LOGGER.info("Added: " + terminationMessage);
			// simulate a random intervals of messages being added to the queue 
			
			// add the Message to the queue
			queue.put(msg4);
			// add the new Message to the messageStorage
			messageStorage.addMessageToStorage(msg4);
			// Log the information 
			LOGGER.info("Added: " + msg4);
			// simulate a random intervals of messages being added to the queue 

			// For the extra credit Termination Messages after all messages have been send
			queue.put(POISON);

		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException " + e);
			e.printStackTrace();
		}
	}

}


