package message;

public interface MessageFactory {

	public Message generateRandomMessage(long groupIdUpperLimit) throws IllegalArgumentException;
	public Message generateRandomMessage();
	public Message createCancellationMessage(long groupID);
	public Message createTerminationMessage(long groupID);
	
}
