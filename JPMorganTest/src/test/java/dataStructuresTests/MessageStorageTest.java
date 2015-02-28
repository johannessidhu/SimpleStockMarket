package dataStructuresTests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import message.Message;
import message.StringMessage;
import dataSructures.MessageStorage;

public class MessageStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
	private MessageStorage msgStorage;
	private Message msg0; 
	private Message msg1; 
	private Message msg2; 
	private Message msg3; 
	private Message msg4; 
	
	@Before
	public void setUp() throws Exception {
		msgStorage = new MessageStorage();
		msg0 = new StringMessage(0, "msg0");
		msg1 = new StringMessage(1, "msg1");
		msg2 = new StringMessage(2, "msg2");
		msg3 = new StringMessage(2, "msg3");
		msg4 = new StringMessage(4, "msg4");

		msgStorage.addMessageToStorage(msg0);
		msgStorage.addMessageToStorage(msg1);
		msgStorage.addMessageToStorage(msg2);
		msgStorage.addMessageToStorage(msg3);
		msgStorage.addMessageToStorage(msg4);
	}

	@Test
	public void testSimpleRemove() throws InterruptedException {
		long groupIDToBeRemoved = 2; 
		LinkedBlockingQueue<Message> removedList = msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);
		StringMessage strMsg0 = (StringMessage) removedList.take();

		assertEquals("msg2", strMsg0.getData());
	}

	@Test
	public void testListSizeRemove() {
		long groupIDToBeRemoved = 2; 
		LinkedBlockingQueue<Message> removedList= msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);

		assertEquals(2, removedList.size());
	}

	@Test
	public void testStorageSizeRemove() {
		long groupIDToBeRemoved = 0; 
		System.out.println("Storage size before removing message = " + msgStorage.size());
		msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);

		assertEquals(3, msgStorage.size());
	}

	@Test
	public void testNullRemove() {
		long groupIDToBeRemoved = Integer.MAX_VALUE; 
		LinkedBlockingQueue<Message> removedList = msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);

		assertEquals(null, removedList);
	}

	@Test
	public void testNullAddMessageToStorage() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
		msgStorage.addMessageToStorage(null);
	}

	@Test
	public void testNullMessageAddMessageToStorage() throws IllegalArgumentException {
		StringMessage msgTemp = new StringMessage();
		msgTemp = null;
        thrown.expect(IllegalArgumentException.class);
		msgStorage.addMessageToStorage(msgTemp);
	}

	@After
	public void tearDown() {
		msgStorage = null;
		msg0 = null;
		msg1 = null;
		msg2 = null;
		msg3 = null;
		msg4 = null;
	}

}