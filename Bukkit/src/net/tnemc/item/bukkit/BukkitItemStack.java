package net.tnemc.item.bukkit;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.providers.SkullProfile;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an ItemStack object related to the Bukkit API.
 */
public class BukkitItemStack implements AbstractItemStack<ItemStack> {

  private final List<String> flags = new ArrayList<>();
  private final Map<String, SerialAttribute> attributes = new HashMap<>();
  private final Map<String, Integer> enchantments = new HashMap<>();
  private final List<Component> lore = new ArrayList<>();

  private final Map<String, SerialComponent<ItemStack>> components = new HashMap<>();

  private int slot = 0;
  private SkullProfile profile = null;
  private String material;
  private Integer maxStack = 64;
  private Integer amount = 1;
  private Component display = Component.empty();
  private short damage = 0;
  private int customModelData = -1;
  private boolean unbreakable = false;
  private boolean hideTooltip = false;
  private boolean fireResistant = false;
  private boolean enchantGlint = false;
  private String rarity = "COMMON";
  private SerialItemData<ItemStack> data;

  //our locale stack
  private boolean dirty = false;
  private boolean debug = false;
  private ItemStack stack;

  @Override
  public BukkitItemStack of(String material, int amount) {
    this.material = material;
    this.amount = amount;
    return this;
  }

  @Override
  public BukkitItemStack of(SerialItem<ItemStack> serialItem) {

    final BukkitItemStack stack = (BukkitItemStack)serialItem.getStack();

    flags.addAll(stack.flags);
    attributes.putAll(stack.attributes);
    enchantments.putAll(stack.enchantments);
    lore.addAll(stack.lore);
    components.putAll(stack.components);

    slot = stack.slot;
    material = stack.material;
    maxStack = stack.maxStack;
    amount = stack.amount;
    display = stack.display;
    damage = stack.damage;
    customModelData = stack.customModelData;
    unbreakable = stack.unbreakable;
    hideTooltip = stack.hideTooltip;
    fireResistant = stack.fireResistant;
    enchantGlint = stack.enchantGlint;
    rarity = stack.rarity;

    data = stack.data;

    if(stack.profile != null) {
      this.profile = stack.profile;
    }

    this.stack = stack.stack;

    return this;
  }

  @Override
  public BukkitItemStack of(ItemStack locale) {
    this.stack = locale;

    return BukkitItemPlatform.PLATFORM.deserialize(this.stack, this);
  }

  @Override
  public BukkitItemStack of(JSONObject json) throws ParseException {
    final Optional<SerialItem<ItemStack>> serialStack = SerialItem.unserialize(json);

    if(serialStack.isPresent()) {
      return of(serialStack.get());
    }
    return this;
  }

  @Override
  public BukkitItemStack flags(List<String> flags) {
    this.flags.clear();
    this.flags.addAll(flags);
    return this;
  }

  @Override
  public BukkitItemStack lore(List<Component> lore) {
    this.lore.clear();
    this.lore.addAll(lore);
    return this;
  }

  @Override
  public BukkitItemStack attribute(String name, SerialAttribute attribute) {
    attributes.put(name, attribute);
    return this;
  }

  @Override
  public BukkitItemStack attribute(Map<String, SerialAttribute> attributes) {

    this.attributes.clear();
    this.attributes.putAll(attributes);
    return this;
  }

  @Override
  public BukkitItemStack enchant(String enchantment, int level) {
    enchantments.put(enchantment, level);
    return this;
  }

  @Override
  public BukkitItemStack enchant(Map<String, Integer> enchantments) {
    this.enchantments.clear();
    this.enchantments.putAll(enchantments);
    return this;
  }

  @Override
  public BukkitItemStack enchant(List<String> enchantments) {
    this.enchantments.clear();
    for(String str : enchantments) {
      this.enchantments.put(str, 1);
    }
    return this;
  }

  @Override
  public BukkitItemStack material(String material) {
    this.material = material;
    return this;
  }

  @Override
  public BukkitItemStack amount(int amount) {
    this.amount = amount;
    return this;
  }

  public void setAmount(int amount) {
    this.amount = amount;

    if(stack != null) {
      stack.setAmount(amount);
    }
  }

