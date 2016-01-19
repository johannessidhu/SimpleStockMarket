package stockMarket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Trades {

	private List<Trade> trades = new ArrayList<Trade>();

	public void addTrade(Trade trade) {
		trades.add(trade);
	}

	public List<Trade> returnElgibleTradesOfThePast15Minutes(String stockSymbol, Timestamp currentTimestamp) {
		List<Trade> tradesOfTheLast15Minutes = new ArrayList<Trade>();
		ListIterator<Trade> reverseIterator = trades.listIterator(trades.size());

		while (reverseIterator.hasPrevious()) {
			Trade latestTrade = reverseIterator.previous();

			if (isTradeFromLast15Minutes(currentTimestamp, latestTrade.getTradeTimestamp())) {
				if (isCorrectStock(stockSymbol, latestTrade)) {
					tradesOfTheLast15Minutes.add(latestTrade);
				}
			} else {
				return tradesOfTheLast15Minutes;
			}
		}
		return tradesOfTheLast15Minutes;
	}

	private boolean isTradeFromLast15Minutes(Timestamp currentTimestamp, Timestamp latestTradeTimeStamp) {
		if (differenceInMinutes(currentTimestamp, latestTradeTimeStamp) <= 15) {
			return true;
		}
		return false;
	}

	private boolean isCorrectStock(String stockSymbol, Trade latestTrade) {
		return stockSymbol.compareToIgnoreCase(latestTrade.getStock().getStockSymbol()) == 0;
	}

	private long differenceInMinutes(Timestamp currentTimestamp, Timestamp latestTradeTimeStamp) {
		long diff = currentTimestamp.getTime() - latestTradeTimeStamp.getTime();
		return diff / (60 * 1000) % 60;
	}

}