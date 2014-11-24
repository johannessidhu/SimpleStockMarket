package factoryTests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadPoolExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import concurrency.ConsumerProducerFactory;

/**
 * Unit tests for StringMessage
 */
public class ConsumerProducerFactoryTest {

	private final static int NUMBER_OF_PRODUCERS = 1;
	private final static int NUMBER_OF_CONSUMERS = 2;
	
	private ConsumerProducerFactory consumerProducerFactory; 

	@Before
	public void setUp() throws Exception {

		consumerProducerFactory = new ConsumerProducerFactory(NUMBER_OF_PRODUCERS, NUMBER_OF_CONSUMERS);
	}

	/**
	 * Test the providesProducerExecutor() method, returns the expected type.
	 * */
	@Test
	public void providesProducerExecutorTypeTest() {		
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();
		assertEquals(ThreadPoolExecutor.class, providedES.getClass());
	}

	/**
	 * Test the providesProducerExecutor() method, returns the expected core pool size.
	 * */
	@Test
	public void providesProducerExecutorCorePoolSizeTest() {		
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();
		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}

	/**
	 * Test the providesProducerExecutor() method
	 * Throws IllegalArgumentException if zero is defined for the core pool size
	 * @throws IllegalArgumentException
	 * */
	@Test(expected = IllegalArgumentException.class) 
	public void providesProducerExecutorCorePoolZeroSizeTest() throws IllegalArgumentException {		

		consumerProducerFactory = new ConsumerProducerFactory(0, 0);
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();
		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}
	
	
	/**
	 * Test the providesProducerExecutor() method
	 * Throws IllegalArgumentException if negative number is defined for the core pool size
	 * @throws IllegalArgumentException
	 * */
	@Test(expected = IllegalArgumentException.class) 
	public void providesProducerExecutorCorePoolNegativeSizeTest() throws IllegalArgumentException {		

		consumerProducerFactory = new ConsumerProducerFactory(-1, 0);
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();
		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}

	
	/**
	 * Test the providesConsumerExecutor() method, returns the expected type.
	 * */
	@Test
	public void providesConsumerExecutorTypeTest() {		
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();
		assertEquals(ThreadPoolExecutor.class, providedES.getClass());
	}

	/**
	 * Test the providesConsumerExecutor() method, returns the expected core pool size.
	 * */
	@Test
	public void providesConsumerExecutorCorePoolSizeTest() {		
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();
		assertEquals(NUMBER_OF_CONSUMERS, providedES.getCorePoolSize());
	}

	/**
	 * Test the providesConsumerExecutor() method
	 * Throws IllegalArgumentException if zero is defined for the core pool size
	 * @throws IllegalArgumentException
	 * */
	@Test(expected = IllegalArgumentException.class) 
	public void providesConsumerExecutorCorePoolZeroSizeTest() throws IllegalArgumentException {		

		consumerProducerFactory = new ConsumerProducerFactory(0, 0);
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();
		assertEquals(NUMBER_OF_CONSUMERS, providedES.getCorePoolSize());
	}
	
	
	/**
	 * Test the providesConsumerExecutor() method
	 * Throws IllegalArgumentException if negative number is defined for the core pool size
	 * @throws IllegalArgumentException
	 * */
	@Test(expected = IllegalArgumentException.class) 
	public void providesConsumerExecutorCorePoolNegativeSizeTest() throws IllegalArgumentException {		

		consumerProducerFactory = new ConsumerProducerFactory(0, -1);
		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();
		assertEquals(NUMBER_OF_CONSUMERS, providedES.getCorePoolSize());
	}
	
	
	
	

	@After
	public void tearDown() {
		consumerProducerFactory = null;
	}

}
