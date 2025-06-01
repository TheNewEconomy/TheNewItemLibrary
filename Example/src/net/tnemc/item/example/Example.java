package net.tnemc.item.example;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.example.command.TNILCountCommand;
import net.tnemc.item.example.command.TNILGiveCommand;
import net.tnemc.item.example.command.TNILTakeCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.tnemc.item.example.listeners.PlayerJoinListener;

import java.awt.print.Paper;
import java.util.Arrays;

public class Example extends JavaPlugin {

  private static Example instance;

  PaperItemPlatform paperPlatform;
  BukkitItemPlatform bukkitPlatform;
  PaperItemStack item;
  PaperItemStack nexoItem;

  @Override
  public void onEnable() {

    this.bukkitPlatform = BukkitItemPlatform.instance();
    this.paperPlatform = PaperItemPlatform.instance();
    this.item = this.build();

    this.nexoItem = this.buildNexo();
    instance = this;

    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

    getCommand("tnilcount").setExecutor(new TNILCountCommand());
    getCommand("tniltake").setExecutor(new TNILTakeCommand());
    getCommand("tnilgive").setExecutor(new TNILGiveCommand());
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  private PaperItemStack build() {
    return this.paperPlatform.createStack("minecraft:gold_ingot")
            .itemName(MiniMessage.miniMessage().deserialize("<gold>Test"))
            .lore(Arrays.asList(
                    MiniMessage.miniMessage().deserialize("<gradient:#5e4fa2:#f79459>This is a test item from the new TNIL library"),
                    MiniMessage.miniMessage().deserialize("<gradient:#5e4fa2:#f79459>This currency item can only be stacked to 10")
            ))
            .maxStackSize(10)
            .amount(10);


            //.itemName(MiniMessage.miniMessage().deserialize("<gold>Test"))
            //.lore(Arrays.asList(
            //        MiniMessage.miniMessage().deserialize("<gradient:#5e4fa2:#f79459>This is a test item from the new TNIL library"),
            //        MiniMessage.miniMessage().deserialize("<gradient:#5e4fa2:#f79459>This currency item can only be stacked to 10")
            //                   ))
            //.maxStackSize(10)
  }

  private PaperItemStack buildNexo() {
    return this.paperPlatform.createStack("diamond")
            .setProviderItemID("forest_axe")
            .setItemProvider("nexo");
  }

  public PaperItemPlatform getPlatform() {
    return paperPlatform;
  }

  public PaperItemStack getItem() {
    return item;
  }

  public PaperItemStack getNexoItem() {
    return nexoItem;
  }

  public static Example instance() {
    return instance;
  }
}