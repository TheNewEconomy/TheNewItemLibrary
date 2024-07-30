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
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.bukkitbase.component.BukkitBaseFoodComponent;
import net.tnemc.item.bukkitbase.component.BukkitJukeBoxComponent;
import net.tnemc.item.bukkitbase.data.BukkitSkullData;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.paper.component.PaperToolComponent;
import net.tnemc.item.providers.SkullProfile;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * PaperItemStack
 *
 * @author creatorfromhell
 * @since 0.1.7.5
 */
public class PaperItemStack implements AbstractItemStack<ItemStack> {

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
    this.material = Material.matchMaterial(material);
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

    profile = stack.profile;
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

    this.stack = stack.stack;

    return this;
  }

  @Override
  public PaperItemStack of(ItemStack locale) {
    this.stack = locale;

    material = stack.getType();
    amount = stack.getAmount();

    if(stack.hasItemMeta() && stack.getItemMeta() != null) {
      
      final ItemMeta meta = stack.getItemMeta();
      
      display = stack.displayName();

      if(meta.lore() != null) {
        lore.clear();
        lore.addAll(meta.lore());
      }

      // Check 1.13 version for compatibility with customModelData
      if(VersionUtil.isOneFourteen(ParsingUtil.version()) && meta.hasCustomModelData()) {
        customModelData = meta.getCustomModelData();
      }

      for(ItemFlag flag : meta.getItemFlags()) {
        flags.add(flag.name());
      }

      if(VersionUtil.isOneThirteen(ParsingUtil.version()) && meta.getAttributeModifiers() != null) {
        meta.getAttributeModifiers().forEach((attr, modifier)->attributes.put(attr.getKey().getKey(), modifier));
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
          components.put("tool", PaperToolComponent.create(stack));
        }

        if(meta.hasJukeboxPlayable()) {
          components.put("jukebox", BukkitJukeBoxComponent.create(stack));
        }
      }

      if(meta.hasEnchants()) {

        meta.getEnchants().forEach(((enchantment, level) ->enchantments.put(enchantment.getKey().getKey(), level)));
      }

      if(stack.hasItemMeta() && meta instanceof SkullMeta) {
        final BukkitSkullData skullData = new BukkitSkullData();
        skullData.of(locale);

        this.profile = skullData.getProfile();

      }

    }

    //Parse the meta data.
    PaperMetaBuild.parseMeta(locale)
            .ifPresent(itemStackSerialItemData->this.data = itemStackSerialItemData);

    return this;
  }

  @Override
  public PaperItemStack of(JSONObject json) {

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
  public PaperItemStack flags(List<String> flags) {
    this.flags.clear();
    this.flags.addAll(flags);
    return this;
  }

  @Override
  public PaperItemStack lore(List<Component> lore) {
    this.lore.clear();
    this.lore.addAll(lore);
    return this;
  }

  @SuppressWarnings("removal")
  @Override
  public PaperItemStack attribute(String name, SerialAttribute attribute) {
    final AttributeModifier attr = new AttributeModifier(attribute.getIdentifier(),
            attribute.getName(),
            attribute.getAmount(),
            ParsingUtil.attributeOperation(attribute.getOperation()),
            ParsingUtil.attributeSlot(attribute.getSlot()));


    attributes.put(name, attr);
    return this;
  }

  @SuppressWarnings("removal")
  @Override
  public PaperItemStack attribute(Map<String, SerialAttribute> attributes) {

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
  public PaperItemStack enchant(String enchantment, int level) {
    enchantments.put(enchantment, level);
    return this;
  }

  @Override
  public PaperItemStack enchant(Map<String, Integer> enchantments) {
    this.enchantments.clear();
    this.enchantments.putAll(enchantments);
    return this;
  }

  @Override
  public PaperItemStack enchant(List<String> enchantments) {
    this.enchantments.clear();
    for(String str : enchantments) {
      this.enchantments.put(str, 1);
    }
    return this;
  }

  @Override
  public PaperItemStack material(String material) {
    this.material = Registry.MATERIAL.get(NamespacedKey.fromString(material));
    return this;
  }

  @Override
  public PaperItemStack amount(int amount) {
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
  public PaperItemStack slot(int slot) {
    this.slot = slot;
    return this;
  }

  @Override
  public PaperItemStack display(Component display) {
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
    this.profile = profile;
    return this;
  }

  @Override
  public PaperItemStack modelData(int modelData) {
    this.customModelData = modelData;
    return this;
  }

  @Override
  public PaperItemStack unbreakable(boolean unbreakable) {
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

  @Override
  public PaperItemStack applyData(SerialItemData<ItemStack> data) {
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

      @SuppressWarnings("removal")
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

  public Material getType() {
    return material;
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
    //return stack.isSimilar(compare.locale());
    return similarStack((PaperItemStack)compare);
  }

  public static PaperItemStack locale(ItemStack stack) {
    return new PaperItemStack().of(stack);
  }

  public boolean similarStack(PaperItemStack stack) {

    if(debug || stack.debug) System.out.println("Similar Stack Check");

    if(material != stack.material) return false;
    if(debug || stack.debug) System.out.println("Material Check Passed");
    if(!displayPlain().equals(stack.displayPlain())) return false;
    if(debug || stack.debug) System.out.println("Display Check Passed");
    if(!Objects.equals(customModelData, stack.customModelData)) return false;
    if(debug || stack.debug) System.out.println("CustomData Check Passed");
    if(unbreakable != stack.unbreakable) return false;
    if(debug || stack.debug) System.out.println("Unbreakable Check Passed");

    //1.21 comps
    if(VersionUtil.isOneTwentyOne(ParsingUtil.version())) {
      if(!Objects.equals(maxStack, stack.maxStack)) return false;
      if(!Objects.equals(rarity, stack.rarity)) return false;
      if(enchantGlint != stack.enchantGlint) return false;
      if(fireResistant != stack.fireResistant) return false;
      if(hideTooltip != stack.hideTooltip) return false;
    }

    if(!textComponentsEqual(lore, stack.lore)) return false;
    if(debug || stack.debug) System.out.println("Lore Check Passed");
    if(!listsEquals(flags, stack.flags, debug)) return false;
    if(debug || stack.debug) System.out.println("Flags Check Passed");
    if(!attributes.equals(stack.attributes)) return false;
    if(debug || stack.debug) System.out.println("Attributes Check Passed");
    if(!enchantments.equals(stack.enchantments)) return false;
    if(debug || stack.debug) System.out.println("Enchants Check Passed");

    if(profile != null) {
      if(debug || stack.debug) System.out.println("Profile Check Entered");
      return stack.profile != null && profile.equals(stack.profile);
    }

    if(data != null) {
      if(debug || stack.debug) System.out.println("Data Check Entered");
      return data.equals(stack.data);
    }

    if(stack.profile != null) {
      if(debug || stack.debug) System.out.println("Profile Check Failed");
      return false;
    }
    if(debug || stack.debug) System.out.println("Profile Check Passed. Final Check.");

    return stack.data == null;
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    if(stack == null || dirty) {
      stack = new ItemStack(material, amount);

      ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
      if(meta != null) {

        if(display != null && !Component.EQUALS.test(display, Component.empty())) {
          meta.displayName(display);
        }

        meta.lore(lore);
        enchantments.forEach((name, level)->{

          final NamespacedKey space = NamespacedKey.fromString(name);
          if(space != null) {

            final Enchantment enchant = Registry.ENCHANTMENT.get(space);
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
          attributes.forEach((key, attribute)->{
            try {
              final NamespacedKey space = NamespacedKey.fromString(key);
              if(space != null) {

                final Attribute attr = Registry.ATTRIBUTE.get(space);
                if(attr != null) {

                  meta.addAttributeModifier(attr, attribute);
                }
              }
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