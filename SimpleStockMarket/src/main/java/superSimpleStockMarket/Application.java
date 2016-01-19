package superSimpleStockMarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import stockMarket.IStock;
import stockMarket.StockMarket;
import stockMarket.Trade;
import stockMarket.Trade.TransactionIndicator;
import stockMarket.Trades;

public class Application {

	private static final String WELCOME_MESSAGE = "\nWELCOME TO THE SUPER SIMPLE STOCK MARKET\n"
			+ "\nYou now have seven options:\n" + "d : view the dividend yield for a given stock and price\n"
			+ "p : view the P/E ratio for a given stock and price\n"
			+ "r : record a trade with timestamp, quantity, buy or sell indicator and traded price\n"
			+ "v : view Volume Weighted Stock Price based on trades of the past 15 minutes\n"
			+ "g : view the GBCE All Share Index\n" + "a : view all stocks\n" + "q : quit" + "\n>> ";

	private static final String CHOOSE_STOCK_MESSAGE = "Please choose a stock from the list below:\n"
			+ "tea : TEA stock\n" + "pop : POP stock\n" + "ale : ALE stock\n" + "gin : GIN stock\n"
			+ "joe : JOE stock\n" + ">> ";

	public static void main(String[] args) {
		StockMarket stockMarket = new StockMarket();
		Trades trades = new Trades();

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String response = "";
			String prompt = "";

			do {
				System.out.println(WELCOME_MESSAGE);
				response = in.readLine();
			} while (!response.matches("[a-zA-Z]"));

			while (!response.equals("q")) {
				String[] chars = response.split(" ");
				char option = chars[0].charAt(0);
				switch (option) {
				case 'd':
					IStock selectedStock = null;
					response = "";
					while (response.isEmpty()) {
						System.out.println("\nCALCULATE DIVIDEND YIELD\n");
						System.out.println(CHOOSE_STOCK_MESSAGE);
						response = in.readLine();
						if (stockMarket.getStock(response) == null) {
							response = "";
						} else {
							selectedStock = stockMarket.getStock(response);
						}
					}
					response = "";
					BigDecimal price = null;
					while (response.isEmpty()) {
						System.out.println("\nPlease enter the price for the stock in pennies\n >> ");
						response = in.readLine();

						if (!response.matches("^[0-9]+$")) {
							response = "";
						} else {
							price = new BigDecimal(response);
						}
					}

					BigDecimal dividendYield = stockMarket.getStock(selectedStock.getStockSymbol())
							.returnDividendYield(price);
					System.out.println(dividendYieldMessage(selectedStock.getStockSymbol(), price.toPlainString(),
							dividendYield.toPlainString()));
					break;
				case 'p':
					selectedStock = null;
					price = null;
					response = "";

					while (response.isEmpty()) {
						System.out.println("\nCALCULATE P/E RATIO\n");
						System.out.println(CHOOSE_STOCK_MESSAGE);
						response = in.readLine();
						if (stockMarket.getStock(response) == null) {
							response = "";
						} else {
							selectedStock = stockMarket.getStock(response);
						}
					}
					response = "";

					while (response.isEmpty()) {
						System.out.println("\nPlease enter the price for the stock in pennies please\n >> ");
						response = in.readLine();

						if (!response.matches("^[0-9]+$")) {
							response = "";
						} else {
							price = new BigDecimal(response);
						}
					}

					BigDecimal peRatio = stockMarket.getStock(selectedStock.getStockSymbol()).returnPERation(price);

					if (peRatio == null) {
						System.out.println("The P/E ratio is undefined" + " for " + selectedStock.getStockSymbol());
					} else {
						System.out.println("The P/E ratio is " + peRatio + " for " + selectedStock.getStockSymbol());
					}
					break;
				case 'r':
					int quantityOfStocks = 0;
					TransactionIndicator transactionIndicator = null;
					BigDecimal tradedPrice = null;
					selectedStock = null;
					response = "";

					while (response.isEmpty()) {
						System.out.println("\nRECORD TRADE\n");
						System.out.println(CHOOSE_STOCK_MESSAGE);
						response = in.readLine();
						if (stockMarket.getStock(response) == null) {
							response = "";
						} else {
							selectedStock = stockMarket.getStock(response);
						}
					}
					response = "";

					while (response.isEmpty()) {
						System.out.println("\nRECORD TRADE\n");
						System.out.println("Please enter stock quantity:\n" + ">> ");
						response = in.readLine();

						if (!response.matches("^[0-9]+$")) {
							response = "";
						} else {
							quantityOfStocks = Integer.valueOf(response);
						}
					}
					response = "";

					while (response.isEmpty()) {
						System.out.println("\nRECORD TRADE\n");
						System.out.println("Please enter trade price in pennies please:\n" + ">> ");
						response = in.readLine();

						if (!response.matches("^[0-9]+$")) {
							response = "";
						} else {
							tradedPrice = new BigDecimal(response);
						}
					}
					response = "";

					while (response.isEmpty()) {
						System.out.println("\nRECORD TRADE\n");
						System.out.println("Please choose a transaction indicator from the list below:\n"
								+ "buy  : buy stock\n" + "sell : sell Stock\n" + ">> ");
						response = in.readLine();

						transactionIndicator = returnTransactionIndicator(response);

						if (transactionIndicator == null) {
							response = "";
						}
					}
					Trade newTrade = new Trade(selectedStock, new Timestamp(new Date().getTime()), quantityOfStocks,
							transactionIndicator, tradedPrice);
					trades.addTrade(newTrade);
					stockMarket.setNewStockPrice(selectedStock.getStockSymbol(), tradedPrice);
					System.out.println("\nRECORD TRADE\n");
					System.out.println("Trade successfully recorded\n");
					System.out.println(newTrade);
					break;
				case 'v':
					selectedStock = null;
					response = "";

					while (response.isEmpty()) {
						System.out
								.println("\nView VOLUME WEIGHTED STOCK PRICE BASED ON TRADES OF THE PAST 15 MINUTES\n");
						System.out.println(CHOOSE_STOCK_MESSAGE);
						response = in.readLine();

						if (stockMarket.getStock(response) == null) {
							response = "";
						} else {
							selectedStock = stockMarket.getStock(response);
						}
					}
					List<Trade> filteredTrades = trades.returnElgibleTradesOfThePast15Minutes(
							selectedStock.getStockSymbol(), new Timestamp(new Date().getTime()));

					if (filteredTrades.isEmpty()) {
						System.out.println("The VWSP cannot be calculated,"
								+ " because no elgible trades were yet recorded for " + selectedStock.getStockSymbol()
								+ " stock");
						break;
					}
					System.out.println("The VWSP is: " + stockMarket.returnVWSP(filteredTrades).toPlainString()
							+ " for " + selectedStock.getStockSymbol());
					break;
				case 'g':
					System.out.println("\nVIEW GBCE All SHARE INDEX\n");
					System.out.println("The GBCE All Share Index is: "
							+ stockMarket.returnGBCEAllShareIndex().toPlainString());
					break;
				case 'a':
					System.out.println("\nView All STOCKS IN STOCK MARKET\n");
					Set<String> keys = stockMarket.getStocks().keySet();
					Iterator<String> iter = keys.iterator();

					while (iter.hasNext()) {
						System.out.println("\t" + stockMarket.getStock(iter.next()));
					}
					break;
				}

				do {
					System.out.println(WELCOME_MESSAGE);
					response = in.readLine();
				} while (!response.matches("[a-zA-Z]"));
			}
			System.out.println("\nSUPER SIMPLE STOCK MARKET IS CLOSED NOW\n" + "THANK YOU FOR VISITING");
			in.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String dividendYieldMessage(String stockSymbol, String price, String dividendYield) {
		return "The dividend yield is " + dividendYield + " for " + stockSymbol + " stock" + " at the price of "
				+ price + " pennies";
	}

	private static TransactionIndicator returnTransactionIndicator(String response) {
		if (response.compareToIgnoreCase(TransactionIndicator.BUY.toString()) == 0) {
			return TransactionIndicator.BUY;
		} else if (response.compareToIgnoreCase(TransactionIndicator.SELL.toString()) == 0) {
			return TransactionIndicator.SELL;
		}
		return null;
	}
}
