
package com.Acrobot.ChestShop.Events.Economy;

import java.math.BigDecimal;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Represents a moment when the currency needs to be shown
 * 
 * @author Acrobot */
public class CurrencyFormatEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	
	private final BigDecimal amount;
	private String formattedAmount = "";
	
	public CurrencyFormatEvent(BigDecimal amount) {
		this.amount = amount;
	}
	
	/** @return Amount of currency */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/** @return Amount of currency, as a double
	 * @deprecated Use {@link #getAmount()} if possible */
	@Deprecated
	public double getDoubleAmount() {
		return amount.doubleValue();
	}
	
	/** @return Already formatted currency amount */
	public String getFormattedAmount() {
		return formattedAmount;
	}
	
	/** Sets the currency formatting
	 * 
	 * @param formattedAmount Formatted amount */
	public void setFormattedAmount(String formattedAmount) {
		this.formattedAmount = formattedAmount;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
