
package com.Acrobot.ChestShop.Listeners.PostTransaction;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.Breeze.Utils.InventoryUtil;
import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Configuration.Properties;
import com.Acrobot.ChestShop.Events.ShopDestroyedEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Signs.ChestShopSign;
import com.Acrobot.ChestShop.Utils.uBlock;

/** @author Acrobot */
public class EmptyShopDeleter implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onTransaction(TransactionEvent event) {
		if (event.getTransactionType() != TransactionEvent.TransactionType.BUY) return;
		
		Inventory ownerInventory = event.getOwnerInventory();
		Sign sign = event.getSign();
		Chest connectedChest = uBlock.findConnectedChest(sign);
		
		if (!shopShouldBeRemoved(ownerInventory, event.getStock())) return;
		
		ShopDestroyedEvent destroyedEvent = new ShopDestroyedEvent(null, event.getSign(), connectedChest);
		ChestShop.callEvent(destroyedEvent);
		
		sign.getBlock().setType(Material.AIR);
		
		if (Properties.REMOVE_EMPTY_CHESTS && !ChestShopSign.isAdminShop(ownerInventory) && InventoryUtil.isEmpty(ownerInventory)) {
			connectedChest.getBlock().setType(Material.AIR);
		}
		else {
			ownerInventory.addItem(new ItemStack(Material.SIGN, 1));
		}
	}
	
	private static boolean shopShouldBeRemoved(Inventory inventory, ItemStack[] stock) {
		return Properties.REMOVE_EMPTY_SHOPS && !ChestShopSign.isAdminShop(inventory) && !InventoryUtil.hasItems(stock, inventory);
	}
}
