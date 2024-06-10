package net.tnemc.item.bukkit;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.*;

/**
 * Represents an ItemStack object related to the Bukkit API.
 */
public class BukkitItemStack implements AbstractItemStack<ItemStack> {

  private final List<String> flags = new ArrayList<>();
  private final Map<String, AttributeModifier> attributes = new HashMap<>();
  private final Map<String, Integer> enchantments = new HashMap<>();
  private final List<String> lore = new ArrayList<>();

  private int slot = 0;
  private Material material;
  private Integer amount = 1;
  private String display = "";
  private short damage = 0;
  private int customModelData = -1;
  private boolean unbreakable = false;
  private SerialItemData<ItemStack> data;

  //our locale stack
  private boolean dirty = false;
  private ItemStack stack;

  @Override
  public BukkitItemStack of(String material, int amount) {
    this.material = Material.getMaterial(material);
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

    slot = stack.slot;
    material = stack.material;
    amount = stack.amount;
    display = stack.display;
    damage = stack.damage;
    customModelData = stack.customModelData;
    unbreakable = stack.unbreakable;
    data = stack.data;
    this.stack = stack.stack;

    return this;
  }

  @Override
  public BukkitItemStack of(ItemStack locale) {
    this.stack = locale;

    material = stack.getType();
    amount = stack.getAmount();
    damage = stack.getDurability();

    if(stack.hasItemMeta() && stack.getItemMeta()!= null) {
      display = stack.getItemMeta().getDisplayName();

      if(stack.getItemMeta().getLore() != null) {
        lore.clear();
        lore.addAll(stack.getItemMeta().getLore());
      }

      // Check 1.13 version for compatibility with customModelData
      if(VersionUtil.isOneFourteen(Bukkit.getServer().getBukkitVersion().split("-")[0]) && stack.getItemMeta().hasCustomModelData()) {
        customModelData = stack.getItemMeta().getCustomModelData();
      }

      for(ItemFlag flag : stack.getItemMeta().getItemFlags()) {
        flags.add(flag.name());
      }

      if(VersionUtil.isOneThirteen(Bukkit.getServer().getBukkitVersion().split("-")[0]) && stack.getItemMeta().getAttributeModifiers() != null) {
        stack.getItemMeta().getAttributeModifiers().forEach((attr, modifier)->attributes.put(attr.name(), modifier));
      }

      if(stack.getItemMeta().hasEnchants()) {

        stack.getItemMeta().getEnchants().forEach(((enchantment, level) ->enchantments.put(enchantment.getKey().toString(), level)));
      }
    }

    //Parse the meta data.
    ParsingUtil.parseMeta(locale)
               .ifPresent(itemStackSerialItemData->this.data = itemStackSerialItemData);

    return this;
  }

