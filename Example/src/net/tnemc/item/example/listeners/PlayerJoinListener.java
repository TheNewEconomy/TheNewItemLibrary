package net.tnemc.item.example.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.tnemc.item.example.Example;

import java.util.Collections;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {

    Example.instance().getPlatform().calculations().giveItems(Collections.singletonList(Example.instance().getItem()), event.getPlayer().getUniqueId());
  }
}
