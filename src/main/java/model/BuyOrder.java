package model;

import java.math.BigDecimal;
import java.util.Date;

public class BuyOrder extends Order{

	public BuyOrder(String cusip, int shares, BigDecimal price, Date date) {
		super(cusip, shares, price, date);
	}

}
