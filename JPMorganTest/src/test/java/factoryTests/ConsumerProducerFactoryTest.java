package factoryTests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import message.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import concurrency.ConsumerProducerFactory;
import dataSructures.MessageStorage;

public class ConsumerProducerFactoryTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final static int NUMBER_OF_PRODUCERS = 1;
	private final static int NUMBER_OF_CONSUMERS = 2;
	private ConsumerProducerFactory consumerProducerFactory; 

	private BlockingQueue<Message> queue;
	private MessageStorage messageStorage;

	@Before
	public void setUp() throws Exception {
		consumerProducerFactory = new ConsumerProducerFactory(NUMBER_OF_PRODUCERS, NUMBER_OF_CONSUMERS);
		queue = new LinkedBlockingQueue<Message>();
		messageStorage = new MessageStorage();
	}

	@Test
	public void testProvideProducerExecutorType() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.provideProducerExecutor();

		assertEquals(ThreadPoolExecutor.class, providedES.getClass());
	}

	@Test
	public void testProvideProducerExecutorCorePoolSize() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.provideProducerExecutor();

		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}

	@Test
	public void testProvideProducerExecutorCorePoolZeroSize() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(0, 0);

		thrown.expect(IllegalArgumentException.class);
		consumerProducerFactory.provideProducerExecutor();
	}

	@Test 
	public void testProvideProducerExecutorCorePoolNegativeSize() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(-1, 0);

		thrown.expect(IllegalArgumentException.class);
		consumerProducerFactory.provideProducerExecutor();
	}

	@Test
	public void testProvideConsumerExecutorType() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.provideConsumerExecutor();

		assertEquals(ThreadPoolExecutor.class, providedES.getClass());
	}

	@Test
	public void testProvideConsumerExecutorCorePoolSize() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.provideConsumerExecutor();

		assertEquals(NUMBER_OF_CONSUMERS, providedES.getCorePoolSize());
	}

	@Test
	public void testProvideConsumerExecutorCorePoolZeroSize() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(0, 0);

		thrown.expect(IllegalArgumentException.class);
		consumerProducerFactory.provideConsumerExecutor();
	}

	@Test
	public void testProvideConsumerExecutorCorePoolNegativeSize() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(0, -1);

		thrown.expect(IllegalArgumentException.class);
		consumerProducerFactory.provideConsumerExecutor();
	}

	@Test
	public void testProvideProducer() {		
		consumerProducerFactory.provideProducer(queue, messageStorage, null);
	}

	@Test
	public void testProvideConsumer() {		
		consumerProducerFactory.provideConsumer(queue, messageStorage, null);
	}
	
	@After
	public void tearDown() {
		consumerProducerFactory = null;
		queue = null;
		messageStorage = null;
	}

}