package stockMarket;

import java.math.BigDecimal;

public class PreferredStock extends Stock implements IStock {

	private BigDecimal fixedDividend;

	public PreferredStock(String stockSymbol, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue) {
		super(stockSymbol, lastDividend, parValue);
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal returnDividendYield(BigDecimal price) {
		return calculateDividendYield(price);
	}

	private BigDecimal calculateDividendYield(BigDecimal price) {
		return (fixedDividend.multiply(getParValue(), mc)).divide(price, mc);
	}

	public String toString() {
		return super.toString() + " | Fixed Dividend: " + fixedDividend;
	}
}
