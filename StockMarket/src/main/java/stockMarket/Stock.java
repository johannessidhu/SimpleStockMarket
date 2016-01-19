package stockMarket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class Stock implements IStock {

	public static final MathContext mc = new MathContext(10, RoundingMode.HALF_EVEN);

	private String stockSymbol;
	private BigDecimal lastDividend;
	private BigDecimal parValue;
	private BigDecimal latestPrice;

	public Stock(String stockSymbol, BigDecimal lastDividend, BigDecimal parValue) {
		this.stockSymbol = stockSymbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
		this.setLatestPrice(BigDecimal.ZERO);
	}

	public abstract BigDecimal returnDividendYield(BigDecimal price);

	public BigDecimal returnPERation(BigDecimal price) {
		return calculatePERation(price);
	}

	private BigDecimal calculatePERation(BigDecimal price) {
		if (lastDividend.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return price.divide(lastDividend, mc);
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public BigDecimal getLatestPrice() {
		return latestPrice;
	}

	public void setLatestPrice(BigDecimal latestPrice) {
		this.latestPrice = latestPrice;
	}

	@Override
	public String toString() {
		return "Stock symbol: " + stockSymbol + " | Last dividend: " + lastDividend + " | Par value: " + parValue
				+ " | Latest price: " + latestPrice;
	}
}
