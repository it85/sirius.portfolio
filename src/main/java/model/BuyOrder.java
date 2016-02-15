package model;

import java.math.BigDecimal;
import java.util.Date;

public class BuyOrder extends Order{

	public BuyOrder(String cusip, int shares, BigDecimal price, Date date) {
		super(cusip, shares, price, date);
		this.amount = price.multiply(new BigDecimal(shares)).multiply(new BigDecimal(-1));
	}

}
