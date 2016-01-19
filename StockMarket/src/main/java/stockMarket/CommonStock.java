package stockMarket;

import java.math.BigDecimal;

public class CommonStock extends Stock implements IStock {

	public CommonStock(String stockSymbol, BigDecimal lastDividend, BigDecimal parValue) {
		super(stockSymbol, lastDividend, parValue);
	}

	public BigDecimal returnDividendYield(BigDecimal price) {
		return calculateDividendYield(price);
	}

	private BigDecimal calculateDividendYield(BigDecimal price) {
		return getLastDividend().divide(price, mc);
	}
}
