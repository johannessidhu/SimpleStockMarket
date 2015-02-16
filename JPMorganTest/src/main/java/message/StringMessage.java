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
 */

public class StringMessage implements Message {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringMessage.class);	
	private long groupID;
	private String data; 

	/**
	 * Empty constructor
	 * Sets the groupID to -1 -- I am making an assumption,
	 * Normally I would go back to the product owner and verify this uncertainty
	 * As to my knowledge from the requirements document there is no mention of only positive numbers used for the groupID 
	 * */
	public StringMessage() {
		groupID = -1;
		data = "";
	}

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

	public long getGroupID() {
		return groupID;
	}

	public String getData() {
		return data;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public void setData(String stringMessage) {
		this.data = stringMessage;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		StringMessage clone=(StringMessage)super.clone();
		clone.groupID = this.groupID;
		clone.data = this.data;

		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		StringMessage cprToStringMsg = null;

		if (!(obj instanceof StringMessage)) {
			return false;
		} else {
			cprToStringMsg = (StringMessage) obj;
		}

		if ((cprToStringMsg.getGroupID() == this.getGroupID()) && 
				(cprToStringMsg.getData().equalsIgnoreCase(this.getData()))) {
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Message: GroupID = " + groupID + " Message String = " + data;
	}

}