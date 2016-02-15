package api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	public void testFullSell1() {
		
		String cusip = "SPY";
		int shares = 50;
		double sellPrice = 4.02;
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
		assertEquals(new BigDecimal("0.00"), trader.getPortfolio().getSecuritiesBalance());
		assertEquals(new BigDecimal("10013.50"), trader.getPortfolio().getTotalBalance());
		assertEquals(new BigDecimal("10013.50"), trader.getPortfolio().getCashBalance());
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
		assertEquals(new BigDecimal("3.75"), trader.getPortfolio().getPositions().get(cusip).getVwap());
		assertEquals(new BigDecimal("4.01"), trader.getPortfolio().getPositions().get(cusip).getLastSalePrice());
	}
	
	@Test
	public void testSell2() {
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
		assertEquals(new BigDecimal("3.75"), trader.getPortfolio().getPositions().get(cusip).getVwap());
		assertEquals(new BigDecimal("4.01"), trader.getPortfolio().getPositions().get(cusip).getLastSalePrice());
		
		shares = 17;
		sellPrice = 4.05;
		
		try {
			trader.sell(cusip, shares, sellPrice, date);
		} catch (InvalidSellOrderException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, trader.getPortfolio().getPositions().size());
		assertEquals(false, trader.getPortfolio().getPositions().get(cusip).getOpen());
		assertEquals(0, trader.getPortfolio().getPositions().get(cusip).getShares());
		assertEquals(new BigDecimal("3.75"), trader.getPortfolio().getPositions().get(cusip).getVwap());
		assertEquals(new BigDecimal("4.05"), trader.getPortfolio().getPositions().get(cusip).getLastSalePrice());
	}
	
	@Test(expected=InvalidSellOrderException.class)
	public void testSellProceedsExeceedPositionValue() throws InvalidSellOrderException {
		
		String cusip = "SPY";
		int shares = 500;
		double buyPrice = 1000;
		Date dateOpened = new Date();
		
		trader.sell(cusip, shares, buyPrice, dateOpened);
	}
	
	@Test(expected=InvalidSellOrderException.class)
	public void testNonexistentPosition() throws InvalidSellOrderException {
		
		String cusip = "FB";
		int shares = 500;
		double buyPrice = 1000;
		Date dateOpened = new Date();
		
		trader.sell(cusip, shares, buyPrice, dateOpened);
	}
	
	@Test(expected=InvalidSellOrderException.class)
	public void testSellZeroShares() throws InvalidSellOrderException {
		
		String cusip = "SPY";
		int shares = 0;
		double buyPrice = 3.75;
		Date dateOpened = new Date();
		
		trader.sell(cusip, shares, buyPrice, dateOpened);
	}
	
	@Test
	public void testSellOrderHistory() {
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
		assertEquals(new BigDecimal("3.75"), trader.getPortfolio().getPositions().get(cusip).getVwap());
		assertEquals(new BigDecimal("4.01"), trader.getPortfolio().getPositions().get(cusip).getLastSalePrice());
		
		assertNotNull(trader.getPortfolio().getOrderHistory());
		assertNotNull(trader.getPortfolio().getOrderHistory().get(cusip));
		assertEquals(2, trader.getPortfolio().getOrderHistory().get(cusip).size());
		
		try {
			trader.sell(cusip, 1, buyPrice, dateOpened);
		} catch (InvalidSellOrderException e) {
			e.printStackTrace();
		}		
		assertEquals(3, trader.getPortfolio().getOrderHistory().get(cusip).size());
	}
}
