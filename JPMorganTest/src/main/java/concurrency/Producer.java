package concurrency;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utilities.Utility;
import dataSructures.MessageStorage;
import message.Message;
import message.StringMessage;

import java.util.Random;

/**
 * 
 * Class represents the income channel for the messages
 * It randomly creates messages and adds them to a BlockingQueue
 * It also utilizes a random thread sleep to simulate the random throughput of messages
 * 
 * It uses the Poison pill design to trigger the end of all messages 
 * It also implements the Termination message for extra credit
 * 
 * @author Johannes Sidhu
 * */
public class Producer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);	

	// POISON added as the very last message in the process, when no more messages are going to be received from any group, to terminate all threads
	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 

	protected BlockingQueue<Message> queue = null;

	protected MessageStorage messageStorage = null;
	
	protected XMLConfiguration appConfig = null;


	public Producer(BlockingQueue<Message> queue, MessageStorage messageStorage, XMLConfiguration appConfig) {
		this.queue = queue;
		this.messageStorage = messageStorage;
		this.appConfig = appConfig;
	}

	/**
	 * run() method for the producer which simulates the adding of messages to the queue and MessageStorage
	 * This method generated random messages and adds them to the afore mentioned data structures 
	 * with groupIDs from 0 to Long.MAX_VALUE
	 * 
	 * */
	public void run() {
		try {

			int numberOFMessages = 0;
			int intervalOfTerminationMessages = 0;
			int intervalOfCancellationMessages = 0;
			int intervalOfMessages = 0;

			
			/*
			 * This block checks for the input from the loaded configuration file
			 */
			if(Utility.validConfigurationFileNumberEntry(appConfig.getString("Producer.numberOFMessagesToBeProduced")) && 
					Utility.validConfigurationFileNumberEntry(appConfig.getString("Producer.intervalOfMessages"))) {
		
				numberOFMessages = Integer.parseInt(appConfig.getString("Producer.numberOFMessagesToBeProduced"));
				intervalOfMessages = Integer.parseInt(appConfig.getString("Producer.intervalOfMessages"));

			}

			/*
			 * This block also checks for the input from the loaded configuration file
			 * I split it into 2 blocks for better readability, grouped by aspect
			 */
			if(Utility.validConfigurationFileNumberEntry(appConfig.getString("Producer.intervalOfTerminationMessages")) && 
					Utility.validConfigurationFileNumberEntry(appConfig.getString("Producer.intervalOfCancellationMessages"))) {
		
				intervalOfTerminationMessages = Integer.parseInt(appConfig.getString("Producer.intervalOfTerminationMessages"));
				intervalOfCancellationMessages = Integer.parseInt(appConfig.getString("Producer.intervalOfCancellationMessages"));

			}
			
			Random rnd = new Random();
			// I decided to run the producer for x iterations to allow for testing and for demoing the application
			for(int x = 1; x <= numberOFMessages; x++) {

				// the randomly generated Message object
				StringMessage newMessage = (StringMessage) StringMessage.generateRandomMessage(2);
				newMessage.setStringMessage("msg" + x);
				// add the new Message to the queue

				// Extra credit for Termination Message for a particular groupID - this if statement turns a newMessage into a termination message
				if(x % intervalOfTerminationMessages == 0) {
					newMessage = (StringMessage) StringMessage.createTerminationMessage(newMessage.getGroupID());
				}

				// Extra credit for Cancellation Message for a particular groupID - this if statement turns a newMessage into a cancellation message
				if(x % intervalOfCancellationMessages == 0) {
					newMessage = (StringMessage) StringMessage.createCancellationMessage(newMessage.getGroupID());
				}
				
				
				// add the message to the FIFO queue 
				queue.put(newMessage);

				// add the new Message to the messageStorage
				messageStorage.addMessageToStorage(newMessage);

				// Log the information 
				LOGGER.info("Added: " + newMessage);

				// simulate a random intervals of messages being added to the queue, used TimeUnit instead of Thread.sleep(), for readability 
				TimeUnit.MILLISECONDS.sleep(rnd.nextInt(intervalOfMessages));
			}

			// Signal the end of the transmission of all message, I added this to allow for testing and for demoing the application
			queue.put(POISON);

		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException " + e);
			e.printStackTrace();
		}
	}
	
}