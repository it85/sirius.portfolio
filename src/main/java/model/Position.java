package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Position {

	protected BigDecimal value;
	protected BigDecimal vwap;
	protected BigDecimal sellPrice;

	protected Date dateOpened;
	protected Date dateClosed;

	protected Boolean open;

	protected String cusip;

	protected int shares;

	protected Position(String cusip, BigDecimal buyPrice, int shares, Date dateOpened) {
		this.dateOpened = dateOpened;
		this.shares = shares;
		this.vwap = buyPrice;
		this.cusip = cusip;
		this.open = true;
		this.value = buyPrice.multiply(new BigDecimal(shares));
	}

	protected void add(BigDecimal buyPrice, int shares) {
		int totalNewShares = this.shares + shares;

		BigDecimal newValue = buyPrice.multiply(new BigDecimal(shares));
		BigDecimal totalValue = this.value.add(newValue);

		this.vwap = totalValue.divide(new BigDecimal(totalNewShares));
		this.shares = totalNewShares;
	}

	protected void sell(BigDecimal sellPrice, int shares) {

	}

	protected void close(Date date, BigDecimal sellPrice) {
		this.dateClosed = date;
		this.sellPrice = sellPrice;
		this.open = false;
	}

	public BigDecimal getValue() {
		return value.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getVwap() {
		return vwap.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getSellPrice() {
		return sellPrice.setScale(2, RoundingMode.HALF_UP);
	}

	public Date getDateOpened() {
		return dateOpened;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public Boolean getOpen() {
		return open;
	}

	public String getCusip() {
		return cusip;
	}

	public int getShares() {
		return shares;
	}
}
