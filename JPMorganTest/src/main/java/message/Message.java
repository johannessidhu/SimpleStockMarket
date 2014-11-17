package message;

/**
 * Interface to represent generic messages and declare the method signatures as specified in the requirements document.
 * 
 * @author Johannes Sidhu
 * 
 */

public interface Message extends Cloneable {

	public String completed();

	public Object clone() throws CloneNotSupportedException;
	
//	public Message generateRandomMessage(long groupIdUpperLimit);

}
