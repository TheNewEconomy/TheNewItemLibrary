package tnemc.item.example;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.platform.ItemPlatform;
import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin {

  ItemPlatform<?, ?, ?> platform;
  AbstractItemStack<?> item;

  @Override
  public void onEnable() {

    this.platform = BukkitItemPlatform.instance();
    this.item = this.build();
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  private AbstractItemStack<?> build() {
    return this.platform.createStack().material("GOLD_INGOT").amount(1).itemName(LegacyComponentSerializer.builder().build().deserialize(""));
  }
}