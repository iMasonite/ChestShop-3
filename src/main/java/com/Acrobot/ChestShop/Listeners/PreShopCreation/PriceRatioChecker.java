
package com.Acrobot.ChestShop.Listeners.PreShopCreation;

import static com.Acrobot.Breeze.Utils.PriceUtil.hasBuyPrice;
import static com.Acrobot.Breeze.Utils.PriceUtil.hasSellPrice;
import static com.Acrobot.ChestShop.Events.PreShopCreationEvent.CreationOutcome.SELL_PRICE_HIGHER_THAN_BUY_PRICE;
import static com.Acrobot.ChestShop.Signs.ChestShopSign.PRICE_LINE;
import static org.bukkit.event.EventPriority.HIGH;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.Breeze.Utils.PriceUtil;
import com.Acrobot.ChestShop.Events.PreShopCreationEvent;

/** @author Acrobot */
public class PriceRatioChecker implements Listener {
	
	@EventHandler(priority = HIGH)
	public static void onPreShopCreation(PreShopCreationEvent event) {
		String priceLine = event.getSignLine(PRICE_LINE);
		
		double buyPrice = PriceUtil.getBuyPrice(priceLine);
		double sellPrice = PriceUtil.getSellPrice(priceLine);
		
		if (hasBuyPrice(priceLine) && hasSellPrice(priceLine) && sellPrice > buyPrice) {
			event.setOutcome(SELL_PRICE_HIGHER_THAN_BUY_PRICE);
		}
	}
}
