package messageTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;
import message.StringMessage;


/**
 * Unit tests for StringMessage
 */
public class StringMessageTest {

	private Message msg0; 
	private Message msg2; 
	private Message msg3; 
	
	private MessageFactory messageFactory = null; 


	@Before
	public void setUp() throws Exception {

		msg0 = new StringMessage(0, "msg0");
		msg2 = new StringMessage(2, "msg2");
		msg3 = new StringMessage(2, "msg2");
		
		messageFactory = new ConcreteMessageFactory(); 


	}

	/**
	 * Test the clone() method
	 * @throws CloneNotSupportedException
	 * */
	@Test
	public void testClone() throws CloneNotSupportedException {

		// caste msg0 to StringMessage
		StringMessage originalMsg = (StringMessage) msg0;

		// use the clone() method
		StringMessage cloneMsg = (StringMessage) originalMsg.clone();

		// change the message of the original StringMessage to something different

		originalMsg.setData("changed Message");
		// if it was just a shallow copy than cloneMsg.getStringMessage would also change to "changed Message"		
		assertEquals("msg0", cloneMsg.getData());
	}


	/**
	 * Test the equals() method
	 * */
	@Test
	public void testEquals() {

		// caste to StringMessage
		StringMessage strMsg2 = (StringMessage) msg2;
		StringMessage strMsg3 = (StringMessage) msg3;

		assertEquals(true, strMsg2.equals(strMsg3));
	}

	/**
	 * Test static method to generate random messages objects.
	 * */
	@Test
	public void testGenerateRandomMessageGroupID() throws IllegalArgumentException {

		StringMessage strMsgnew = (StringMessage) messageFactory.generateRandomMessage(10);

		boolean groupIDinRange = false;
		if(strMsgnew.getGroupID() <= 10) {
			groupIDinRange = true;
		}

		assertEquals(true, groupIDinRange);
	}	

	/**
	 * Test static method to generate random messages objects, the creation of the StringMessge field
	 * @throws IllegalArgumentException
	 * */
	@Test
	public void testGenerateRandomMessageStringMessage() throws IllegalArgumentException  {

		StringMessage strMsgnew = (StringMessage) messageFactory.generateRandomMessage(1);
	
		boolean groupIDinRange = false;
		if(strMsgnew.getData().equalsIgnoreCase("msg0") == true) {
			groupIDinRange = true;
		}

		assertEquals(true, groupIDinRange);
	}	
	

	/**
	 * Test static method to generate random messages objects.
	 * @throws IllegalArgumentException
	 * */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class) 
	public void testGenerateRandomMessageZero() {

		StringMessage strMsgnew = (StringMessage) messageFactory.generateRandomMessage(0);
	}	

	@After
	public void tearDown() {
		messageFactory = null;
		msg0 = null;
		msg2 = null;
		msg3 = null;
	}

}
