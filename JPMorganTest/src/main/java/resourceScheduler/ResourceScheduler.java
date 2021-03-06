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
import utilities.ConfigurationFileValidator;
import dataSructures.MessageStorage;

public class ResourceScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceScheduler.class);	
	protected static final GenericGateway GENERIC_GATEWAY = new GenericGateway();

	/**
	 * Entry point to the application, it can take as argument an Apache configuration file, 
	 * otherwise it loads the default xml configuration file in the directory resources/META-INF
	 * @param args
	 */
	public static void main(final String[] args) {
		final ResourceScheduler application = new ResourceScheduler();
		LOGGER.info("Starting Resource Scheduler");
		XMLConfiguration appConfig = null;

		try {
			if (args != null && args.length == 1) { 
				appConfig = new XMLConfiguration(args[0]);
				LOGGER.info("Provided " + args[0]+ "configuration file was loaded.");
			} else {
				appConfig = new XMLConfiguration("META-INF/configuration.xml");
				LOGGER.info("Default configuration was loaded.");
			}
		} catch (ConfigurationException cex) {
			LOGGER.error("Could not read the xml file." + cex.toString());
			System.exit(0);
		} catch(Exception ex) {
			LOGGER.error("Generic exception caught when reading configuration file: " + ex.toString());
			System.exit(0);
		}

		try {
			application.start(appConfig);
		} catch (final Exception e) {
			LOGGER.error("Resource Scheduler failed", e);
		}
	}

	private void start(XMLConfiguration appConfig) throws Exception{
		int numberOFProducers = 0;
		int numberOFConsumers = 0;

		if (ConfigurationFileValidator.isValidConfigurationFileIntegerEntry(appConfig.getString("ResourceScheduler.numberOFProducers")) && 
				ConfigurationFileValidator.isValidConfigurationFileIntegerEntry(appConfig.getString("ResourceScheduler.numberOFConsumers"))) {
			numberOFProducers = Integer.parseInt(appConfig.getString("ResourceScheduler.numberOFProducers"));
			numberOFConsumers = Integer.parseInt(appConfig.getString("ResourceScheduler.numberOFConsumers"));
		}

		if (numberOFConsumers <= 0 || numberOFProducers <= 0) {
			LOGGER.error("Please specify at least 1 Consumer and 1 Producer in the configuration file. "
					+ "Exiting Resource Scheduler with System.exit(0).");
			System.exit(0);
		}

		BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
		MessageStorage messageStorage = new MessageStorage();
		ConsumerProducerFactory consumerProducerFactory = new ConsumerProducerFactory(numberOFProducers, numberOFConsumers); 
		ExecutorService producerExecutor = consumerProducerFactory.provideProducerExecutor();

		for (int i = 0; i < numberOFProducers; i++) {
			Runnable producer = consumerProducerFactory.provideProducer(queue, messageStorage, appConfig);
			producerExecutor.execute(producer);
		}

		producerExecutor.shutdown();
		ExecutorService consumerExecutor = consumerProducerFactory.provideConsumerExecutor();

		for (int i = 0; i < numberOFConsumers; i++) {
			Runnable consumer = consumerProducerFactory.provideConsumer(queue, messageStorage, GENERIC_GATEWAY);
			consumerExecutor.execute(consumer);
		}

		consumerExecutor.shutdown();

		while (!consumerExecutor.isTerminated() || !producerExecutor.isTerminated()) {
		}

		LOGGER.info("Resource Scheduler finished.");
	}

}