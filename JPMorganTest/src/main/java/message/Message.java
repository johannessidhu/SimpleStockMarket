package message;

/**
 * Interface to represent messages and declare the method signatures as specified in the requirements document.
 * @author Johannes Sidhu
 */

public interface Message extends Cloneable {

	public void completed();
	public long getGroupID();
	public String getData();
	public Object clone() throws CloneNotSupportedException;
	
}