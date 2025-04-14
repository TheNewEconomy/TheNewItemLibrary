package net.tnemc.item.example;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.tnemc.item.example.listeners.PlayerJoinListener;

public class Example extends JavaPlugin {

  private static Example instance;

  BukkitItemPlatform platform;
  BukkitItemStack item;

  @Override
  public void onEnable() {

    this.platform = BukkitItemPlatform.instance();
    this.item = this.build();
    instance = this;

    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  private BukkitItemStack build() {
    return this.platform.createStack().material("GOLD_INGOT").amount(1).itemName(LegacyComponentSerializer.builder().build().deserialize(""));
  }

  public BukkitItemPlatform getPlatform() {
    return platform;
  }

  public BukkitItemStack getItem() {
    return item;
  }

  public static Example instance() {
    return instance;
  }
}