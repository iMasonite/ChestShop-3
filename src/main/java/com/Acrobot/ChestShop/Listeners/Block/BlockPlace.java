
package com.Acrobot.ChestShop.Listeners.Block;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.Acrobot.Breeze.Utils.BlockUtil;
import com.Acrobot.ChestShop.Permission;
import com.Acrobot.ChestShop.Security;
import com.Acrobot.ChestShop.Configuration.Messages;
import com.Acrobot.ChestShop.Signs.ChestShopSign;
import com.Acrobot.ChestShop.Utils.uBlock;

/** @author Acrobot */
public class BlockPlace implements Listener {
	@EventHandler(ignoreCancelled = true)
	public static void onChestPlace(BlockPlaceEvent event) {
		Block placed = event.getBlockPlaced();
		
		if (!BlockUtil.isChest(placed)) return;
		
		Player player = event.getPlayer();
		
		if (Permission.has(player, Permission.ADMIN)) return;
		
		if (!Security.canAccess(player, placed)) {
			event.getPlayer().sendMessage(Messages.prefix(Messages.ACCESS_DENIED));
			event.setCancelled(true);
		}
		
		Chest neighbor = uBlock.findNeighbor(placed);
		
		if (neighbor != null && !Security.canAccess(event.getPlayer(), neighbor.getBlock())) {
			event.getPlayer().sendMessage(Messages.prefix(Messages.ACCESS_DENIED));
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler(ignoreCancelled = true)
	public static void onPlaceAgainstSign(BlockPlaceEvent event) {
		Block against = event.getBlockAgainst();
		
		if (!ChestShopSign.isValid(against)) return;
		
		event.setCancelled(true);
	}
	
}
