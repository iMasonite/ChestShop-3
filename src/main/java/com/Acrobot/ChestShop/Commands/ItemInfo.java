
package com.Acrobot.ChestShop.Commands;

import static com.Acrobot.ChestShop.Configuration.Messages.iteminfo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.Breeze.Utils.MaterialUtil;
import com.Acrobot.Breeze.Utils.StringUtil;
import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Configuration.Messages;
import com.Acrobot.ChestShop.Events.ItemInfoEvent;

/** @author Acrobot */
public class ItemInfo implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ItemStack item;
		
		if (args.length == 0) {
			if (!(sender instanceof HumanEntity)) return false;
			
			item = ((HumanEntity) sender).getItemInHand();
		}
		else {
			item = MaterialUtil.getItem(StringUtil.joinArray(args));
		}
		
		if (MaterialUtil.isEmpty(item)) return false;
		
		String durability = getDurability(item);
		String metadata = getMetadata(item);
		
		sender.sendMessage(Messages.prefix(iteminfo));
		sender.sendMessage(getNameAndID(item) + durability + metadata + ChatColor.WHITE);
		
		ItemInfoEvent event = new ItemInfoEvent(sender, item);
		ChestShop.callEvent(event);
		
		return true;
	}
	
	private static String getNameAndID(ItemStack item) {
		String itemName = MaterialUtil.getName(item);
		
		return ChatColor.GRAY + itemName + ChatColor.WHITE + "      " + item.getTypeId();
	}
	
	private static String getDurability(ItemStack item) {
		if (item.getDurability() != 0) return ChatColor.DARK_GREEN + ":" + Integer.toString(item.getDurability());
		else return "";
	}
	
	private static String getMetadata(ItemStack item) {
		if (!item.hasItemMeta()) return "";
		
		return ChatColor.GOLD + "#" + MaterialUtil.Metadata.getItemCode(item);
	}
}
