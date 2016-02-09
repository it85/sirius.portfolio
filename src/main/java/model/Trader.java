package model;

import contract.ITrader;

/**
 * A Trader instance which encapsulates a set of core behaviors necessary to manipulate a Portfolio, e.g buy and sell securities.
 * In addition, a Trader abstracts out from a Portfolio control behaviors unrelated to core Portfolio functionality, i.e. trailing-stop awareness, Regulation-T 
 * @author Isaac T
 *
 */
public class Trader implements ITrader {

	private Portfolio portfolio;
	
	public Trader(){
		this.portfolio = new Portfolio();
	}
	
	public void buy(String cusip, int shares, double price) {
		this.portfolio.buy(cusip, shares, price);		
	}

	public void sell(String cusip, int shares, double price) {
		this.portfolio.sell(cusip, shares, price);		
	}

}
