package net.swordsofvalor.rpgplus.iconmenu;

import java.util.Arrays;

import net.swordsofvalor.rpgplus.RPGPlus;
import net.swordsofvalor.rpgplus.util.synchronization.Scheduling;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IconMenu implements Listener {

	protected boolean exists;
	
	protected Player player;
	protected String name;
	protected int size;
	protected OptionClickEventHandler handler;
	
	protected String[] optionNames;
	protected ItemStack[] optionIcons;
	
	public IconMenu(Player p, String name, int size, OptionClickEventHandler handler) {
		exists = true;
		
		this.player = p;
		this.name = name;
		this.size = size;
		this.handler = handler;
		
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
	}
	
	public IconMenu(IconMenu menu) {
		this.player = menu.player;
		this.name = menu.name;
		this.size = menu.size;
		this.handler = menu.handler;
	}
	
    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = formatText(name);
        optionIcons[position] = setItemNameAndLore(icon, formatText(name), formatText(info));
        return this;
    }
	
    public IconMenu setOption(int position, ItemStack formattedIcon) {
    	optionNames[position] = formattedIcon.getItemMeta().getDisplayName();
    	optionIcons[position] = formattedIcon;
    	return this;
    }
    
	public void open() {
		register();
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++) {
			if (optionIcons[i] != null) {
				inventory.setItem(i, optionIcons[i]);
			}
		}
		player.openInventory(inventory);
		player.updateInventory();
	}
	
	public void register() {
		RPGPlus.getInstance().registerListener(this);
	}
	
	public void close() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.getInstance(), new Runnable() {
			public void run() {
				player.closeInventory();
			}
		}, 1);
	}

	public void destroy() {
		final IconMenu inst = this;
		exists = false;
		Scheduling.runTaskLater(new Runnable() {
			@Override
			public void run() {
				HandlerList.unregisterAll(inst);
			}
		}, 20);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name) && event.getWhoClicked().getName().equals(player.getName())) {
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if (slot >= 0 && slot < size && optionNames[slot] != null && exists) {
				OptionClickEvent e = new OptionClickEvent(player, slot, optionNames[slot], this);
				handler.onOptionClick(e);
			}
			destroy();
		}
	}

	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private Player player;
		private int position;
		private String name;
		private IconMenu menu;
		
		public OptionClickEvent(Player player, int position, String name, IconMenu menu) {
			this.player = player;
			this.position = position;
			this.name = ChatColor.stripColor(name);
			this.menu = menu;
		}
		
		public IconMenu getMenu() {
			return menu;
		}
		
		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}
	}
	
	/*
	 * @return the formatted text standard to RPGPlus IconMenus.
	 * uses the @ symbol to handle color/formatting such that:
	 * <code>@p</code> --> <code>ChatColor.AQUA + ChatColor.BOLD</code>
	 * <code>@s</code> --> <code>ChatColor.GREEN</code>
	 * <code>@g</code> --> <code>ChatColor.GRAY</code>
	 * <code>@e</code> --> <code>ChatColor.YELLOW</code>
	 */
	public static String formatText(String text) {
		return ChatColor.translateAlternateColorCodes('&',
				text.replace("@t", ChatColor.AQUA + "" + ChatColor.BOLD)
				   .replace("@s", ChatColor.GREEN + "")
				   .replace("@g", ChatColor.GRAY + "")
				   .replace("@e", ChatColor.YELLOW + ""));
	}
	
	/*
	 * @return an array of formatted text.
	 * @see #formatText(String)
	 */
	public static String[] formatText(String[] text) {
		for (int i = 0; i < text.length; i++) {
			text[i] = formatText(text[i]);
		}
		return text;
	}
	
	public static ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(formatText(name));
		im.setLore(Arrays.asList(formatText(lore)));
		item.setItemMeta(im);
		return item;
	}

}
