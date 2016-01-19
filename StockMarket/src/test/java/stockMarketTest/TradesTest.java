package stockMarketTest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import stockMarket.CommonStock;
import stockMarket.IStock;
import stockMarket.Trade;
import stockMarket.Trade.TransactionIndicator;
import stockMarket.Trades;

public class TradesTest {

	private Trades trades;
	private Timestamp timestamp;
	private IStock tea;
	private IStock pop;
	private int quantityOfShares = 1;
	private TransactionIndicator transactionIndicator = TransactionIndicator.BUY;
	private BigDecimal tradedPrice = new BigDecimal("10");

	@Before
	public void setup() {
		trades = new Trades();
		timestamp = new Timestamp(new Date().getTime());
		tea = new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100"));
		pop = new CommonStock("POP", new BigDecimal("8"), new BigDecimal("100"));
	}

	@Test
	public void returnElgibleTradesOfThePast15Minutes_Given4TradesButOnly3AreElgible_ExpectOnly3AreReturned() {
		timestamp = new Timestamp(timestamp.getTime() - (60000 * 16));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		timestamp = new Timestamp(timestamp.getTime() + (60000 * 5));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		timestamp = new Timestamp(timestamp.getTime() + (60000 * 5));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		timestamp = new Timestamp(timestamp.getTime() + (60000 * 5));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		Timestamp now = new Timestamp(new Date().getTime());

		assertEquals(3, trades.returnElgibleTradesOfThePast15Minutes(tea.getStockSymbol(), now).size());
	}

	@Test
	public void returnElgibleTradesOfThePast15Minutes_GivenBothTradesWereCommittedMoreThant15MinutesAgo_ExpectNoTradesAreReturned() {
		timestamp = new Timestamp(timestamp.getTime() - (60000 * 19));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		timestamp = new Timestamp(timestamp.getTime() + (60000 * 3));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		Timestamp now = new Timestamp(new Date().getTime());

		assertEquals(0, trades.returnElgibleTradesOfThePast15Minutes(tea.getStockSymbol(), now).size());
	}

	@Test
	public void returnElgibleTradesOfThePast15Minutes_GivenTEAAsStockSymbol_ExpectOnlyTradeFromStockSymbolTEAToBeReturned() {
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));
		trades.addTrade(new Trade(tea, timestamp, quantityOfShares, transactionIndicator, tradedPrice));
		trades.addTrade(new Trade(pop, timestamp, quantityOfShares, transactionIndicator, tradedPrice));

		assertEquals(2, trades.returnElgibleTradesOfThePast15Minutes(tea.getStockSymbol(), timestamp).size());
	}
}
