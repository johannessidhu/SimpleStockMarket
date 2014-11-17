package gateway;

import message.Message;

/**
 * Interface to represent generic messages and declare the method signatures as specified in the requirements document.
 * 
 * @author Johannes Sidhu
 * 
 */

public interface Gateway {
	
	public void send(Message msg);

}
