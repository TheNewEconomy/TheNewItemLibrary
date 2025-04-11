package tnemc.item.example;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.platform.ItemPlatform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tnemc.item.example.listeners.PlayerJoinListener;

public class Example extends JavaPlugin {

  private static Example instance;

  ItemPlatform<?, ?, ?> platform;
  AbstractItemStack<?> item;

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

  private AbstractItemStack<?> build() {
    return this.platform.createStack().material("GOLD_INGOT").amount(1).itemName(LegacyComponentSerializer.builder().build().deserialize(""));
  }

  public ItemPlatform<?, ?, ?> getPlatform() {
    return platform;
  }

  public AbstractItemStack<?> getItem() {
    return item;
  }

  public static Example instance() {
    return instance;
  }
}