package stockMarket;

import java.math.BigDecimal;

public interface IStock {

	public BigDecimal returnDividendYield(BigDecimal price);

	public BigDecimal returnPERation(BigDecimal price);

	public String getStockSymbol();

	public void setLatestPrice(BigDecimal latestPrice);

	public BigDecimal getLatestPrice();

	@Override
	public String toString();

}
