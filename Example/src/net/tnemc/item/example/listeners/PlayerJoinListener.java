package net.tnemc.item.example.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import net.tnemc.item.example.Example;

import java.util.Arrays;
import java.util.Collections;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {

    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getItem()), event.getPlayer().getUniqueId());
    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getPlatform().createStack().material("paper")
                                                                                                .setProviderItemID("forest_axe")
                                                                                                .setItemProvider("nexo")), event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onClick(final PlayerInteractEvent event) {
    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getPlatform().createStack().material("paper")
                                                                                                .setProviderItemID("forest_axe")
                                                                                                .setItemProvider("nexo")), event.getPlayer().getUniqueId());
  }
}
