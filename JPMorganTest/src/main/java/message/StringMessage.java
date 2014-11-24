package message;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MessageString Class implements the interface Message and provides additional methods 
 * including builder methods that create and return specific StringMessage i.e TERMINATION_MESSAGE, CANCELLATION_MESSAGE and random generated messages.
 * 
 * @author Johannes Sidhu
 *
 */

public class StringMessage implements Message {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringMessage.class);	

	// field to represent the groupId of the message, using Long instead of int to allow a greater number of GrouIds
	private long groupID;

	// field to represent the message in this class the message is a string
	private String data; 

	/**
	 * Empty constructor
	 * Sets the groupID to -1 -- I am making an assumption,
	 * Normally I would go back to the product owner and verify this uncertainty
	 * As to my knowledge from the requirements document there is no mention of only positive numbers used for the groupID 
	 *
	 * I am including an explicit empty constructor because it is better to be aware of the long -1 
	 * than letting some arbitrary number that might still be in the memory address to be the groupID 
	 *
	 * */
	public StringMessage() {
		groupID = -1;
		data = "";
	}

	/**
	 * Preferred constructor with explicitly setting the groupID
	 * @param groupID
	 * @param data
	 */
	public StringMessage(long groupID, String stringMessage) {
		this.groupID = groupID;
		this.data = stringMessage;
	}

	/**
	 * when a Message has completed processing, it's completed() method will be called
	 * To simulate the random nature of processing the message, I have included a TimeUnit.MILLISECONDS.sleep(rnd.nextInt(150)).
	 * This simulates the following extract from the spec sheet: 
	 * "to perform some, potentially very time consuming, operations on messages that you send to it."
	 * 
	 */
	public void completed() {

		Random rnd = new Random();
		try {
			TimeUnit.MILLISECONDS.sleep(rnd.nextInt(150));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		LOGGER.info("Completed processing " + toString());
	}

	/**
	 * @return the groupID
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String stringMessage) {
		this.data = stringMessage;
	}

	/**
	 * 
	 *  Make a deep copy of the object of type Department
	 * */
	@Override
	public Object clone() throws CloneNotSupportedException {
		StringMessage clone=(StringMessage)super.clone();
		clone.groupID = this.groupID;
		clone.data = this.data;
		return clone;
	}

	@Override
	public boolean equals(Object o) {

		StringMessage cprToStringMsg = null;

		if(!(o instanceof StringMessage)){
			return false;
		}else {
			cprToStringMsg = (StringMessage) o;
		}

		if( (cprToStringMsg.getGroupID() == this.getGroupID()) && 
				(cprToStringMsg.getData().equalsIgnoreCase(this.getData())) ){
			return true; // return true if names are equal
		}
		return false;
	}

	@Override
	public String toString() {

		return "Message: GroupID = " + groupID + " Message String = " + data;
	}
}