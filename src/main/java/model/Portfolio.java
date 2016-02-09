package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exception.InsufficientFundsException;

public class Portfolio {

	protected BigDecimal totalBalance;
	protected BigDecimal cashBalace;
	protected BigDecimal securitiesBalance;
	
	protected Map<String, Position> positions;
	
	protected Portfolio(double startingBalance){		
		this.positions = new HashMap<String, Position>();
		
		this.totalBalance = new BigDecimal(startingBalance);
		this.cashBalace = new BigDecimal(startingBalance);
		this.securitiesBalance = new BigDecimal(0);
	}
	
	protected void buy(String cusip, int shares, BigDecimal price, Date date) throws InsufficientFundsException {
		
		BigDecimal orderTotal = price.multiply(new BigDecimal(shares));
		
		if(sufficientFunds(orderTotal)){
			Position p = new Position(cusip, price, shares, date);		
			positions.put(cusip, p);
			this.updateBalancesPostBuy(orderTotal);
		}else{
			throw new InsufficientFundsException("Cash Balance: " + this.cashBalace + ", Order Total: " + orderTotal);
		}		
	}

	protected void sell(String cusip, int shares, BigDecimal price, Date date) {
//		this.updateBalancesPostSell(shares, price);
	}
	
	protected boolean sufficientFunds(BigDecimal orderTotal){
		if(this.cashBalace.compareTo(orderTotal) > 0){
			return true;
		}else{
			return false;
		}
	}
	
	private void updateBalancesPostBuy(BigDecimal orderTotal){
		this.cashBalace.subtract(orderTotal);
		this.securitiesBalance.add(orderTotal);
	}
	
//	private void updateBalancesPostSell(int shares, BigDecimal price){
//		BigDecimal orderTotal = price.multiply(new BigDecimal(shares));
//	}

}
