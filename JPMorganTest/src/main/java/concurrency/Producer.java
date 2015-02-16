package concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.Utility;
import dataSructures.MessageStorage;
import message.ConcreteMessageFactory;
import message.MessageFactory;
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
public class Producer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);	
	// POISON added as the very last message in the process, when no more messages are going to be received from any group, to terminate all threads
	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 
	private MessageFactory messageFactory = null; 

	protected BlockingQueue<Message> queue = null;
	protected MessageStorage messageStorage = null;
	protected XMLConfiguration appConfig = null;

	public Producer(BlockingQueue<Message> queue, MessageStorage messageStorage, XMLConfiguration appConfig) {
		this.queue = queue;
		this.messageStorage = messageStorage;
		this.appConfig = appConfig;
		this.messageFactory = new ConcreteMessageFactory();
	}

	/**
	 * run() method for the producer which simulates the adding of messages to the queue and MessageStorage
	 * This method generated random messages and adds them to the afore mentioned data structures 
	 * with groupIDs from 0 to Long.MAX_VALUE
	 * */
	public void run() {
		try {
			int numberOFMessages = 0;
			int intervalOfTerminationMessages = 0;
			int intervalOfCancellationMessages = 0;
			int intervalOfMessages = 0;
			long upperBoundForGroupID = 0;
			
			if (Utility.isValidConfigurationFileIntegerEntry(appConfig.getString("Producer.numberOFMessagesToBeProduced")) && 
					Utility.isValidConfigurationFileIntegerEntry(appConfig.getString("Producer.intervalOfMessages"))) {
				numberOFMessages = Integer.parseInt(appConfig.getString("Producer.numberOFMessagesToBeProduced"));
				intervalOfMessages = Integer.parseInt(appConfig.getString("Producer.intervalOfMessages"));
			}

			if (Utility.isValidConfigurationFileIntegerEntry(appConfig.getString("Producer.intervalOfTerminationMessages")) && 
					Utility.isValidConfigurationFileIntegerEntry(appConfig.getString("Producer.intervalOfCancellationMessages"))) {
				intervalOfTerminationMessages = Integer.parseInt(appConfig.getString("Producer.intervalOfTerminationMessages"));
				intervalOfCancellationMessages = Integer.parseInt(appConfig.getString("Producer.intervalOfCancellationMessages"));
			}

			if (Utility.isValidConfigurationFileLongEntry(appConfig.getString("Producer.upperBoundForGroupID"))) {
				upperBoundForGroupID = Long.parseLong(appConfig.getString("Producer.upperBoundForGroupID"));
			}
			
			Random rnd = new Random();
			// I decided to run the producer for x iterations to allow for testing and for demoing the application
			for(int x = 1; x <= numberOFMessages; x++) {
				Message newMessage =  messageFactory.generateRandomMessage(upperBoundForGroupID);

				if(x % intervalOfTerminationMessages == 0) {
					newMessage = (StringMessage) messageFactory.createTerminationMessage(newMessage.getGroupID());
				}

				if(x % intervalOfCancellationMessages == 0) {
					newMessage = (StringMessage) messageFactory.createCancellationMessage(newMessage.getGroupID());
				}

				queue.put(newMessage);
				messageStorage.addMessageToStorage(newMessage);
				LOGGER.info("Added: " + newMessage);
				// simulate a random intervals of messages being added to the queue, used TimeUnit instead of Thread.sleep(), for readability 
				TimeUnit.MILLISECONDS.sleep(rnd.nextInt(intervalOfMessages));
			}

			queue.put(POISON);

		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException " + e);
			e.printStackTrace();
		}
	}

}