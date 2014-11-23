package concurrency;

import gateway.GenericGateway;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.XMLConfiguration;

import dataSructures.MessageStorage;
import message.Message;

public class ConsumerProducerFactory {

	private int numberOFProducers;
	private int numberOFConsumers;

	/**
	 * 
	 * @param numberOFProducers
	 * @param numberOFConsumers
	 */
	public ConsumerProducerFactory(int numberOFProducers, int numberOFConsumers) {
		this.numberOFProducers = numberOFProducers;
		this.numberOFConsumers = numberOFConsumers;
	}

	/**
	 * 
	 * @param numberOFProducers
	 * @return
	 */
	public ExecutorService providesProducerExecutor() {
		return Executors.newFixedThreadPool(numberOFProducers);
	}

	/**
	 * 
	 * @param queue
	 * @param messageStorage
	 * @param appConfig
	 * @return Runnable Producer
	 */
	public Runnable providesProducer(BlockingQueue<Message> queue, MessageStorage messageStorage, XMLConfiguration appConfig) {
		return new Producer(queue, messageStorage, appConfig);
	}

	/**
	 * 
	 * @param numberOFConsumers
	 * @return
	 */
	public ExecutorService providesConsumerExecutor() {
		return Executors.newFixedThreadPool(numberOFConsumers);
	}

	/**
	 * 
	 * @param queue
	 * @param messageStorage
	 * @param genericGateway
	 * @return Runnable Consumer
	 */
	public Runnable providesConsumer(BlockingQueue<Message> queue, MessageStorage messageStorage, GenericGateway genericGateway) {
		return new Consumer(queue, messageStorage, genericGateway);
	}

	/**
	 * @return the numberOFProducers
	 */
	public int getNumberOFProducers() {
		return numberOFProducers;
	}

	/**
	 * @return the numberOFConsumers
	 */
	public int getNumberOFConsumers() {
		return numberOFConsumers;
	}

	/**
	 * @param numberOFProducers the numberOFProducers to set
	 */
	public void setNumberOFProducers(int numberOFProducers) {
		this.numberOFProducers = numberOFProducers;
	}

	/**
	 * @param numberOFConsumers the numberOFConsumers to set
	 */
	public void setNumberOFConsumers(int numberOFConsumers) {
		this.numberOFConsumers = numberOFConsumers;
	}

	

}
