package message;

/**
 * Interface to represent messages and declare the method signatures as specified in the requirements document.
 * 
 * @author Johannes Sidhu
 * 
 */

public interface Message extends Cloneable {

	/**
	 * From the spec sheet: "when a Message has completed processing, it's completed() method will be called"
	 * */
	public void completed();

	
	/**
	 * The clone() method for object duplication. 
	 * 
	 * NOTE: I added the method for unit testing purposes and it is good practice to provide a clone() method
	 * */
	public Object clone() throws CloneNotSupportedException;
	
}
