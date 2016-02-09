package contract;

/**
 * Defines a set of behaviors for a Portfolio instance. This is the interface that is exposed to the world to use.
 * @author Isaac T
 *
 */
public interface IPortfolio {
	
	void buy(String cusip, int shares, double price);
	void sell(String cusip, int shares, double price);

}
