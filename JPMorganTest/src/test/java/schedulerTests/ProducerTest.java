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

import concurrency.Producer;

public class ProducerTest {

	private BlockingQueue<Message> queue;
	private MessageStorage messageStorage;
	private MockProducer producer;
	private Message msg1; 
	private Message msg2; 
	private Message msg3; 
	private Message msg4;
	private Message terminationMessage;
	private MessageFactory messageFactory = null; 

	@Before
	public void setUp() throws Exception {
		messageFactory = new ConcreteMessageFactory(); 
		msg1 = new StringMessage(2, "msg1");
		msg2 = new StringMessage(1, "msg2");
		msg3 = new StringMessage(2, "msg3");
		msg4 = new StringMessage(3, "msg4");
		terminationMessage = messageFactory.createTerminationMessage(3);
		queue = new LinkedBlockingQueue<Message>();
		messageStorage = new MessageStorage();
		producer = new MockProducer(queue, messageStorage);
	}

	@Test
	public void testProducerFromSpecSheetQueue() throws InterruptedException{
		producer.run();

		assertEquals(msg1, queue.take());
		assertEquals(msg2, queue.take());		
		assertEquals(msg3, queue.take());
		assertEquals(terminationMessage, queue.take());
		assertEquals(msg4, queue.take());
	}

	@Test
	public void testProducerFromSpecSheetMessageStorage() throws InterruptedException{
		producer.run();

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
 * This nested mock class is a slightly modified version of the concurrency.Producer class
 * The difference being it adds the predefined messages for the tests to the BlockingQueue
 * Instead of randomly generating the Messages, all other logic is identical with concurrency.Producer
 * 
 * */
class MockProducer extends Producer {

	protected MockProducer(BlockingQueue<Message> queue, MessageStorage messageStorage) {
		super(queue, messageStorage, null);
	}

	@Override
	public void run() {
		try {
			messageFactory = new ConcreteMessageFactory();
			Message msg1 = new StringMessage(2, "msg1");
			Message msg2 = new StringMessage(1, "msg2");
			Message msg3 = new StringMessage(2, "msg3");
			Message msg4 = new StringMessage(3, "msg4");
			Message terminationMessage = messageFactory.createTerminationMessage(3);

			queue.put(msg1);
			messageStorage.addMessageToStorage(msg1);
			queue.put(msg2);
			messageStorage.addMessageToStorage(msg2);
			queue.put(msg3);
			messageStorage.addMessageToStorage(msg3);
			// Extra credit for Termination Message for a particular groupID - this if statement turns a newMessage into a termination message
			queue.put(terminationMessage);
			messageStorage.addMessageToStorage(terminationMessage);
			queue.put(msg4);
			messageStorage.addMessageToStorage(msg4);
			// For the extra credit Termination Messages after all messages have been send
			queue.put(POISON);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}