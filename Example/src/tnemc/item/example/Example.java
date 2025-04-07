package tnemc.item.example;

import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.platform.ItemPlatform;
import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin {

    ItemPlatform<?, ?> platform;

    @Override
    public void onEnable() {

        this.platform = BukkitItemPlatform.instance();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}