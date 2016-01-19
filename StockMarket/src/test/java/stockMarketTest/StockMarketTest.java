package stockMarketTest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import stockMarket.Stock;
import stockMarket.StockMarket;
import stockMarket.Trade;
import stockMarket.Trade.TransactionIndicator;
import stockMarket.Trades;

public class StockMarketTest {

	private Trades trades;
	private Timestamp timestamp;
	private StockMarket stockMarket;
	private int quantityOfShares = 10;
	private TransactionIndicator transactionIndicator = TransactionIndicator.BUY;
	private BigDecimal tradedPrice = new BigDecimal("120");

	@Before
	public void setup() {
		trades = new Trades();
		timestamp = new Timestamp(new Date().getTime());
		stockMarket = new StockMarket();
	}

	@Test
	public void setNewStockPrice_GivenANewLatestPrice150ForACommonStock_ExpectNewPriceToBeSet() {
		trades.addTrade(new Trade(stockMarket.getStock("TEA"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.setNewStockPrice(stockMarket.getStock("TEA").getStockSymbol(), tradedPrice);

		tradedPrice = tradedPrice.add(new BigDecimal("30"));
		timestamp = new Timestamp(timestamp.getTime() + (60000 * 1));
		trades.addTrade(new Trade(stockMarket.getStock("TEA"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.setNewStockPrice(stockMarket.getStock("TEA").getStockSymbol(), tradedPrice);

		BigDecimal expectedLatestPrice = new BigDecimal("150", Stock.mc);
		assertEquals(expectedLatestPrice, stockMarket.getLatestStockPrice(stockMarket.getStock("TEA").getStockSymbol()));
	}

	@Test
	public void getLatestStockPrice_GivenANewLatestPrice150ForAPreferredStock_ExpectNewPriceToBeReturned() {
		trades.addTrade(new Trade(stockMarket.getStock("GIN"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.setNewStockPrice(stockMarket.getStock("GIN").getStockSymbol(), tradedPrice);

		tradedPrice = tradedPrice.add(new BigDecimal("30"));
		timestamp = new Timestamp(timestamp.getTime() + (60000 * 1));
		trades.addTrade(new Trade(stockMarket.getStock("GIN"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.setNewStockPrice(stockMarket.getStock("GIN").getStockSymbol(), tradedPrice);

		BigDecimal expectedLatestPrice = new BigDecimal("150", Stock.mc);
		assertEquals(expectedLatestPrice, stockMarket.getLatestStockPrice(stockMarket.getStock("GIN").getStockSymbol()));
	}

	@Test
	public void returnVWSP_Given2AppropriateCommonTeaStockTradesOf20StockesEachWithAPriceOf120PerStock_ExpectVWSPOf120() {
		trades.addTrade(new Trade(stockMarket.getStock("TEA"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		trades.addTrade(new Trade(stockMarket.getStock("TEA"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		trades.addTrade(new Trade(stockMarket.getStock("POP"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		timestamp = new Timestamp(timestamp.getTime() - (60000 * 19));
		trades.addTrade(new Trade(stockMarket.getStock("TEA"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));

		List<Trade> filteredTrades = trades.returnElgibleTradesOfThePast15Minutes(stockMarket.getStock("TEA")
				.getStockSymbol(), timestamp);
		BigDecimal expectedVWSP = new BigDecimal("120", Stock.mc);
		assertEquals(expectedVWSP, stockMarket.returnVWSP(filteredTrades));
	}

	@Test
	public void returnGBCEAllShareIndex_Given5TradesTradePricesOf1_3_9_27_81_ExpectGBCEAllShareIndexOf9() {
		tradedPrice = new BigDecimal("1");
		trades.addTrade(new Trade(stockMarket.getStock("TEA"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.getStock("TEA").setLatestPrice(tradedPrice);

		tradedPrice = new BigDecimal("3");
		trades.addTrade(new Trade(stockMarket.getStock("POP"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.getStock("POP").setLatestPrice(tradedPrice);

		tradedPrice = new BigDecimal("9");
		trades.addTrade(new Trade(stockMarket.getStock("ALE"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.getStock("ALE").setLatestPrice(tradedPrice);

		tradedPrice = new BigDecimal("27");
		trades.addTrade(new Trade(stockMarket.getStock("GIN"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.getStock("GIN").setLatestPrice(tradedPrice);

		tradedPrice = new BigDecimal("81");
		trades.addTrade(new Trade(stockMarket.getStock("JOE"), timestamp, quantityOfShares, transactionIndicator,
				tradedPrice));
		stockMarket.getStock("JOE").setLatestPrice(tradedPrice);

		BigDecimal expectedLatestPrice = new BigDecimal("9.000000000", Stock.mc);
		assertEquals(expectedLatestPrice, stockMarket.returnGBCEAllShareIndex());
	}

}
