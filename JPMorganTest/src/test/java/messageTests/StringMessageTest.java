package messageTests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;
import message.StringMessage;

public class StringMessageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
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
	public void testStringMessageEmptyConstructor() throws CloneNotSupportedException {
		StringMessage stringMessag = new StringMessage();
		assertEquals(Integer.MIN_VALUE, stringMessag.getGroupID());
	}

	@Test
	public void testClone() throws CloneNotSupportedException {
		StringMessage originalMsg = (StringMessage) msg0;
		StringMessage cloneMsg = (StringMessage) originalMsg.clone();
		originalMsg.setData("changed Message");

		assertEquals("msg0", cloneMsg.getData());
	}

	@Test
	public void testEqualsWithEqualObjectsReturnsFalse() {
		StringMessage strMsg2 = (StringMessage) msg2;
		StringMessage strMsg3 = (StringMessage) msg3;

		assertEquals(true, strMsg2.equals(strMsg3));
	}

	@Test
	public void testEqualsWithNotEqualObjectsReturnsFalse() {
		StringMessage strMsg0 = (StringMessage) msg0;
		StringMessage strMsg3 = (StringMessage) msg3;

		assertThat(false, is(strMsg0.equals(strMsg3)));
	}
	
	@Test
	public void testEqualsWithNotStringMessageAndStringMessageObject() {
		StringMessage strMsg2 = (StringMessage) msg2;

		assertThat(false, is(strMsg2.equals(new Object())));
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
	
	@Test 
	public void testGenerateRandomMessageZero() {
		thrown.expect(IllegalArgumentException.class);
		messageFactory.generateRandomMessage(0);
	}	
	
	@Test 
	public void testCompleted() {
		msg0.completed();
	}

	@Test 
	public void testHashCodeGeneratesSameHashCodeForIdenticalMessage() {
		assertEquals(msg0.hashCode(), msg0.hashCode());
	}
	
	@Test 
	public void testHashCodeGeneratesDifferentHashCodeForDifferentMessages() {
		assertThat(msg0.hashCode(), is(not(msg2.hashCode())));
	}
	
	@After
	public void tearDown() {
		messageFactory = null;
		msg0 = null;
		msg2 = null;
		msg3 = null;
	}

}