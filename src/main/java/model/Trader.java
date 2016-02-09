package model;

import contract.ITrader;

/**
 * A Trader instance which encapsulates a set of core behaviors necessary to manipulate a Portfolio, e.g buy and sell securities.
 * In addition, a Trader abstracts out from a Portfolio control behaviors unrelated to core Portfolio functionality, i.e. trailing-stop awareness, Regulation-T 
 * @author Isaac T
 *
 */
public class Trader implements ITrader {

	public void buy(String cusip, int shares, double price) {
		// TODO Auto-generated method stub
		
	}

	public void sell(String cusip, int shares, double price) {
		// TODO Auto-generated method stub
		
	}

}
