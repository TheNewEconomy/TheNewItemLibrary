package net.tnemc.item.paper;

/*
 * The New Item Library
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
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.paper.platform.PaperItemPlatform;
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
 * PaperItemStack
 *
 * @author creatorfromhell
 * @since 0.1.7.5
 */
public class PaperItemStack implements AbstractItemStack<ItemStack> {

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
  public PaperItemStack of(String material, int amount) {
    this.material = material;
    this.amount = amount;
    return this;
  }

  @Override
  public PaperItemStack of(SerialItem<ItemStack> serialItem) {

    final PaperItemStack stack = (PaperItemStack)serialItem.getStack();

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
    customModelData = stack.customModelData;
    unbreakable = stack.unbreakable;
    hideTooltip = stack.hideTooltip;
    fireResistant = stack.fireResistant;
    enchantGlint = stack.enchantGlint;
    rarity = stack.rarity;

    if(stack.profile != null) {
      this.profile = stack.profile;
    }
    this.data = stack.data;

    this.stack = stack.stack;

    return this;
  }

  @Override
  public PaperItemStack of(ItemStack locale) {
    this.stack = locale;

    return PaperItemPlatform.PLATFORM.deserialize(this.stack, this);
  }

  @Override
  public PaperItemStack of(JSONObject json) throws ParseException {
    final Optional<SerialItem<ItemStack>> serialStack = SerialItem.unserialize(json);

    if(serialStack.isPresent()) {
      return of(serialStack.get());
    }
    return this;
  }

  @Override
  public PaperItemStack flags(List<String> flags) {
    this.dirty = true;
    this.flags.clear();
    this.flags.addAll(flags);
    return this;
  }

  @Override
  public PaperItemStack lore(List<Component> lore) {
    this.dirty = true;
    this.lore.clear();
    this.lore.addAll(lore);
    return this;
  }

  @SuppressWarnings("removal")
  @Override
  public PaperItemStack attribute(String name, SerialAttribute attribute) {
    this.dirty = true;
    this.attributes.put(name, attribute);
    return this;
  }

  @SuppressWarnings("removal")
  @Override
  public PaperItemStack attribute(Map<String, SerialAttribute> attributes) {
    this.dirty = true;

    this.attributes.clear();
    this.attributes.putAll(attributes);
    return this;
  }

  @Override
  public PaperItemStack enchant(String enchantment, int level) {
    this.dirty = true;
    enchantments.put(enchantment, level);
    return this;
  }

  @Override
  public PaperItemStack enchant(Map<String, Integer> enchantments) {
    this.dirty = true;
    this.enchantments.clear();
    this.enchantments.putAll(enchantments);
    return this;
  }

  @Override
  public PaperItemStack enchant(List<String> enchantments) {
    this.dirty = true;
    this.enchantments.clear();
    for(String str : enchantments) {
      this.enchantments.put(str, 1);
    }
    return this;
  }

  @Override
  public PaperItemStack material(String material) {
    this.dirty = true;
    this.material = material;
    return this;
  }

  @Override
  public PaperItemStack amount(int amount) {
    this.dirty = true;
    this.amount = amount;
    return this;
  }

  public void setAmount(int amount) {
    this.dirty = true;
    this.amount = amount;

    if(stack != null) {
      stack.setAmount(amount);
    }
  }

  @Override
  public PaperItemStack slot(int slot) {
    this.dirty = true;
    this.slot = slot;
    return this;
  }

  @Override
  public PaperItemStack display(Component display) {
    this.dirty = true;
    this.display = display;
    return this;
  }

  public PaperItemStack debug(boolean debug) {
    this.debug = debug;
    return this;
  }

  /**
   * @deprecated Damage values not supported in modern mc, use the bukkit core for legacy.
   * @param damage ignored
   * @return this
   */
  @Override
  @Deprecated
  public PaperItemStack damage(short damage) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> profile(SkullProfile profile) {
    this.dirty = true;
    this.profile = profile;
    return this;
  }

  @Override
  public PaperItemStack modelData(int modelData) {
    this.dirty = true;
    this.customModelData = modelData;
    return this;
  }

  @Override
  public PaperItemStack unbreakable(boolean unbreakable) {
    this.dirty = true;
    this.unbreakable = unbreakable;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> maxStack(int maxStack) {
    this.dirty = true;
    this.maxStack = maxStack;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> hideTooltip(boolean hideTooltip) {
    this.dirty = true;
    this.hideTooltip = hideTooltip;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> fireResistant(boolean fireResistant) {
    this.dirty = true;
    this.fireResistant = fireResistant;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> enchantGlint(boolean enchantGlint) {
    this.dirty = true;
    this.enchantGlint = enchantGlint;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> rarity(String rarity) {
    this.dirty = true;
    this.rarity = rarity;
    return this;
  }

  @Override
  public PaperItemStack applyData(SerialItemData<ItemStack> data) {
    this.dirty = true;
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

  /**
   * @deprecated Modern minecraft doesn't use damage values, use bukkit for legacy
   * @return 0
   */
  @Override
  @Deprecated
  public short damage() {
    return 0;
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

  public boolean debug() {
    return debug;
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
    return similarStack((PaperItemStack)compare);
  }

  public static PaperItemStack locale(ItemStack stack) {
    return new PaperItemStack().of(stack);
  }

  public boolean similarStack(PaperItemStack stack) {

    return PaperItemPlatform.PLATFORM.check(this, stack);
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    if(stack == null || dirty) {

      final NamespacedKey key = NamespacedKey.fromString(material);

      if(key != null) {

        final Material material = Registry.MATERIAL.get(key);
        if(material != null) {

          stack = new ItemStack(material, amount);

          stack = PaperItemPlatform.PLATFORM.apply(this, stack);
        }
      }
    }
    return stack;
  }
}