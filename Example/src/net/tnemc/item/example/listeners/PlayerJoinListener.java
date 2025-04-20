package net.tnemc.item.example.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import net.tnemc.item.example.Example;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {

    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getItem()), event.getPlayer().getUniqueId());
    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getPlatform().createStack("paper")
                                                                                                .setProviderItemID("forest_axe")
                                                                                                .setItemProvider("nexo")), event.getPlayer().getUniqueId());

    //event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
  }
}
