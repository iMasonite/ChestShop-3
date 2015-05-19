
package com.Acrobot.ChestShop;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import com.Acrobot.Breeze.Utils.MaterialUtil;
import com.Acrobot.ChestShop.Configuration.Properties;
import com.Acrobot.ChestShop.Listeners.Economy.Plugins.RegisterListener;
import com.Acrobot.ChestShop.Listeners.Economy.Plugins.VaultListener;
import com.Acrobot.ChestShop.Plugins.WorldGuardBuilding;
import com.Acrobot.ChestShop.Plugins.WorldGuardProtection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/** @author Acrobot */
public class Dependencies {
	public static void loadPlugins() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		
		for (String dependency : ChestShop.getDependencies()) {
			Plugin plugin = pluginManager.getPlugin(dependency);
			
			if (plugin != null) {
				initializePlugin(dependency, plugin);
			}
		}
		
		loadEconomy();
	}
	
	private static void loadEconomy() {
		String plugin = "Vault";
		Listener economy = VaultListener.initializeVault();
		
		if (economy == null) {
			plugin = "Register";
			economy = RegisterListener.initializeRegister();
		}
		
		if (economy == null) return;
		
		ChestShop.registerListener(economy);
		ChestShop.getBukkitLogger().info(plugin + " loaded! Found an economy plugin!");
	}
	
	private static void initializePlugin(String name, Plugin plugin) { // Really messy, right? But
																																			// it's short and fast :)
		Dependency dependency;
		
		try {
			dependency = Dependency.valueOf(name);
		}
		catch (IllegalArgumentException exception) {
			return;
		}
		
		Listener listener = null;
		
		switch (dependency) {
			case WorldGuard:
				WorldGuardPlugin worldGuard = (WorldGuardPlugin) plugin;
				boolean inUse = Properties.WORLDGUARD_USE_PROTECTION || Properties.WORLDGUARD_INTEGRATION;
				
				if (!inUse) return;
				
				if (Properties.WORLDGUARD_USE_PROTECTION) {
					ChestShop.registerListener(new WorldGuardProtection(worldGuard));
				}
				
				if (Properties.WORLDGUARD_INTEGRATION) {
					listener = new WorldGuardBuilding(worldGuard);
				}
				
				break;
			
			// Other plugins
			case OddItem:
				MaterialUtil.Odd.initialize();
				break;
		}
		
		if (listener != null) {
			ChestShop.registerListener(listener);
		}
		
		PluginDescriptionFile description = plugin.getDescription();
		ChestShop.getBukkitLogger().info(description.getName() + " version " + description.getVersion() + " loaded.");
	}
	
	private static enum Dependency {
		OddItem,
		WorldGuard
	}
}
