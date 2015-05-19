
package com.Acrobot.ChestShop.Listeners.PreShopCreation;

import static com.Acrobot.ChestShop.Events.PreShopCreationEvent.CreationOutcome.NO_PERMISSION_FOR_TERRAIN;
import static com.Acrobot.ChestShop.Permission.ADMIN;
import static com.Acrobot.ChestShop.Signs.ChestShopSign.NAME_LINE;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Permission;
import com.Acrobot.ChestShop.Security;
import com.Acrobot.ChestShop.Events.PreShopCreationEvent;
import com.Acrobot.ChestShop.Events.Protection.BuildPermissionEvent;
import com.Acrobot.ChestShop.Signs.ChestShopSign;
import com.Acrobot.ChestShop.Utils.uBlock;

/** @author Acrobot */
public class TerrainChecker implements Listener {
	
	@EventHandler
	public static void onPreShopCreation(PreShopCreationEvent event) {
		String nameLine = event.getSignLine(NAME_LINE);
		
		if (ChestShopSign.isAdminShop(nameLine)) return;
		
		Player player = event.getPlayer();
		
		if (Permission.has(player, ADMIN)) return;
		
		if (!Security.canPlaceSign(player, event.getSign())) {
			event.setOutcome(NO_PERMISSION_FOR_TERRAIN);
			return;
		}
		
		Chest connectedChest = uBlock.findConnectedChest(event.getSign().getBlock());
		Location chestLocation = (connectedChest != null ? connectedChest.getLocation() : null);
		
		BuildPermissionEvent bEvent = new BuildPermissionEvent(player, chestLocation, event.getSign().getLocation());
		ChestShop.callEvent(bEvent);
		
		if (!bEvent.isAllowed()) {
			event.setOutcome(NO_PERMISSION_FOR_TERRAIN);
		}
		
	}
}
