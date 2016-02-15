package contract;

import java.util.Date;

import exception.InvalidBuyOrderException;
import exception.InvalidSellOrderException;

/**
 * Defines a set of behaviors for a Trader instance. This is the interface that will be exposed to the world.
 * @author Isaac T
 *
 */
public interface ITrader {
	
	void buy(String cusip, int shares, double price, Date date) throws InvalidBuyOrderException;
	void sell(String cusip, int shares, double price, Date date) throws InvalidSellOrderException;

}
