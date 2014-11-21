package gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import message.Message;

/**
 * Implementation of the Gateway interface to represent a generic Gateway and implement the method signatures as specified in the requirements document.
 * 
 * @author Johannes Sidhu
 * 
 */
public class GenericGateway implements Gateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericGateway.class);	

	public void send(Message msg) {
		
		LOGGER.info("Send " + msg.toString());
			
	}

}
