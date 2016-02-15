package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
	protected Map<String, ArrayList<Order>> orderHistory;
	
	protected Portfolio(double startingBalance){		
		this.positions = new HashMap<String, Position>();
		this.orderHistory = new HashMap<String, ArrayList<Order>>();
		
		this.totalBalance = new BigDecimal(startingBalance);
		this.cashBalance = new BigDecimal(startingBalance);
		this.securitiesBalance = new BigDecimal(0.0);
	}
	
	protected void buy(String cusip, int shares, BigDecimal price, Date dateOpened) throws InvalidBuyOrderException {
		
		BigDecimal orderTotal = price.multiply(new BigDecimal(shares));
		
		if(buyOrderSufficientFunds(orderTotal)){
			
			if(!positions.containsKey(cusip)){
				Position p = new Position(cusip, price, shares, dateOpened);		
				positions.put(cusip, p);
			}else{
				Position p = positions.get(cusip);
				p.add(price, shares);
			}
			
			this.postBuyAccounting(cusip, shares, price, dateOpened);
		}else{
			throw new InvalidBuyOrderException("Cash Balance: " + this.cashBalance + ", Order Total: " + orderTotal);
		}		
	}
	
	

	protected void sell(String cusip, int shares, BigDecimal price, Date date) throws InvalidSellOrderException{
		
		if(positions.containsKey(cusip)) {
		
			Position position = positions.get(cusip);
			BigDecimal totalProceeds = price.multiply(new BigDecimal(shares));
			
			if(position.sellOrderSufficientFunds(totalProceeds) && position.sellOrderSufficientShares(shares)){				
				position.sell(price, shares);
				this.postSellAccounting(cusip, shares, price, date);		
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
	
	
	private void postBuyAccounting(String cusip, int shares, BigDecimal price, Date date){		
		BigDecimal orderTotal = price.multiply(new BigDecimal(shares));
		this.postBuyUpdateBalances(orderTotal);
		this.addBuyOrderToHistory(cusip, shares, price, date);
	}
	
	private void postSellAccounting(String cusip, int shares, BigDecimal price, Date date){		
		BigDecimal orderTotal = price.multiply(new BigDecimal(shares));
		this.postSellUpdateBalances(orderTotal);
		this.addSellOrderToHistory(cusip, shares, price, date);
	}
	
	private void addBuyOrderToHistory(String cusip, int shares, BigDecimal price, Date date){
		Order order = new BuyOrder(cusip, shares, price, date);
		
		if(!this.orderHistory.containsKey(cusip) || orderHistory.get(cusip) == null)
			this.orderHistory.put(cusip, new ArrayList<Order>());
		
		this.orderHistory.get(cusip).add(order);		
	}
	
	private void addSellOrderToHistory(String cusip, int shares, BigDecimal price, Date date){
		Order order = new SellOrder(cusip, shares, price, date);
		
		if(!this.orderHistory.containsKey(cusip) || orderHistory.get(cusip) == null)
			this.orderHistory.put(cusip, new ArrayList<Order>());
		
		this.orderHistory.get(cusip).add(order);		
	}
	
	private void postBuyUpdateBalances(BigDecimal orderTotal){
		this.cashBalance = this.cashBalance.subtract(orderTotal);
		this.securitiesBalance = this.securitiesBalance.add(orderTotal);
		this.totalBalance = this.cashBalance.add(this.securitiesBalance);
	}
	
	private void postSellUpdateBalances(BigDecimal totalProceeds){
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

	public Map<String, ArrayList<Order>> getOrderHistory() {
		return orderHistory;
	}
}