  @Override
  public BukkitItemStack slot(int slot) {
    this.slot = slot;
    return this;
  }

  @Override
  public BukkitItemStack display(Component display) {
    this.display = display;
    return this;
  }

  @Override
  public BukkitItemStack damage(short damage) {
    this.damage = damage;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> profile(SkullProfile profile) {
    this.profile = profile;
    return this;
  }

  @Override
  public BukkitItemStack modelData(int modelData) {
    this.customModelData = modelData;
    return this;
  }

  @Override
  public BukkitItemStack unbreakable(boolean unbreakable) {
    this.unbreakable = unbreakable;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> maxStack(int maxStack) {
    this.maxStack = maxStack;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> hideTooltip(boolean hideTooltip) {
    this.hideTooltip = hideTooltip;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> fireResistant(boolean fireResistant) {
    this.fireResistant = fireResistant;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> enchantGlint(boolean enchantGlint) {
    this.enchantGlint = enchantGlint;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> rarity(String rarity) {
    this.rarity = rarity;
    return this;
  }

  public BukkitItemStack debug(boolean debug) {
    this.debug = debug;
    return this;
  }

  @Override
  public BukkitItemStack applyData(SerialItemData<ItemStack> data) {
    this.data = data;
    return this;
  }

  @Override
  public List<String> flags() {
    return flags;
  }

  @Override
  public List<Component> lore() {
    return lore;
  }

  @Override
  public Map<String, SerialAttribute> attributes() {
    return attributes;
  }

  @Override
  public Map<String, Integer> enchantments() {
    return enchantments;
  }

  @Override
  public Map<String, SerialComponent<ItemStack>> components() {
    return components;
  }

  @Override
  public String material() {
    return material;
  }

  @Override
  public int amount() {
    return amount;
  }

  @Override
  public int slot() {
    return slot;
  }

  public String displayPlain() {
    return PlainTextComponentSerializer.plainText().serialize(display);
  }

  @Override
  public Component display() {
    return display;
  }

  @Override
  public short damage() {
    return damage;
  }

  @Override
  public Optional<SkullProfile> profile() {
    return Optional.ofNullable(profile);
  }

  @Override
  public int modelData() {
    return customModelData;
  }

  @Override
  public boolean unbreakable() {
    return unbreakable;
  }

  @Override
  public int maxStack() {
    return maxStack;
  }

  @Override
  public boolean hideTooltip() {
    return hideTooltip;
  }

  @Override
  public boolean fireResistant() {
    return fireResistant;
  }

  @Override
  public boolean enchantGlint() {
    return enchantGlint;
  }

  @Override
  public String rarity() {
    return rarity;
  }

  @Override
  public void markDirty() {
    this.dirty = true;
  }

  @Override
  public Optional<SerialItemData<ItemStack>> data() {
    return Optional.ofNullable(data);
  }

  /**
   * Returns true if the provided item is similar to this.
   * An item is similar if the basic information is the same, except for the amount.
   * What this includes:
   * - material
   * - display
   * - modelData
   * - flags
   * - lore
   * - attributes
   * - enchantments
   * <p>
   * What this does not include:
   * - Item Data.
   *
   * @param compare The stack to compare.
   *
   * @return True if the two are similar, otherwise false.
   */
  @Override
  public boolean similar(AbstractItemStack<? extends ItemStack> compare) {
    if(stack == null) return false;
    return similarStack((BukkitItemStack)compare);
  }

  public static BukkitItemStack locale(ItemStack stack) {
    return new BukkitItemStack().of(stack);
  }

  public boolean similarStack(BukkitItemStack stack) {

    return BukkitItemPlatform.PLATFORM.check(this, stack);
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    if(stack == null || dirty) {

      Material material = null;
      try {
        final NamespacedKey key = NamespacedKey.fromString(this.material);
        if(key != null) {
          material = Registry.MATERIAL.get(key);
        }
      } catch(Exception ignore) {
        material = Material.matchMaterial(this.material);
      }

      if(material != null) {

        stack = new ItemStack(material, amount);

        stack = BukkitItemPlatform.PLATFORM.apply(this, stack);
      }
    }
    return stack;
  }
}