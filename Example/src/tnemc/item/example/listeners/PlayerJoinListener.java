package tnemc.item.example.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tnemc.item.example.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PlayerJoinListener implements Listener {

  public void onJoin(final PlayerJoinEvent event) {

    Example.instance().getPlatform().calculations().giveItems(Collections.synchronizedCollection(Example.instance().getItem()), event.getPlayer().getUniqueId());
  }
}
