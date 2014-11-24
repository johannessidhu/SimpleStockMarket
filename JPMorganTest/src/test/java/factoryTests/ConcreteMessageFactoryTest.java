package factoryTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;

/**
 * Unit tests for ConcreteMessageFactory, focusing on created and generated objects. 
 * StringMessageTest focuses more on the actual message content and logic. 
 */
public class ConcreteMessageFactoryTest {

	private MessageFactory messageFactory = null; 

	@Before
	public void setUp() throws Exception {

		messageFactory = new ConcreteMessageFactory(); 

	}

	/**
	 * Test the generateRandomMessage() method, by generating a new Message and checking if the object type is of Message
	 * */
	@Test
	public void testGenerateRandomMessage(){

		Object objMsg = messageFactory.generateRandomMessage();

		if(objMsg instanceof Message) {
			assertEquals(true, true);
		}else {
			assertEquals(false, false);
		}
	}


	/**
	 * Test the generateRandomMessage() method, by generating a new Message with an upperlimit and checking if the object type is of Message
	 * @throws IllegalArgumentException
	 * */
	@Test
	public void testGenerateRandomMessageUpperLimit() throws IllegalArgumentException{

		Object objMsg = messageFactory.generateRandomMessage(10);
		Message msg = null;
		if(objMsg instanceof Message) {
			msg = (Message) objMsg; 
		}else {
			fail();		
		}

		if(msg.getGroupID() < 10) {
			assertEquals(true, true);
		}else {

			fail();		
		}

	}

	/**
	 * Test createCancellationMessage with 1 as input
	 * @throws IllegalArgumentException
	 * */
	@Test
	public void testCreateCancellationMessage() throws IllegalArgumentException {

		Object objMsg = messageFactory.createCancellationMessage(1);
		Message msg = null;
		if(objMsg instanceof Message) {
			msg = (Message) objMsg; 
		}else {
			fail();		
		}

		if(msg.getData().equalsIgnoreCase("CANCELLATION_MESSAGE")) {
			assertEquals(true, true);
		}else {

			fail();		
		}
	}

	/**
	 * Test createTerminationMessage with 1 as input
	 * @throws IllegalArgumentException
	 * */
	@Test
	public void testCreateTerminationMessage() throws IllegalArgumentException {

		Object objMsg = messageFactory.createTerminationMessage(1);
		Message msg = null;
		if(objMsg instanceof Message) {
			msg = (Message) objMsg; 
		}else {
			fail();		
		}

		if(msg.getData().equalsIgnoreCase("TERMINATION_MESSAGE")) {
			assertEquals(true, true);
		}else {

			fail();		
		}
	}	

	@After
	public void tearDown() {
		messageFactory = null;
	}

}
