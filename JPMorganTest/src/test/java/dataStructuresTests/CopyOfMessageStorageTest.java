//package dataStructuresTests;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import message.Message;
//import message.StringMessage;
//import dataSructures.MessageStorage;
//
///**
// * Unit tests for MessageStorage
// */
//public class CopyOfMessageStorageTest {
//
//	private MessageStorage msgStorage;
//	private Message msg0; 
//	private Message msg1; 
//	private Message msg2; 
//	private Message msg3; 
//	private Message msg4; 
//
//	@Before
//	public void setUp() throws Exception {
//
//		msgStorage = new MessageStorage();
//		msg0 = new StringMessage(0, "msg0");
//		msg1 = new StringMessage(1, "msg1");
//		msg2 = new StringMessage(2, "msg2");
//		msg3 = new StringMessage(2, "msg3");
//		msg4 = new StringMessage(4, "msg4");
//
//		
//		msgStorage.addMessageToStorage(msg0);
//		msgStorage.addMessageToStorage(msg1);
//		msgStorage.addMessageToStorage(msg2);
//		msgStorage.addMessageToStorage(msg3);
//		msgStorage.addMessageToStorage(msg4);
//		
//	}
//
//	/**
//	 * Remove Messages from the message Storage and the list should be in the correct sequence of messages FIFO
//	 * */
//	@Test
//	public void testSimpleRemove() {
//
//		long groupIDToBeRemoved = 2; 
//
//		// reference the removed ArrayList from the message storage
//		ArrayList<Message> removedList= msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);
//		
//		// caste the first Message to StringMessage 
//		StringMessage strMsg0 = (StringMessage)removedList.get(0);
//		
//		assertEquals("msg2", strMsg0.getStringMessage());
//	}
//
//	/**
//	 * Remove Messages from the message Storage, test the size of the ArrayList returned
//	 * expected value is 2 
//	 * */
//	@Test
//	public void testListSizeRemove() {
//
//		long groupIDToBeRemoved = 2; 
//
//		// reference the removed ArrayList from the message storage
//		ArrayList<Message> removedList= msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);
//
//		assertEquals(2, removedList.size());
//	}
//
//	/**
//	 * Remove Messages from the message Storage, test the size of the ArrayList returned
//	 * expected value is 3, after removing the list with groupID 0 
//	 * */
//	@Test
//	public void testStorageSizeRemove() {
//
//		long groupIDToBeRemoved = 0; 
//
//		System.out.println("Storage size before removing message = " + msgStorage.size());
//
//		msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);
//		assertEquals(3, msgStorage.size());
//	}
//
//	
//	/**
//	 * Remove Messages from the message Storage, but with a groupID not in the message storage
//	 * */
//	@Test
//	public void testNullRemove() {
//
//		long groupIDToBeRemoved = Integer.MAX_VALUE; 
//
//		// reference the removed ArrayList from the message storage
//		ArrayList<Message> removedList = msgStorage.removeMessagesFromStorage(groupIDToBeRemoved);
//
//		assertEquals(null, removedList);
//	}
//
//
//	/**
//	 * Add null to Message Storage, expect an IllegalArgumentException exception
//	 * @throws IllegalArgumentException
//	 * */
//	@Test(expected = IllegalArgumentException.class) 
//	public void testNullAddMessageToStorage() throws IllegalArgumentException {
//
//		msgStorage.addMessageToStorage(null);
//
//	}
//
//	/**
//	 * Add a null StringMessage to Message Storage, expect an IllegalArgumentException exception
//	 * @throws IllegalArgumentException
//	 * */
//	@Test(expected = IllegalArgumentException.class) 
//	public void testNullMessageAddMessageToStorage() throws IllegalArgumentException {
//
//		StringMessage msgTemp = new StringMessage();
//		msgTemp = null;
//		msgStorage.addMessageToStorage(msgTemp);
//
//	}
//
//	@After
//	public void tearDown() {
//	    msgStorage = null;
//	    msg0 = null;
//	    msg1 = null;
//	    msg2 = null;
//	    msg3 = null;
//	    msg4 = null;
//	}
//
//}
