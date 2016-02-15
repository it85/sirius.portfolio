package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exception.InvalidBuyOrderException;
import exception.InvalidSellOrderException;

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
	
	protected void buy(String cusip, int shares, BigDecimal buyPrice, Date dateOpened) throws InvalidBuyOrderException {
		
		BigDecimal orderTotal = buyPrice.multiply(new BigDecimal(shares));
		
		if(buyOrderSufficientFunds(orderTotal)){
			
			if(!positions.containsKey(cusip)){
				Position p = new Position(cusip, buyPrice, shares, dateOpened);		
				positions.put(cusip, p);
			}else{
				Position p = positions.get(cusip);
				p.add(buyPrice, shares);
			}
			
			this.updateBalancesPostBuy(orderTotal);
		}else{
			throw new InvalidBuyOrderException("Cash Balance: " + this.cashBalance + ", Order Total: " + orderTotal);
		}		
	}
	
	

	protected void sell(String cusip, int shares, BigDecimal sellPrice, Date date) throws InvalidSellOrderException{
		
		if(positions.containsKey(cusip)) {
		
			Position position = positions.get(cusip);
			BigDecimal totalProceeds = sellPrice.multiply(new BigDecimal(shares));
			
			if(position.sellOrderSufficientFunds(totalProceeds) && position.sellOrderSufficientShares(shares)){				
				position.sell(sellPrice, shares);
				this.updateBalancesPostSell(totalProceeds);		
			}else{
				throw new InvalidSellOrderException("Sell order total proceeds: " + totalProceeds +
						", Position value: " + position.getValue());
			}
		}else{
			throw new InvalidSellOrderException("Position for cusip " + cusip + " does not exist");
		}
	}
	
	private boolean buyOrderSufficientFunds(BigDecimal orderTotal){		
		return (this.cashBalance.compareTo(orderTotal) == 1) && (orderTotal.compareTo(new BigDecimal(0)) != 0);
	}
	
	
	private void updateBalancesPostBuy(BigDecimal orderTotal){
		this.cashBalance = this.cashBalance.subtract(orderTotal);
		this.securitiesBalance = this.securitiesBalance.add(orderTotal);
		this.totalBalance = this.cashBalance.add(this.securitiesBalance);
	}
	
	private void updateBalancesPostSell(BigDecimal totalProceeds){
		this.cashBalance = this.cashBalance.add(totalProceeds);
		this.securitiesBalance = this.securitiesBalance.subtract(totalProceeds);
		this.totalBalance = this.cashBalance.add(this.securitiesBalance);
	}

	public BigDecimal getTotalBalance() {
		return totalBalance.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getCashBalance() {
		return cashBalance.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getSecuritiesBalance() {
		return securitiesBalance.setScale(2, RoundingMode.HALF_UP);
	}

	public Map<String, Position> getPositions() {
		return positions;
	}
}