  @Override
  public BukkitItemStack of(JSONObject json) {

    try {
      final Optional<SerialItem<ItemStack>> serialStack = SerialItem.unserialize(json);

      if(serialStack.isPresent()) {
        return of(serialStack.get());
      }
    } catch(ParseException e) {
      e.printStackTrace();
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
  public BukkitItemStack lore(List<String> lore) {
    this.lore.clear();
    this.lore.addAll(lore);
    return this;
  }

  @Override
  public BukkitItemStack attribute(String name, SerialAttribute attribute) {
    final AttributeModifier attr = new AttributeModifier(attribute.getIdentifier(),
                                                         attribute.getName(),
                                                         attribute.getAmount(),
                                                         ParsingUtil.attributeOperation(attribute.getOperation()),
                                                         ParsingUtil.attributeSlot(attribute.getSlot()));


    attributes.put(name, attr);
    return this;
  }

  @Override
  public BukkitItemStack attribute(Map<String, SerialAttribute> attributes) {

    for(Map.Entry<String, SerialAttribute> entry : attributes.entrySet()) {

      final SerialAttribute attribute = entry.getValue();
      final AttributeModifier attr = new AttributeModifier(attribute.getIdentifier(),
                                                           attribute.getName(),
                                                           attribute.getAmount(),
                                                           ParsingUtil.attributeOperation(attribute.getOperation()),
                                                           ParsingUtil.attributeSlot(attribute.getSlot()));


      this.attributes.put(entry.getKey(), attr);
    }
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
    this.material = Material.getMaterial(material);
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
  public BukkitItemStack display(String display) {
    this.display = display;
    return this;
  }

  @Override
  public BukkitItemStack damage(short damage) {
    this.damage = damage;
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
  public BukkitItemStack applyData(SerialItemData<ItemStack> data) {
    this.data = data;
    return this;
  }

  @Override
  public List<String> flags() {
    return flags;
  }

  @Override
  public List<String> lore() {
    return lore;
  }

  @Override
  public Map<String, SerialAttribute> attributes() {
    final Map<String, SerialAttribute> serialAttributes = new HashMap<>();

    for(Map.Entry<String, AttributeModifier> entry : attributes.entrySet()) {

      final AttributeModifier attribute = entry.getValue();

      final SerialAttribute attr = new SerialAttribute(attribute.getUniqueId(),
                                                       attribute.getName(),
                                                       attribute.getAmount(),
                                                       ParsingUtil.attributeOperation(attribute.getOperation()));
      if(attribute.getSlot() != null) {
        attr.setSlot(ParsingUtil.attributeSlot(attribute.getSlot()));
      }
      serialAttributes.put(entry.getKey(), attr);
    }
    return serialAttributes;
  }

  @Override
  public Map<String, Integer> enchantments() {
    return enchantments;
  }

  @Override
  public String material() {
    return material.getKey().toString();
  }

  @Override
  public int amount() {
    return amount;
  }

  @Override
  public int slot() {
    return slot;
  }

  @Override
  public String display() {
    return display;
  }

  @Override
  public short damage() {
    return damage;
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
    //return stack.isSimilar(compare.locale());
    return similarStack((BukkitItemStack)compare);
  }

  public static BukkitItemStack locale(ItemStack stack) {
    return new BukkitItemStack().of(stack);
  }

  public boolean similarStack(BukkitItemStack stack) {

    if(!material.equals(stack.material)) return false;
    if(!display.equals(stack.display)) return false;
    if(!Objects.equals(damage, stack.damage)) return false;
    if(!Objects.equals(customModelData, stack.customModelData)) return false;
    if(unbreakable != stack.unbreakable) return false;

    if(!listsEquals(lore, stack.lore)) return false;
    if(!listsEquals(flags, stack.flags)) return false;
    if(!attributes.equals(stack.attributes)) return false;
    if(!enchantments.equals(stack.enchantments)) return false;
    if(data != null) {
      return data.equals(stack.data);
    }

    return stack.data == null;
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    if(stack == null || dirty) {
      stack = new ItemStack(material, amount, damage);

      ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
      if(meta != null) {
        if(display != null) {
          meta.setDisplayName(display);
        }
        meta.setLore(lore);
        enchantments.forEach((name, level)->{

          final NamespacedKey space = NamespacedKey.fromString(name);
          if(space != null) {

            final Enchantment enchant = Enchantment.getByKey(space);
            if(enchant != null) {
              meta.addEnchant(enchant, level, true);
            }
          }
        });

        if(flags.size() > 0) {
          for(String str : flags) {
            try {
              meta.addItemFlags(ItemFlag.valueOf(str));
            } catch(Exception ignore) {
              //do nothing, invalid value
            }
          }
        }

        if(customModelData != -1) {
          meta.setCustomModelData(customModelData);
        }

        if(VersionUtil.isOneThirteen(Bukkit.getServer().getBukkitVersion().split("-")[0])) {
          attributes.forEach((name, attribute)->{
            try {
              meta.addAttributeModifier(Attribute.valueOf(name), attribute);
            } catch(Exception ignore) {
              //catched invalid Attribute names.
            }
          });
        }
      }

      stack.setItemMeta(meta);
      if(data != null) {
        stack = data.apply(stack);
      }
    }
    return stack;
  }
}