package message;

import java.util.Random;

public class ConcreteMessageFactory implements MessageFactory {

	private static final String TERMINATION_MESSAGE = "TERMINATION_MESSAGE";
	private static final String CANCELLATION_MESSAGE = "CANCELLATION_MESSAGE";

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
	
	@Override
	public Message createTerminationMessage(long groupID) throws IllegalArgumentException{
		if(groupID < 0) {
			throw new IllegalArgumentException ();
		}

		StringMessage terminationMessage = new StringMessage(groupID, TERMINATION_MESSAGE);

		return terminationMessage;
	}

	@Override
	public Message createCancellationMessage(long groupID) throws IllegalArgumentException{
		if(groupID < 0) {
			throw new IllegalArgumentException ();
		}
		
		StringMessage cancellationMessage = new StringMessage(groupID, CANCELLATION_MESSAGE);

		return cancellationMessage;
	}

}