package model;

import java.math.BigDecimal;
import java.util.Date;

public class Position {
	
	protected BigDecimal value;
	protected BigDecimal buyPrice;
	protected BigDecimal sellPrice;
	
	protected Date dateOpened;
	protected Date dateClosed;
	
	protected Boolean open;
	
	protected String cusip;
	
	protected int shares;
	
	protected Position(String cusip, BigDecimal buyPrice, int shares, Date dateOpened){
		this.dateOpened = dateOpened;
		this.buyPrice = buyPrice;
		this.shares = shares;
	}
	
	protected void sell(BigDecimal sellPrice, int shares){
		
	}
	
	protected void close(Date date, BigDecimal sellPrice){
		this.dateClosed = date;
		this.sellPrice = sellPrice;
		this.open = false;
	}
}
