package factoryTests;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import concurrency.ConsumerProducerFactory;

public class ConsumerProducerFactoryTest {

	private final static int NUMBER_OF_PRODUCERS = 1;
	private final static int NUMBER_OF_CONSUMERS = 2;
	private ConsumerProducerFactory consumerProducerFactory; 

	@Before
	public void setUp() throws Exception {
		consumerProducerFactory = new ConsumerProducerFactory(NUMBER_OF_PRODUCERS, NUMBER_OF_CONSUMERS);
	}

	@Test
	public void providesProducerExecutorTypeTest() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();

		assertEquals(ThreadPoolExecutor.class, providedES.getClass());
	}

	@Test
	public void providesProducerExecutorCorePoolSizeTest() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();

		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}

	@Test(expected = IllegalArgumentException.class) 
	public void providesProducerExecutorCorePoolZeroSizeTest() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(0, 0);
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();

		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void providesProducerExecutorCorePoolNegativeSizeTest() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(-1, 0);
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesProducerExecutor();

		assertEquals(NUMBER_OF_PRODUCERS, providedES.getCorePoolSize());
	}

	@Test
	public void providesConsumerExecutorTypeTest() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();

		assertEquals(ThreadPoolExecutor.class, providedES.getClass());
	}

	@Test
	public void providesConsumerExecutorCorePoolSizeTest() {		
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();

		assertEquals(NUMBER_OF_CONSUMERS, providedES.getCorePoolSize());
	}

	@Test(expected = IllegalArgumentException.class) 
	public void providesConsumerExecutorCorePoolZeroSizeTest() throws IllegalArgumentException {		
		consumerProducerFactory = new ConsumerProducerFactory(0, 0);
		ThreadPoolExecutor providedES = (ThreadPoolExecutor) consumerProducerFactory.providesConsumerExecutor();

		assertEquals(NUMBER_OF_CONSUMERS, providedES.getCorePoolSize());
	}
	
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