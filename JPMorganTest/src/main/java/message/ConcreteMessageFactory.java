package message;

import java.util.Random;

public class ConcreteMessageFactory implements MessageFactory {

	private static final String TERMINATION_MESSAGE = "TERMINATION_MESSAGE";

	private static final String CANCELLATION_MESSAGE = "CANCELLATION_MESSAGE";


	/**
	 * Empty Constructor
	 */
	public ConcreteMessageFactory() {
	}

	/**
	 * Method to create random Messages - used for testing purposes and demo the program
	 * @param the exclusive upperLimit of the groupID, strictly generates groupIds < groupIdUpperLimit.
	 * @return Message, new object of type Message is returned
	 * @throws IllegalArgumentException
	 * 
	 * */
	@Override
	public Message generateRandomMessage(long groupIdUpperLimit) throws IllegalArgumentException {

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
	 * Method to create random Messages - used for testing purposes and demo the program,
	 * it uses Long.MAX_VALUE as the upper limit
	 * @return Message, new object of type Message is returned
	 * @throws IllegalArgumentException
	 * 
	 * */
	@Override
	public Message generateRandomMessage(){

		long groupIdUpperLimit = Long.MAX_VALUE;

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
	 * This method returns a termination message for a particular groupID
	 * @param groupID, of the group for which the Termination message should be created
	 * @return termination Message
	 * @throws IllegalArgumentException
	 * */
	@Override
	public Message createTerminationMessage(long groupID) throws IllegalArgumentException{

		if(groupID < 0) {
			throw new IllegalArgumentException ();
		}

		StringMessage terminationMessage = new StringMessage(groupID, TERMINATION_MESSAGE);

		return terminationMessage;
	}

	/**
	 * This method returns a cancellation message for a particular groupID
	 * @param groupID, of the group for which the Cancellation message should be created
	 * @return cancellation Message
	 * @throws IllegalArgumentException
	 * */
	@Override
	public Message createCancellationMessage(long groupID) throws IllegalArgumentException{

		if(groupID < 0) {
			throw new IllegalArgumentException ();
		}
		
		StringMessage cancellationMessage = new StringMessage(groupID, CANCELLATION_MESSAGE);

		return cancellationMessage;
	}
}
