
package com.Acrobot.ChestShop.Listeners.PreTransaction;

import static com.Acrobot.ChestShop.Events.PreTransactionEvent.TransactionOutcome.NOT_ENOUGH_SPACE_IN_CHEST;
import static com.Acrobot.ChestShop.Events.PreTransactionEvent.TransactionOutcome.NOT_ENOUGH_SPACE_IN_INVENTORY;
import static com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType.BUY;
import static com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType.SELL;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.Breeze.Utils.InventoryUtil;
import com.Acrobot.ChestShop.Events.PreTransactionEvent;

/** @author Acrobot */
public class StockFittingChecker implements Listener {
	@EventHandler
	public static void onSellCheck(PreTransactionEvent event) {
		if (event.isCancelled() || event.getTransactionType() != SELL) return;
		
		Inventory shopInventory = event.getOwnerInventory();
		ItemStack[] stock = event.getStock();
		
		if (!itemsFitInInventory(stock, shopInventory)) {
			event.setCancelled(NOT_ENOUGH_SPACE_IN_CHEST);
		}
	}
	
	@EventHandler
	public static void onBuyCheck(PreTransactionEvent event) {
		if (event.isCancelled() || event.getTransactionType() != BUY) return;
		
		Inventory clientInventory = event.getClientInventory();
		ItemStack[] stock = event.getStock();
		
		if (!itemsFitInInventory(stock, clientInventory)) {
			event.setCancelled(NOT_ENOUGH_SPACE_IN_INVENTORY);
		}
	}
	
	private static boolean itemsFitInInventory(ItemStack[] items, Inventory inventory) {
		for (ItemStack item : items) {
			if (!InventoryUtil.fits(item, inventory)) return false;
		}
		
		return true;
	}
}
