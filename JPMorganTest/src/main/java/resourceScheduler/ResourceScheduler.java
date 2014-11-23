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

	protected static final GenericGateway GENERIC_GATEWAY = new GenericGateway();

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
		
		int numberOFProducers = appConfig.getInt("ResourceScheduler.numberOFProducers");
		int numberOFConsumers = appConfig.getInt("ResourceScheduler.numberOFConsumers");
								
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
