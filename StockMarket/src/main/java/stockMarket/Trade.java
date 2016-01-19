package stockMarket;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Trade {

	public enum TransactionIndicator {
		BUY, SELL;

		@Override
		public String toString() {
			switch (this) {
			case BUY:
				return "Buy";
			case SELL:
				return "Sell";
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	private Timestamp tradeTimestamp;
	private IStock stock;
	private int quantityOfStocks;
	private TransactionIndicator transactionIndicator;
	private BigDecimal tradedPrice;

	public Trade(IStock stock, Timestamp timestamp, int quantityOfStocks, TransactionIndicator transactionIndicator,
			BigDecimal tradedPrice) {
		this.tradeTimestamp = timestamp;
		this.stock = stock;
		this.quantityOfStocks = quantityOfStocks;
		this.transactionIndicator = transactionIndicator;
		this.tradedPrice = tradedPrice;
	}

	public Timestamp getTradeTimestamp() {
		return tradeTimestamp;
	}

	public IStock getStock() {
		return stock;
	}

	public int getQuantityOfStocks() {
		return quantityOfStocks;
	}

	public TransactionIndicator getTransactionIndicator() {
		return transactionIndicator;
	}

	public BigDecimal getTradedPrice() {
		return tradedPrice;
	}

	@Override
	public String toString() {
		return "Stock: " + stock.getStockSymbol() + " | Trade date: " + tradeTimestamp + " | Quantity: "
				+ quantityOfStocks + " | Transaction: " + transactionIndicator + " | Traded price: "
				+ tradedPrice.toPlainString();
	}
}
