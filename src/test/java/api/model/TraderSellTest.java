package api.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.InvalidSellOrderException;
import model.Trader;

public class TraderSellTest {

	static Trader trader;
	final static String cusip = "SPY";
	final static int shares = 50;
	final static double buyPrice = 3.75;
	final static Date dateOpened = new Date();
	final static double startingBalance = 10000;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trader = new Trader(startingBalance);
		trader.buy(cusip, shares, buyPrice, dateOpened);
	}

	@Before
	public void setUp() throws Exception {
		trader = new Trader(startingBalance);
		trader.getPortfolio().getPositions().clear();
		trader.buy(cusip, shares, buyPrice, dateOpened);
	}

	@After
	public void tearDown() throws Exception {
		trader = new Trader(startingBalance);
		trader.getPortfolio().getPositions().clear();
		trader.buy(cusip, shares, buyPrice, dateOpened);
	}
	
	@Test
	public void testFullSell() {
		String cusip = "SPY";
		int shares = 50;
		double sellPrice = 3.75;
		Date date = new Date();
		
		assertEquals(1, trader.getPortfolio().getPositions().size());
		
		try {
			trader.sell(cusip, shares, sellPrice, date);
		} catch (InvalidSellOrderException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, trader.getPortfolio().getPositions().size());
		assertEquals(false, trader.getPortfolio().getPositions().get(cusip).getOpen());
		assertEquals(0, trader.getPortfolio().getPositions().get(cusip).getShares());		
	}
	
	@Test
	public void testSell1() {
		String cusip = "SPY";
		int shares = 33;
		double sellPrice = 4.01;
		Date date = new Date();
		
		assertEquals(1, trader.getPortfolio().getPositions().size());
		
		try {
			trader.sell(cusip, shares, sellPrice, date);
		} catch (InvalidSellOrderException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, trader.getPortfolio().getPositions().size());
		assertEquals(true, trader.getPortfolio().getPositions().get(cusip).getOpen());
		assertEquals(17, trader.getPortfolio().getPositions().get(cusip).getShares());
		assertEquals(new BigDecimal("4.01"), trader.getPortfolio().getPositions().get(cusip).getVwap());
	}

}
