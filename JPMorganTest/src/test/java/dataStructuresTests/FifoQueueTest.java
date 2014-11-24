package dataStructuresTests;

import static org.junit.Assert.assertEquals;
import message.Message;
import message.StringMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataSructures.EmptyQueueException;
import dataSructures.FifoQueue;

/**
 * Unit tests for FifoQueue, superseded by the use of the java.util.concurrent.BlockingQueue
 */
public class FifoQueueTest {

	private FifoQueue fifoQ;
	private Message msg0; 
	private Message msg1; 
	private Message msg2; 
	private Message msg3; 


	@Before
	public void setUp() throws Exception {

		fifoQ = new FifoQueue();
		msg0 = new StringMessage(0, "msg0");
		msg1 = new StringMessage(1, "msg1");
		msg2 = new StringMessage(2, "msg2");
		msg3 = new StringMessage(2, "msg3");

		fifoQ.enqueue(msg0);
		fifoQ.enqueue(msg1);
		fifoQ.enqueue(msg2);
		fifoQ.enqueue(msg3);
		fifoQ.enqueue(msg1);
	}

	/**
	 * dequeue first element msg0
	 * @throws EmptyQueueException
	 * */
	@Test
	public void testSimpleDequeue() throws EmptyQueueException {

		StringMessage strMsg0 = (StringMessage)fifoQ.dequeue();
		System.out.println(fifoQ.size());
		assertEquals("msg0", strMsg0.getData());
	}

	/**
	 * dequeue second element msg1
	 * @throws EmptyQueueException
	 * */
	@Test
	public void testSimpleDequeue2() throws EmptyQueueException {

		fifoQ.dequeue();
		StringMessage strMsg1 = (StringMessage)fifoQ.dequeue();
		assertEquals("msg1", strMsg1.getData());
	}

	/**
	 * dequeue till last element msg0 and compare it
	 * @throws EmptyQueueException
	 * */
	@Test
	public void testSimpleDequeue3() throws EmptyQueueException {

		for(int x = 0; x < 4; x++) {
			fifoQ.dequeue();			
		}

		StringMessage strMsg1 = (StringMessage)fifoQ.dequeue();
		assertEquals("msg1", strMsg1.getData());
	}

	/**
	 * dequeue till only one element is left to test if the elements are actually removed
	 * @throws EmptyQueueException
	 * */
	@Test
	public void testSizeDequeue() throws EmptyQueueException {

		for(int x = 0; x < 4; x++) {
			fifoQ.dequeue();			
		}
		assertEquals(1, fifoQ.size());
	}

	/**
	 * 'Dequeing' more elements than there are in the queue
	 * Expect EmptyQueueException exception
 	 * @throws EmptyQueueException
	 * */
	@Test(expected = EmptyQueueException.class) 
	public void testExceptionDequeue() throws EmptyQueueException {

		for(int x = 0; x < 7; x++) {
			fifoQ.dequeue();			
		}
	}

	/**
	 * 3 * Dequeue and then front(), front should not remove the element from the queue  
	 * @throws EmptyQueueException
	 * */
	@Test
	public void testFront() throws EmptyQueueException {

		for(int x = 0; x < 3; x++) {
			fifoQ.dequeue();			
		}
		fifoQ.front();

		assertEquals(2, fifoQ.size());
	}
	
	/**
	 * Enqueue simple message  
 	 * @throws IllegalArgumentException
	 * */
	@Test 
	public void testEnqueue() throws IllegalArgumentException {

		fifoQ.enqueue(msg2);
		assertEquals(6, fifoQ.size());		
	}
	
	/**
	 * Enqueue null to the queue,s should throw an exception.  
	 * @throws IllegalArgumentException
	 * */
	@Test(expected = IllegalArgumentException.class) 
	public void testNullEnqueue() throws IllegalArgumentException {

		fifoQ.enqueue(null);
	}

	@After
	public void tearDown() {
	    fifoQ = null;
	    msg0 = null;
	    msg1 = null;
	    msg2 = null;
	    msg3 = null;
	}

}
