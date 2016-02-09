package contract;

/**
 * Defines a set of behaviors for a Trader instance. This is the interface that will be exposed to the world.
 * @author Isaac T
 *
 */
public interface ITrader {
	
	void buy(String cusip, int shares, double price);
	void sell(String cusip, int shares, double price);

}
