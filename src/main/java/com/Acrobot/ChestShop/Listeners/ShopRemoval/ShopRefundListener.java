
package com.Acrobot.ChestShop.Listeners.ShopRemoval;

import static com.Acrobot.ChestShop.Permission.NOFEE;
import static com.Acrobot.ChestShop.Signs.ChestShopSign.NAME_LINE;

import java.math.BigDecimal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Permission;
import com.Acrobot.ChestShop.Configuration.Messages;
import com.Acrobot.ChestShop.Configuration.Properties;
import com.Acrobot.ChestShop.Economy.Economy;
import com.Acrobot.ChestShop.Events.ShopDestroyedEvent;
import com.Acrobot.ChestShop.Events.Economy.CurrencyAddEvent;
import com.Acrobot.ChestShop.Utils.uName;

/** @author Acrobot */
public class ShopRefundListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	public static void onShopDestroy(ShopDestroyedEvent event) {
		double refundPrice = Properties.SHOP_REFUND_PRICE;
		
		if (event.getDestroyer() == null || Permission.has(event.getDestroyer(), NOFEE) || refundPrice == 0) return;
		
		String owner = uName.getName(event.getSign().getLine(NAME_LINE));
		
		CurrencyAddEvent currencyEvent = new CurrencyAddEvent(BigDecimal.valueOf(refundPrice), owner, event.getSign().getWorld());
		ChestShop.callEvent(currencyEvent);
		
		String message = Messages.SHOP_REFUNDED.replace("%amount", Economy.formatBalance(refundPrice));
		event.getDestroyer().sendMessage(Messages.prefix(message));
	}
}
