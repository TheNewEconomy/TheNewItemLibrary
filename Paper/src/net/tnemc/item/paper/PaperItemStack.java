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
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.attribute.SerialAttributeOperation;
import net.tnemc.item.attribute.SerialAttributeSlot;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.persistent.PersistentDataHolder;
import net.tnemc.item.providers.SkullProfile;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * PaperItemStack
 *
 * @author creatorfromhell
 * @since 0.1.7.5
 */
public class PaperItemStack implements AbstractItemStack<ItemStack> {

  private final PersistentDataHolder holder = new PersistentDataHolder();
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
  private boolean enchantGlint = false;
  private String rarity = "COMMON";

  private SerialItemData<ItemStack> data;

  //our locale stack
  private boolean dirty = false;
  private boolean debug = false;
  private ItemStack stack;

  @Override
  public PaperItemStack of(final String material, final int amount) {

    this.material = material;
    this.amount = amount;
    return this;
  }

  public PaperItemStack of(final PaperItemStack stack) {

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
  public PaperItemStack of(final ItemStack locale) {

    this.stack = locale;

    return PaperItemPlatform.PLATFORM.serializer(this.stack, this);
  }

  @Override
  public PaperItemStack of(final JSONObject json) throws ParseException {

    unserialize(json);

    return this;
  }

  /**
   * Sets the resistance properties of the item stack.
   *
   * @param resistence A set of resistance types.
   *
   * @return The updated item stack instance.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> resistence(final HashSet<String> resistence) {

    return null;
  }

  @Override
  public PaperItemStack flags(final List<String> flags) {

    this.dirty = true;
    this.flags.clear();
    this.flags.addAll(flags);
    return this;
  }

  @Override
  public PaperItemStack lore(final List<Component> lore) {

    this.dirty = true;
    this.lore.clear();
    this.lore.addAll(lore);
    return this;
  }

  @SuppressWarnings("removal")
  @Override
  public PaperItemStack attribute(final String name, final SerialAttribute attribute) {

    this.dirty = true;
    this.attributes.put(name, attribute);
    return this;
  }

  @SuppressWarnings("removal")
  @Override
  public PaperItemStack attribute(final Map<String, SerialAttribute> attributes) {

    this.dirty = true;

    this.attributes.clear();
    this.attributes.putAll(attributes);
    return this;
  }

  @Override
  public PaperItemStack enchant(final String enchantment, final int level) {

    this.dirty = true;
    enchantments.put(enchantment, level);
    return this;
  }

  @Override
  public PaperItemStack enchant(final Map<String, Integer> enchantments) {

    this.dirty = true;
    this.enchantments.clear();
    this.enchantments.putAll(enchantments);
    return this;
  }

  @Override
  public PaperItemStack enchant(final List<String> enchantments) {

    this.dirty = true;
    this.enchantments.clear();
    for(final String str : enchantments) {
      this.enchantments.put(str, 1);
    }
    return this;
  }

  @Override
  public PaperItemStack material(final String material) {

    this.dirty = true;
    this.material = material;
    return this;
  }

  @Override
  public PaperItemStack amount(final int amount) {

    this.dirty = true;
    this.amount = amount;
    return this;
  }

  public void setAmount(final int amount) {

    this.dirty = true;
    this.amount = amount;

    if(stack != null) {
      stack.setAmount(amount);
    }
  }

  @Override
  public PaperItemStack slot(final int slot) {

    this.dirty = true;
    this.slot = slot;
    return this;
  }

  @Override
  public PaperItemStack display(final Component display) {

    this.dirty = true;
    this.display = display;
    return this;
  }

  public PaperItemStack debug(final boolean debug) {

    this.debug = debug;
    return this;
  }

  /**
   * @param damage ignored
   *
   * @return this
   *
   * @deprecated Damage values not supported in modern mc, use the bukkit core for legacy.
   */
  @Override
  @Deprecated
  public PaperItemStack damage(final short damage) {

    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> profile(final SkullProfile profile) {

    this.dirty = true;
    this.profile = profile;
    return this;
  }

  @Override
  public PaperItemStack modelData(final int modelData) {

    this.dirty = true;
    this.customModelData = modelData;
    return this;
  }

  /**
   * Sets the model identifier for the item stack.
   *
   * @param model The model identifier.
   *
   * @return The updated item stack instance.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> model(final String model) {

    return null;
  }

  @Override
  public PaperItemStack unbreakable(final boolean unbreakable) {

    this.dirty = true;
    this.unbreakable = unbreakable;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> maxStack(final int maxStack) {

    this.dirty = true;
    this.maxStack = maxStack;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> hideTooltip(final boolean hideTooltip) {

    this.dirty = true;
    this.hideTooltip = hideTooltip;
    return this;
  }

  /**
   * Sets the tooltip style for the item stack.
   *
   * @param tooltipStyle The tooltip style identifier.
   *
   * @return The updated item stack instance.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> tooltipStyle(final @NotNull String tooltipStyle) {

    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchantGlint(final boolean enchantGlint) {

    this.dirty = true;
    this.enchantGlint = enchantGlint;
    return this;
  }

  /**
   * Sets the enchantability level of the item stack.
   *
   * @param enchantable The enchantability level.
   *
   * @return The updated item stack instance.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> enchantable(final int enchantable) {

    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> rarity(final String rarity) {

    this.dirty = true;
    this.rarity = rarity;
    return this;
  }

  /**
   * Sets whether the item stack can function as a glider.
   *
   * @param glider True if the item should act as a glider, false otherwise.
   *
   * @return The updated item stack instance.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> glider(final boolean glider) {

    return null;
  }

  /**
   * Sets the item that remains after the current item stack is used.
   *
   * @param remainder The remaining item stack.
   *
   * @return The updated item stack instance.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> remainder(final @Nullable AbstractItemStack<ItemStack> remainder) {

    return null;
  }

  @Override
  public PaperItemStack applyData(final SerialItemData<ItemStack> data) {

    this.dirty = true;
    this.data = data;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> applyPersistentHolder(final PersistentDataHolder newHolder, final boolean replaceOld) {

    if(replaceOld) {
      this.holder.getData().clear();
    }

    this.holder.getData().putAll(newHolder.getData());
    return this;
  }

  /**
   * Retrieves the resistance types applied to the item stack.
   *
   * @return A set of resistance types.
   *
   * @since 0.1.7.7
   */
  @Override
  public HashSet<String> resistence() {

    return null;
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
  public PersistentDataHolder persistentHolder() {

    return holder;
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
   * @return 0
   *
   * @deprecated Modern minecraft doesn't use damage values, use bukkit for legacy
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

  /**
   * Retrieves the model identifier of the item stack.
   *
   * @return The model identifier.
   *
   * @since 0.1.7.7
   */
  @Override
  public String model() {

    return "";
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

  /**
   * Retrieves the tooltip style of the item stack.
   *
   * @return The tooltip style identifier.
   *
   * @since 0.1.7.7
   */
  @Override
  public String tooltipStyle() {

    return "";
  }

  @Override
  public boolean enchantGlint() {

    return enchantGlint;
  }

  /**
   * Retrieves the enchantability level of the item stack.
   *
   * @return The enchantability level.
   *
   * @since 0.1.7.7
   */
  @Override
  public int enchantable() {

    return 0;
  }

  @Override
  public String rarity() {

    return rarity;
  }

  /**
   * Checks if the item stack functions as a glider.
   *
   * @return True if the item acts as a glider, otherwise false.
   *
   * @since 0.1.7.7
   */
  @Override
  public boolean glider() {

    return false;
  }

  /**
   * Retrieves the remaining item stack after the current stack is used.
   *
   * @return The remainder item stack.
   *
   * @since 0.1.7.7
   */
  @Override
  public AbstractItemStack<ItemStack> remainder() {

    return null;
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
   * Returns true if the provided item is similar to this. An item is similar if the basic
   * information is the same, except for the amount. What this includes: - material - display -
   * modelData - flags - lore - attributes - enchantments
   * <p>
   * What this does not include: - Item Data.
   *
   * @param compare The stack to compare.
   *
   * @return True if the two are similar, otherwise false.
   */
  @Override
  public boolean similar(final AbstractItemStack<? extends ItemStack> compare) {

    if(stack == null) return false;
    return similarStack((PaperItemStack)compare);
  }

  public static PaperItemStack locale(final ItemStack stack) {

    return new PaperItemStack().of(stack);
  }

  public boolean similarStack(final PaperItemStack stack) {

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

  @Override
  public void parse(final JSONHelper json) throws ParseException {

    this.dirty = true;

    // Basic properties
    slot(json.getInteger("slot"));
    of(json.getString("material"), json.getInteger("amount"));
    unbreakable(json.getBoolean("unbreakable"));

    // Optional display component
    if(json.has("display") && !json.isNull("display")) {
      display(JSONComponentSerializer.json().deserialize(json.getString("display")));
    }

    // Optional damage
    if(json.has("damage") && !json.isNull("damage")) {
      damage(json.getShort("damage"));
    }

    // Optional model data
    if(json.has("modelData") && !json.isNull("modelData")) {
      modelData(json.getInteger("modelData"));
    }

    // Optional lore
    if(json.has("lore") && !json.isNull("lore")) {

      final String[] loreArray = json.getString("lore").split(",");
      final List<Component> loreList = new LinkedList<>();
      for(final String lore : loreArray) {
        loreList.add(JSONComponentSerializer.json().deserialize(lore));
      }
      lore(loreList);
    }

    // Optional flags
    if(json.has("flags") && !json.isNull("flags")) {

      final String[] flagsArray = json.getString("flags").split(",");
      final List<String> flagsList = Arrays.asList(flagsArray);
      flags(flagsList);
    }

    // Enchantments
    if(json.has("enchantments") && !json.isNull("enchantments")) {


      final Map<String, Integer> enchantments = new HashMap<>();
      final JSONObject enchants = json.getJSON("enchantments");
      enchants.forEach((key, value)->{
        enchantments.put(key.toString(), Integer.valueOf(value.toString()));
      });

      enchant(enchantments);
    }

    // Attributes
    if(json.has("attributes") && !json.isNull("attributes")) {

      final JSONHelper attributesHelper = json.getHelper("attributes");
      final Map<String, SerialAttribute> attributes = new HashMap<>();

      attributesHelper.getObject().forEach((key, value)->{

        final JSONHelper modHelper = attributesHelper.getHelper(key.toString());
        final UUID id = modHelper.getUUID("id");
        final String name = modHelper.getString("name");
        final double amount = modHelper.getDouble("amount");
        final SerialAttributeOperation operation = SerialAttributeOperation.valueOf(modHelper.getString("operation"));

        final SerialAttribute modifier = new SerialAttribute(id, name, amount, operation);

        if(modHelper.has("slot") && !modHelper.isNull("slot")) {
          modifier.setSlot(EquipSlot.valueOf(modHelper.getString("slot")));
        }

        attributes.put(key.toString(), modifier);
      });

      attribute(attributes);
    }


    if(json.has("persistent-data")) {
      holder.readJSON(json.getJSON("persistent-data"), PaperItemPlatform.PLATFORM);
    }

    // Optional custom data
    if(json.has("data") && !json.isNull("data")) {

      //ItemPlatform.applyData(CustomData.fromJSON(helper.getJSON("data")));
    }
  }
}