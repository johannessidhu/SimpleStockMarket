package message;

public interface MessageFactory {
	
	/**
	 * Method to generate random Messages - used for testing purposes and demo the program
	 * @param the exclusive upperLimit of the groupID, strictly generates groupIds < groupIdUpperLimit.
	 * @return Message, new object of type Message is returned
	 * @throws IllegalArgumentException
	 * 
	 * */
	public Message generateRandomMessage(long groupIdUpperLimit) throws IllegalArgumentException;

	/**
	 * Method to generate random Messages - used for testing purposes and demo the program,
	 * it uses Long.MAX_VALUE as the upper limit
	 * @return Message, new object of type Message is returned
	 * @throws IllegalArgumentException
	 * 
	 * */
	public Message generateRandomMessage();
	
	/**
	 * This method returns a cancellation message for a particular groupID
	 * @param groupID, of the group for which the Cancellation message should be created
	 * @return cancellation Message
	 * 
	 * */
	public Message createCancellationMessage(long groupID);
	
	/**
	 * This method returns a termination message for a particular groupID
	 * @param groupID, of the group for which the Termination message should be created
	 * @return termination Message
	 * 
	 * */
	public Message createTerminationMessage(long groupID);

}
