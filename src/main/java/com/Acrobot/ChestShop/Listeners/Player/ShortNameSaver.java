
package com.Acrobot.ChestShop.Listeners.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.Events.ShopCreatedEvent;
import com.Acrobot.ChestShop.Utils.uName;

/** @author Acrobot */
public class ShortNameSaver implements Listener {
	@EventHandler
	public static void onShopCreated(ShopCreatedEvent event) {
		uName.saveName(event.getPlayer().getName());
	}
}
