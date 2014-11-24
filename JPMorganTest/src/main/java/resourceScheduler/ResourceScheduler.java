/**
 * 
 */
package resourceScheduler;

import message.Message;
import gateway.GenericGateway;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import concurrency.ConsumerProducerFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dataSructures.MessageStorage;

/**
 * @author Johannes Sidhu
 *
 */
public class ResourceScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceScheduler.class);	
	private static final int DEFAULT_NUMBER_OF_PRODUCERS = 1;	
	private static final int DEFAULT_NUMBER_OF_CONSUMERS = 1;	


	protected static final GenericGateway GENERIC_GATEWAY = new GenericGateway();

	/**
	 * Utility method for checking input from the Apache configuration file
	 * @param string
	 * @return true if the string entered is not null, is not empty and does not consist of only white space 
	 */
	public static boolean stringChecker(final String string) {  
		return (string != null && !string.isEmpty() && !string.trim().isEmpty());  
	}

	/**
	 * Utility method to check if a string is a valid number for configuring the ResourceScheduler
	 * @param inputNumberString
	 * @return true if the input string is a valid number (for configuring the ResourceScheduler)
	 */
	public static boolean validConfigurationFileNumberEntry(String inputNumberString){

		if(!stringChecker(inputNumberString)) {
			LOGGER.error("The input string is either empty or null or only consist of empty space.");
			return false;
		}
		else {
			try {

				if(Integer.parseInt(inputNumberString) < 0) {
					LOGGER.error("For this application the assumption is all configuration numbers must be greater or equal to 0.");
					return false;			}

			} catch (NumberFormatException e) {
				LOGGER.error("input number in the loaded configuration file is NaN.");
				return false;
			}
		}
		return true;
	}



	/**
	 * Entry point to the application, it can take as argument one Apache configuration file, 
	 * otherwise it loads the default xml configuration file in the directory resources/META-INF
	 * @param args
	 */
	public static void main(final String[] args) {

		final ResourceScheduler application = new ResourceScheduler();

		LOGGER.info("Starting SchedulerTests");

		XMLConfiguration appConfig = null;

		try{
			// if no arguments passed then load the default configuration file.
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
			LOGGER.error("Generic exception caught when reading configuration file: " + ex.toString());
			System.exit(0);
		}

		try {
			application.start(appConfig);
		}
		catch (final Exception e) {
			LOGGER.error("ResourceScheduler failed", e);
		}
	}

	/**
	 * Runs the resourceScheduler and the demo
	 * @param args
	 */
	private void start(XMLConfiguration appConfig) throws Exception{

		int numberOFProducers = DEFAULT_NUMBER_OF_PRODUCERS;
		int numberOFConsumers = DEFAULT_NUMBER_OF_CONSUMERS;

		/*
		 * This block checks for the input from the loaded configuration file
		 */
		if(validConfigurationFileNumberEntry(appConfig.getString("ResourceScheduler.numberOFProducers")) && 
				validConfigurationFileNumberEntry(appConfig.getString("ResourceScheduler.numberOFConsumers"))) {
	
			numberOFProducers = Integer.parseInt(appConfig.getString("ResourceScheduler.numberOFProducers"));
			numberOFConsumers = Integer.parseInt(appConfig.getString("ResourceScheduler.numberOFConsumers"));

		}
		
		// for the Resource Scheduler to execute it must have at least one producer and one consumer
		if(numberOFConsumers <= 0 || numberOFProducers <= 0) {
			LOGGER.error("Please specify at least 1 Consumer and Producer in the configuration file. Default setup of 1 producer and 2 Consumers initated");

			numberOFProducers = DEFAULT_NUMBER_OF_PRODUCERS;
			numberOFConsumers = DEFAULT_NUMBER_OF_CONSUMERS;

		}

		BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
		MessageStorage messageStorage = new MessageStorage();

		ConsumerProducerFactory consumerProducerFactory = new ConsumerProducerFactory(numberOFProducers, numberOFConsumers); 

		// Initiate Producers and start the threads
		ExecutorService producerExecutor = consumerProducerFactory.providesProducerExecutor();

		for (int i = 0; i < numberOFProducers; i++) {

			Runnable producer = consumerProducerFactory.providesProducer(queue, messageStorage, appConfig);
			producerExecutor.execute(producer);

		}

		producerExecutor.shutdown();

		// Initiate Consumers and start the threads
		ExecutorService consumerExecutor = consumerProducerFactory.providesConsumerExecutor();

		for (int i = 0; i < numberOFConsumers; i++) {

			Runnable consumer = consumerProducerFactory.providesConsumer(queue, messageStorage, GENERIC_GATEWAY);
			consumerExecutor.execute(consumer);

		}

		consumerExecutor.shutdown();

		while (!consumerExecutor.isTerminated() || !producerExecutor.isTerminated() ) {
		}

		LOGGER.info("Application finished.");

	}

}
