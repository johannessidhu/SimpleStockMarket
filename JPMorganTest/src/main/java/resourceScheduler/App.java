/**
 * 
 */
package resourceScheduler;

import message.Message;
import message.StringMessage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import concurrency.Consumer;
import concurrency.Producer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dataSructures.MessageStorage;

/**
 * @author Johannes Sidhu
 *
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);	

	/**
	 * Entry point to the application
	 * @param args
	 */
	public static void main(final String[] args) {

		final App application = new App();

		LOGGER.info("Starting App");
		
		XMLConfiguration appConfig = null;

		try{
			// if no arguments passed then load the default configurtion file.
			if(args.length == 0){
				appConfig = new XMLConfiguration("META-INF/configuration.xml");
				LOGGER.info("Default configuration was loaded.");
			}else if(args.length == 1){ 
				appConfig = new XMLConfiguration(args[0]);
				LOGGER.info("Provided " + args[0]+ "configuration file was loaded.");
			}
		}catch(ConfigurationException cex){
			LOGGER.error("Could not read the xml file." + cex.toString());
			System.exit(0);
		}catch(Exception ex){
			LOGGER.error("Exception when reading configuration file: " + ex.toString());
			System.exit(0);
		}
		
		try {
			application.start(appConfig);
		}
		catch (final Exception e) {
			LOGGER.error("App failed", e);
		}
	}


	/**
	 * Runs the resourceScheduler and the demo
	 * @param args
	 */
	private void start(XMLConfiguration appConfig) throws Exception{
		
		LOGGER.info(appConfig.getString("test"));
		
		Message[] incommingMessages = {new StringMessage(2, "message2"),
				new StringMessage(2, "message2"),
				new StringMessage(3, "message3"),
				new StringMessage(2, "message2")};
		
	
		BlockingQueue queue = new ArrayBlockingQueue(1024);
		MessageStorage messageStorage = new MessageStorage();

//		Producer producer = new Producer(queue);
//		Consumer consumer = new Consumer(queue);
//		Consumer consumer1 = new Consumer(queue);

		Producer producer = new Producer(queue, messageStorage);
		Consumer consumer = new Consumer(queue, messageStorage);
		Consumer consumer1 = new Consumer(queue, messageStorage);
		
		new Thread(producer).start();
		new Thread(consumer).start();
		new Thread(consumer1).start();
		
//		new Thread(producer).start();
//		new Thread(consumer).start();
//		new Thread(consumer1).start();

		Thread.sleep(4000);
		System.out.println("End");
		
		System.out.println(queue.size());
		
	}
	
}
