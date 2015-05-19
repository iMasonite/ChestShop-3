
package com.Acrobot.ChestShop.Listeners.PostShopCreation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.Configuration.Messages;
import com.Acrobot.ChestShop.Events.ShopCreatedEvent;

/** @author Acrobot */
public class MessageSender implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public static void onShopCreation(ShopCreatedEvent event) {
		event.getPlayer().sendMessage(Messages.prefix(Messages.SHOP_CREATED));
	}
}
