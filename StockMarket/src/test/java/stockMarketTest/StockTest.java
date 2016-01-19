package stockMarketTest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import stockMarket.CommonStock;
import stockMarket.IStock;
import stockMarket.PreferredStock;
import stockMarket.Stock;

public class StockTest {

	@Test
	public void returnDividendYield_GivenThePrice100PenceAndLastDividend0ForACommonStock_ExpectDividendYieldOf0() {
		IStock tea = new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100"));
		BigDecimal expectedDividendYield = new BigDecimal("0", Stock.mc);

		assertEquals(expectedDividendYield, tea.returnDividendYield(new BigDecimal("100")));
	}

	@Test
	public void returnDividendYield_GivenThePrice110PenceAndLastDividend8ForACommonStock_ExpectDividendYield0Point072() {
		IStock pop = new CommonStock("POP", new BigDecimal("8"), new BigDecimal("100"));
		BigDecimal expectedDividendYield = new BigDecimal("0.07272727273", Stock.mc);

		assertEquals(expectedDividendYield, pop.returnDividendYield(new BigDecimal("110")));
	}

	@Test
	public void returnDividendYield_GivenThePrice110PenceAndFixedDividend2AndParValue100ForAPreferredStock_ExpectDividendYield1Point818() {
		IStock gin = new PreferredStock("GIN", new BigDecimal("8"), new BigDecimal("2.0"), new BigDecimal("100"));
		BigDecimal expectedDividendYield = new BigDecimal("1.818181818", Stock.mc);

		assertEquals(expectedDividendYield, gin.returnDividendYield(new BigDecimal("110")));
	}

	@Test
	public void returnPERation_GivenThePrice110PenceAndLastDividend0ForForACommonStock_ExpectPERatioNullRepresentingUndefined() {
		IStock tea = new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100"));
		BigDecimal expectedPERatio = null;

		assertEquals(expectedPERatio, tea.returnPERation(new BigDecimal("110")));
	}

	@Test
	public void returnPERation_GivenThePrice110PenceAndLastDividend23ForACommonStock_ExpectPERatio4Point782() {
		IStock ale = new CommonStock("ALE", new BigDecimal("23"), new BigDecimal("60"));
		BigDecimal expectedPERatio = new BigDecimal("4.782608696", Stock.mc);

		assertEquals(expectedPERatio, ale.returnPERation(new BigDecimal("110")));
	}

	@Test
	public void returnPERation_GivenThePrice110PenceAndDividend8ForAPreferredStock_ExpectPERatio13Point75() {
		IStock gin = new PreferredStock("GIN", new BigDecimal("8"), new BigDecimal("2.0"), new BigDecimal("100"));
		BigDecimal expectedPERatio = new BigDecimal("13.75", Stock.mc);

		assertEquals(expectedPERatio, gin.returnPERation(new BigDecimal("110")));
	}
}
