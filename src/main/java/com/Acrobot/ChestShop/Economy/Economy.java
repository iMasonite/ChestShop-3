
package com.Acrobot.ChestShop.Economy;

import java.math.BigDecimal;

import org.bukkit.World;
import org.bukkit.inventory.Inventory;

import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Configuration.Properties;
import com.Acrobot.ChestShop.Events.Economy.CurrencyAddEvent;
import com.Acrobot.ChestShop.Events.Economy.CurrencyCheckEvent;
import com.Acrobot.ChestShop.Events.Economy.CurrencyFormatEvent;
import com.Acrobot.ChestShop.Events.Economy.CurrencySubtractEvent;
import com.Acrobot.ChestShop.Signs.ChestShopSign;

/** @author Acrobot Economy management */
public class Economy {
	public static String getServerAccountName() {
		return Properties.SERVER_ECONOMY_ACCOUNT;
	}
	
	public static boolean isOwnerEconomicallyActive(Inventory inventory) {
		return !ChestShopSign.isAdminShop(inventory) || !getServerAccountName().isEmpty();
	}
	
	public static boolean add(String name, World world, double amount) {
		CurrencyAddEvent event = new CurrencyAddEvent(BigDecimal.valueOf(amount), name, world);
		ChestShop.callEvent(event);
		
		return true;
	}
	
	public static boolean subtract(String name, World world, double amount) {
		CurrencySubtractEvent event = new CurrencySubtractEvent(BigDecimal.valueOf(amount), name, world);
		ChestShop.callEvent(event);
		
		return true;
	}
	
	public static boolean hasEnough(String name, World world, double amount) {
		CurrencyCheckEvent event = new CurrencyCheckEvent(BigDecimal.valueOf(amount), name, world);
		ChestShop.callEvent(event);
		
		return event.hasEnough();
	}
	
	public static String formatBalance(double amount) {
		CurrencyFormatEvent event = new CurrencyFormatEvent(BigDecimal.valueOf(amount));
		ChestShop.callEvent(event);
		
		return event.getFormattedAmount();
	}
}
