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
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.bukkit.component.BukkitToolComponent;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.bukkitbase.component.BukkitBaseFoodComponent;
import net.tnemc.item.bukkitbase.component.BukkitJukeBoxComponent;
import net.tnemc.item.bukkitbase.data.BukkitSkullData;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.providers.SkullProfile;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents an ItemStack object related to the Bukkit API.
 */
public class BukkitItemStack implements AbstractItemStack<ItemStack> {

  private final List<String> flags = new ArrayList<>();
  private final Map<String, AttributeModifier> attributes = new HashMap<>();
  private final Map<String, Integer> enchantments = new HashMap<>();
  private final List<Component> lore = new ArrayList<>();

  private final Map<String, SerialComponent<ItemStack>> components = new HashMap<>();

  private int slot = 0;
  private SkullProfile profile = null;
  private Material material;
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
    this.material = Material.matchMaterial(material);
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

    profile = stack.profile;
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

    material = stack.getType();
    amount = stack.getAmount();
    damage = stack.getDurability();

    if(stack.hasItemMeta() && stack.getItemMeta()!= null) {

      final ItemMeta meta = stack.getItemMeta();
      
      display = LegacyComponentSerializer.legacySection().deserialize(meta.getDisplayName());

      if(meta.getLore() != null) {
        lore.clear();

        for(String str : meta.getLore()) {
          lore.add(LegacyComponentSerializer.legacySection().deserialize(str));
        }
      }

      // Check 1.13 version for compatibility with customModelData
      if(VersionUtil.isOneFourteen(ParsingUtil.version()) && meta.hasCustomModelData()) {
        customModelData = meta.getCustomModelData();
      }

      //1.21 compat
      if(VersionUtil.isOneTwentyOne(ParsingUtil.version())) {

        this.maxStack = meta.getMaxStackSize();
        if(meta.hasRarity()) {
          this.rarity = meta.getRarity().name();
        }

        if(meta.hasEnchantmentGlintOverride()) {
          this.enchantGlint = meta.getEnchantmentGlintOverride();
        }

        this.fireResistant = meta.isFireResistant();
        this.hideTooltip = meta.isHideTooltip();

        if(meta.hasFood()) {
          components.put("food", BukkitBaseFoodComponent.create(stack));
        }

        if(meta.hasTool()) {
          components.put("tool", BukkitToolComponent.create(stack));
        }

        if(meta.hasJukeboxPlayable()) {
          components.put("jukebox", BukkitJukeBoxComponent.create(stack));
        }
      }

      for(ItemFlag flag : meta.getItemFlags()) {
        flags.add(flag.name());
      }

      if(VersionUtil.isOneThirteen(ParsingUtil.version()) && meta.getAttributeModifiers() != null) {
        meta.getAttributeModifiers().forEach((attr, modifier)->attributes.put(attr.name(), modifier));
      }

      if(meta.hasEnchants()) {

        meta.getEnchants().forEach(((enchantment, level) ->enchantments.put(enchantment.getKey().toString(), level)));
      }

      if(stack.hasItemMeta() && meta instanceof SkullMeta) {
        final BukkitSkullData skullData = new BukkitSkullData();
        skullData.of(locale);

        this.profile = skullData.getProfile();
      }


    }

    //Parse the meta data.
    BukkitMetaBuild.parseMeta(locale)
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
  public BukkitItemStack lore(List<Component> lore) {
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
    this.material = Material.matchMaterial(material);
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
  public Map<String, SerialComponent<ItemStack>> components() {
    return components;
  }

  @Override
  public String material() {
    return material.getKey().getKey();
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
    //return stack.isSimilar(compare.locale());
    return similarStack((BukkitItemStack)compare);
  }

  public static BukkitItemStack locale(ItemStack stack) {
    return new BukkitItemStack().of(stack);
  }

  public boolean similarStack(BukkitItemStack stack) {

    //TODO: Check system
    //Should allow version control too
    if(material != stack.material) return false;
    if(!displayPlain().equals(stack.displayPlain())) return false;
    if(!Objects.equals(damage, stack.damage)) return false;
    if(!Objects.equals(customModelData, stack.customModelData)) return false;
    if(unbreakable != stack.unbreakable) return false;

    //1.21 comps
    if(VersionUtil.isOneTwentyOne(ParsingUtil.version())) {
      if(!Objects.equals(maxStack, stack.maxStack)) return false;
      if(!Objects.equals(rarity, stack.rarity)) return false;
      if(enchantGlint != stack.enchantGlint) return false;
      if(fireResistant != stack.fireResistant) return false;
      if(hideTooltip != stack.hideTooltip) return false;
    }

    if(!textComponentsEqual(lore, stack.lore)) return false;
    if(!listsEquals(flags, stack.flags)) return false;
    if(!attributes.equals(stack.attributes)) return false;
    if(!enchantments.equals(stack.enchantments)) return false;
    if(data != null) {
      return data.equals(stack.data);
    }

    if(profile != null) {
      return stack.profile != null && profile.equals(stack.profile);
    }

    if(stack.profile != null) {
      return false;
    }

    return stack.data == null;
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    if(stack == null || dirty) {

      try {
        stack = new ItemStack(material, amount, damage);
      } catch(Exception ignore) {
        stack = new ItemStack(material, amount);
      }

      //TODO: Application system
      //Should allow version control too

      ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
      if(meta != null) {
        if(display != null) {
          meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(display));
        }

        final LinkedList<String> newLore = new LinkedList<>();
        for(Component comp : lore) {
          newLore.add(LegacyComponentSerializer.legacySection().serialize(comp));
        }
        meta.setLore(newLore);

        enchantments.forEach((name, level)->{

          final NamespacedKey space = NamespacedKey.fromString(name);
          if(space != null) {

            final Enchantment enchant = Enchantment.getByKey(space);
            if(enchant != null) {
              meta.addEnchant(enchant, level, true);
            }
          }
        });

        if(!flags.isEmpty()) {
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

        if(VersionUtil.isOneThirteen(ParsingUtil.version())) {
          attributes.forEach((name, attribute)->{
            try {
              meta.addAttributeModifier(Attribute.valueOf(name), attribute);
            } catch(Exception ignore) {
              //catched invalid Attribute names.
            }
          });
        }

        if(VersionUtil.isOneTwentyOne(ParsingUtil.version())) {

          meta.setMaxStackSize(maxStack);
          meta.setRarity(ItemRarity.valueOf(rarity));
          meta.setEnchantmentGlintOverride(enchantGlint);
          meta.setFireResistant(fireResistant);
          meta.setHideTooltip(hideTooltip);
        }
      }

      if(profile != null && meta instanceof SkullMeta) {

        if(profile.getUuid() != null) {

          ((SkullMeta)meta).setOwningPlayer(Bukkit.getOfflinePlayer(profile.getUuid()));

        } else if(profile.getUuid() == null && profile.getName() != null) {

          ((SkullMeta)meta).setOwner(profile.getName());
        }
      }

      stack.setItemMeta(meta);

      if(VersionUtil.isOneTwentyOne(ParsingUtil.version())) {

        for(SerialComponent<ItemStack> component : components.values()) {
          component.apply(stack);
        }
      }

      if(data != null) {
        stack = data.apply(stack);
      }
    }
    return stack;
  }
}