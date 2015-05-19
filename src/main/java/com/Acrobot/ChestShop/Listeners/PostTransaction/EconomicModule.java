
package com.Acrobot.ChestShop.Listeners.PostTransaction;

import static com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType.BUY;
import static com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType.SELL;

import java.math.BigDecimal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.Economy.CurrencyAddEvent;
import com.Acrobot.ChestShop.Events.Economy.CurrencySubtractEvent;

/** @author Acrobot */
public class EconomicModule implements Listener {
	@EventHandler
	public static void onBuyTransaction(TransactionEvent event) {
		if (event.getTransactionType() != BUY) return;
		
		CurrencyAddEvent currencyAddEvent = new CurrencyAddEvent(BigDecimal.valueOf(event.getPrice()), event.getOwner().getName(), event.getSign().getWorld());
		ChestShop.callEvent(currencyAddEvent);
		
		CurrencySubtractEvent currencySubtractEvent = new CurrencySubtractEvent(BigDecimal.valueOf(event.getPrice()), event.getClient());
		ChestShop.callEvent(currencySubtractEvent);
	}
	
	@EventHandler
	public static void onSellTransaction(TransactionEvent event) {
		if (event.getTransactionType() != SELL) return;
		
		CurrencySubtractEvent currencySubtractEvent = new CurrencySubtractEvent(BigDecimal.valueOf(event.getPrice()), event.getOwner().getName(), event.getSign().getWorld());
		ChestShop.callEvent(currencySubtractEvent);
		
		CurrencyAddEvent currencyAddEvent = new CurrencyAddEvent(BigDecimal.valueOf(event.getPrice()), event.getClient());
		ChestShop.callEvent(currencyAddEvent);
	}
}
