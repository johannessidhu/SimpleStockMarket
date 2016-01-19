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

public class TradeTest {

	private IStock stock;
	private Timestamp timestamp;
	private int quantityOfStocks;
	private TransactionIndicator transactionIndicator;
	private BigDecimal tradedPrice;

	@Before
	public void setUp() {
		stock = new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100"));
		timestamp = new Timestamp(new Date().getTime());
		tradedPrice = new BigDecimal("10");
		quantityOfStocks = 1;
		transactionIndicator = TransactionIndicator.BUY;
	}

	@Test
	public void tradeConstructor_GivenATeaStockTrade_ExpectTheStockToBeCorrectlyRecorded() {
		Trade trade = new Trade(stock, timestamp, quantityOfStocks, transactionIndicator, tradedPrice);

		assertEquals(stock, trade.getStock());
	}

	@Test
	public void tradeConstructor_GivenATradeWithTheTransactionIndicatorSell_ExpectTransactionIndicatorIsRecordedCorrectly() {
		transactionIndicator = TransactionIndicator.SELL;
		Trade trade = new Trade(stock, timestamp, quantityOfStocks, transactionIndicator, tradedPrice);

		assertEquals(transactionIndicator, trade.getTransactionIndicator());
	}

}