package contract;

public interface IPortfolio {
	
	void buy(String cusip, int shares, double price);
	void sell(String cusip, int shares, double price);

}
