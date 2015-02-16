package messageTests;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;
import message.StringMessage;

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

	@Test
	public void testClone() throws CloneNotSupportedException {
		StringMessage originalMsg = (StringMessage) msg0;
		StringMessage cloneMsg = (StringMessage) originalMsg.clone();
		originalMsg.setData("changed Message");

		assertEquals("msg0", cloneMsg.getData());
	}

	@Test
	public void testEquals() {
		StringMessage strMsg2 = (StringMessage) msg2;
		StringMessage strMsg3 = (StringMessage) msg3;

		assertEquals(true, strMsg2.equals(strMsg3));
	}

	@Test
	public void testGenerateRandomMessageGroupID() throws IllegalArgumentException {
		StringMessage strMsgnew = (StringMessage) messageFactory.generateRandomMessage(10);
		boolean groupIDinRange = false;

		if (strMsgnew.getGroupID() <= 10) {
			groupIDinRange = true;
		}

		assertEquals(true, groupIDinRange);
	}	

	@Test
	public void testGenerateRandomMessageStringMessage() throws IllegalArgumentException  {
		StringMessage strMsgnew = (StringMessage) messageFactory.generateRandomMessage(1);
		boolean groupIDinRange = false;

		if (strMsgnew.getData().equalsIgnoreCase("msg0") == true) {
			groupIDinRange = true;
		}

		assertEquals(true, groupIDinRange);
	}	
	
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