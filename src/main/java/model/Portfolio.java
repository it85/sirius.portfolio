package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exception.InsufficientFundsException;

public class Portfolio {

	protected BigDecimal totalBalance;
	protected BigDecimal cashBalance;
	protected BigDecimal securitiesBalance;
	
	protected Map<String, Position> positions;
	
	protected Portfolio(double startingBalance){		
		this.positions = new HashMap<String, Position>();
		
		this.totalBalance = new BigDecimal(startingBalance);
		this.cashBalance = new BigDecimal(startingBalance);
		this.securitiesBalance = new BigDecimal(0.0);
	}
	
	protected void buy(String cusip, int shares, BigDecimal buyPrice, Date dateOpened) throws InsufficientFundsException {
		
		BigDecimal orderTotal = buyPrice.multiply(new BigDecimal(shares));
		
		if(sufficientFunds(orderTotal)){
			
			if(!positions.containsKey(cusip)){
				Position p = new Position(cusip, buyPrice, shares, dateOpened);		
				positions.put(cusip, p);
			}else{
				Position p = positions.get(cusip);
				p.add(buyPrice, shares);
			}
			
			this.updateBalancesPostBuy(orderTotal);
		}else{
			throw new InsufficientFundsException("Cash Balance: " + this.cashBalance + ", Order Total: " + orderTotal);
		}		
	}
	
	

	protected void sell(String cusip, int shares, BigDecimal price, Date date) {
//		this.updateBalancesPostSell(shares, price);
	}
	
	protected boolean sufficientFunds(BigDecimal orderTotal){
		if(this.cashBalance.compareTo(orderTotal) > 0){
			return true;
		}else{
			return false;
		}
	}
	
	private void updateBalancesPostBuy(BigDecimal orderTotal){
		this.cashBalance = this.cashBalance.subtract(orderTotal);
		this.securitiesBalance = this.securitiesBalance.add(orderTotal);
	}
	
//	private void updateBalancesPostSell(int shares, BigDecimal price){
//		BigDecimal orderTotal = price.multiply(new BigDecimal(shares));
//	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public BigDecimal getSecuritiesBalance() {
		return securitiesBalance;
	}

	public Map<String, Position> getPositions() {
		return positions;
	}
}
