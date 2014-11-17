package message;

import java.util.Random;

/**
 * MessageString Class implements the interface Message
 * 
 * @author Johannes Sidhu
 *
 */

public class StringMessage implements Message {

	// field to represent the groupId of the message, using Long instead of int to allow a greater number of GrouIds
	private long groupID;
	// field to represent the message in this class the message is a string
	private String stringMessage; 

	/**
	 * Empty constructor
	 * Sets the grouID to -1 -- I am making an assumption,
	 * if I had the opportunity I would go back to the product owner and verify this uncertainty
	 * As to my knowledge from the requirements document there is no mention of only positive integers used 
	 *
	 * I am including an explicit empty constructor because it is better to be aware of the long -1 
	 * than letting some arbitrary number that might still be in the memory address to be the groupID 
	 *
	 * */
	public StringMessage() {
		groupID = -1;
		stringMessage = "";
	}

	/**
	 * Preferred constructor with explicitly setting the groupID
	 * @param groupID
	 * @param stringMessage
	 */
	public StringMessage(long groupID, String stringMessage) {
		this.groupID = groupID;
		this.stringMessage = stringMessage;
	}


	/**
	 * when a Message has completed processing, it's completed() method will be called
	 * To simulate the random nature of processing the message, I have included a Thread.sleep(rnd.nextInt(100)).
	 * 
	 */
	public String completed() {
		
		Random rnd = new Random();
		try {
			Thread.sleep(rnd.nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "Completed " + toString();
	}

	/**
	 * @return the groupID
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * @return the stringMessage
	 */
	public String getStringMessage() {
		return stringMessage;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @param stringMessage the stringMessage to set
	 */
	public void setStringMessage(String stringMessage) {
		this.stringMessage = stringMessage;
	}

	/**
	 * static method to create random Messages - used for testing purposes and demo the program
	 * @param the exclusive upperLimit of the groupID, strictly generates groupIds < groupIdUpperLimit.
	 * @return Message, new object of type Message is returned
	 * @throws IllegalArgumentException
	 * 
	 * */
	public static Message generateRandomMessage(long groupIdUpperLimit) throws IllegalArgumentException {

		if(groupIdUpperLimit == 0) {
			throw new IllegalArgumentException ();
		}
		
		Random rng = new Random();
		// error checking and 2^x checking removed for simplicity.
		long bits, generatedGroupID;
		do {
			bits = (rng.nextLong() << 1) >>> 1;
			generatedGroupID = bits % groupIdUpperLimit;
		} while (bits-generatedGroupID+(groupIdUpperLimit-1) < 0L);

		StringMessage newStringMsg = new StringMessage(generatedGroupID, "msg"+generatedGroupID);
		
		return newStringMsg;
	}




	/**
	 * 
	 *  Make a deep copy of the object of type Department
	 * */
	@Override
	public Object clone() throws CloneNotSupportedException {
		StringMessage clone=(StringMessage)super.clone();
		clone.groupID = this.groupID;
		clone.stringMessage = this.stringMessage;
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
				(cprToStringMsg.getStringMessage().equalsIgnoreCase(this.getStringMessage())) ){
			return true; // return true if names are equal
		}
		return false;
	}

	@Override
	public String toString() {
		
		return "Message: GroupID = " + groupID + " Message String = " + stringMessage;
	}
}