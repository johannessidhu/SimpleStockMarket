package stockMarket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class StockMarket {

	private Map<String, IStock> stocks;

	public StockMarket() {
		stocks = new HashMap<String, IStock>();
		stocks.put("TEA", new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100")));
		stocks.put("POP", new CommonStock("POP", new BigDecimal("8"), new BigDecimal("100")));
		stocks.put("ALE", new CommonStock("ALE", new BigDecimal("23"), new BigDecimal("60")));
		stocks.put("GIN", new PreferredStock("GIN", new BigDecimal("8"), new BigDecimal("2.0"), new BigDecimal("100")));
		stocks.put("JOE", new CommonStock("JOE", new BigDecimal("13"), new BigDecimal("250")));
	}

	public BigDecimal returnVWSP(List<Trade> trades) {
		return calculateVWSP(calculateTotalTradeValue(trades), calculateTotalNumberOfStocks(trades));
	}

	private BigDecimal calculateTotalTradeValue(List<Trade> trades) {
		ListIterator<Trade> tradesIterator = trades.listIterator();
		BigDecimal totalTradeValue = new BigDecimal("0");

		while (tradesIterator.hasNext()) {
			Trade currentTrade = tradesIterator.next();
			BigDecimal currentTradeValue = BigDecimal.valueOf(currentTrade.getQuantityOfStocks());
			currentTradeValue = currentTradeValue.multiply(currentTrade.getTradedPrice(), Stock.mc);
			totalTradeValue = totalTradeValue.add(currentTradeValue);
		}
		return totalTradeValue;
	}

	private BigDecimal calculateTotalNumberOfStocks(List<Trade> trades) {
		ListIterator<Trade> tradesIterator = trades.listIterator();
		BigDecimal totalNumberOfStocks = new BigDecimal("0");

		while (tradesIterator.hasNext()) {
			Trade currentTrade = tradesIterator.next();
			totalNumberOfStocks = totalNumberOfStocks.add(BigDecimal.valueOf(currentTrade.getQuantityOfStocks()));
		}
		return totalNumberOfStocks;
	}

	private BigDecimal calculateVWSP(BigDecimal totalTradeValue, BigDecimal totalNumberOfStocks) {
		return totalTradeValue.divide(totalNumberOfStocks, Stock.mc);
	}

	public BigDecimal returnGBCEAllShareIndex() {
		BigDecimal product = new BigDecimal("1");

		for (IStock stock : stocks.values()) {
			if (stock.getLatestPrice().compareTo(BigDecimal.ZERO) != 0) {
				product = product.multiply(stock.getLatestPrice(), Stock.mc);
			}
		}

		Double productAsDouble = Double.valueOf(product.toPlainString());
		Double allShareIndex = Math.pow(productAsDouble, 1.0 / stocks.size());
		return new BigDecimal(allShareIndex.toString(), Stock.mc);
	}

	public Map<String, IStock> getStocks() {
		return stocks;
	}

	public IStock getStock(String stockSymbol) {
		stockSymbol = stockSymbol.toUpperCase();
		return stocks.get(stockSymbol);
	}

	public void setNewStockPrice(String stockSymbol, BigDecimal newPrice) {
		stocks.get(stockSymbol).setLatestPrice(newPrice);
	}

	public BigDecimal getLatestStockPrice(String stockSymbol) {
		return stocks.get(stockSymbol).getLatestPrice();
	}

}
