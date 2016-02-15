package model;

import java.math.BigDecimal;
import java.util.Date;

public class SellOrder extends Order {
	
	public SellOrder(String cusip, int shares, BigDecimal price, Date date) {
		super(cusip, shares, price, date);
		this.amount = price.multiply(new BigDecimal(shares));
	}
}
