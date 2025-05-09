package net.tnemc.item.bukkit;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.bukkit.platform.impl.BukkitAttributeModifiersComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitBundleComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitContainerComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitCustomNameComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitDamageComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitDyedColorComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitEnchantableComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitEnchantmentsComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitItemModelComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitItemNameComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitLoreComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitMaxDamageComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitMaxStackSizeComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitModelDataComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitModelDataOldComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitProfileComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitRarityComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitRepairCostComponent;
import net.tnemc.item.bukkit.platform.impl.BukkitTooltipStyleComponent;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.helper.AttributeModifier;
import net.tnemc.item.component.helper.BlockPredicate;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.component.helper.ExplosionData;
import net.tnemc.item.component.helper.ItemDamage;
import net.tnemc.item.component.helper.PatternData;
import net.tnemc.item.component.helper.ToolRule;
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.component.impl.AttributeModifiersComponent;
import net.tnemc.item.component.impl.BannerPatternsComponent;
import net.tnemc.item.component.impl.BaseColorComponent;
import net.tnemc.item.component.impl.BlocksAttacksComponent;
import net.tnemc.item.component.impl.BreakSoundComponent;
import net.tnemc.item.component.impl.BucketEntityDataComponent;
import net.tnemc.item.component.impl.BundleComponent;
import net.tnemc.item.component.impl.CanBreakComponent;
import net.tnemc.item.component.impl.CanPlaceOnComponent;
import net.tnemc.item.component.impl.ConsumableComponent;
import net.tnemc.item.component.impl.ContainerComponent;
import net.tnemc.item.component.impl.CustomNameComponent;
import net.tnemc.item.component.impl.DamageComponent;
import net.tnemc.item.component.impl.DamageResistantComponent;
import net.tnemc.item.component.impl.DeathProtectionComponent;
import net.tnemc.item.component.impl.DyedColorComponent;
import net.tnemc.item.component.impl.EnchantableComponent;
import net.tnemc.item.component.impl.EnchantmentGlintOverrideComponent;
import net.tnemc.item.component.impl.EnchantmentsComponent;
import net.tnemc.item.component.impl.EntityVariantComponent;
import net.tnemc.item.component.impl.EquipComponent;
import net.tnemc.item.component.impl.FireworkExplosionComponent;
import net.tnemc.item.component.impl.FireworksComponent;
import net.tnemc.item.component.impl.FoodComponent;
import net.tnemc.item.component.impl.GliderComponent;
import net.tnemc.item.component.impl.HideAdditionalTooltipComponent;
import net.tnemc.item.component.impl.HideTooltipComponent;
import net.tnemc.item.component.impl.InstrumentComponent;
import net.tnemc.item.component.impl.IntangibleProjectileComponent;
import net.tnemc.item.component.impl.ItemModelComponent;
import net.tnemc.item.component.impl.ItemNameComponent;
import net.tnemc.item.component.impl.JukeBoxComponent;
import net.tnemc.item.component.impl.LodestoneTrackerComponent;
import net.tnemc.item.component.impl.LoreComponent;
import net.tnemc.item.component.impl.MapColorComponent;
import net.tnemc.item.component.impl.MapIDComponent;
import net.tnemc.item.component.impl.MaxDamageComponent;
import net.tnemc.item.component.impl.MaxStackSizeComponent;
import net.tnemc.item.component.impl.ModelDataComponent;
import net.tnemc.item.component.impl.ModelDataOldComponent;
import net.tnemc.item.component.impl.NoteBlockSoundComponent;
import net.tnemc.item.component.impl.OminousBottleAmplifierComponent;
import net.tnemc.item.component.impl.PotDecorationsComponent;
import net.tnemc.item.component.impl.PotionContentsComponent;
import net.tnemc.item.component.impl.PotionDurationScaleComponent;
import net.tnemc.item.component.impl.ProvidesBannerPatternsComponent;
import net.tnemc.item.component.impl.ProvidesTrimMaterialComponent;
import net.tnemc.item.component.impl.RarityComponent;
import net.tnemc.item.component.impl.RecipesComponent;
import net.tnemc.item.component.impl.RepairCostComponent;
import net.tnemc.item.component.impl.RepairableComponent;
import net.tnemc.item.component.impl.StoredEnchantmentsComponent;
import net.tnemc.item.component.impl.SuspiciousStewEffectsComponent;
import net.tnemc.item.component.impl.ToolComponent;
import net.tnemc.item.component.impl.TooltipDisplayComponent;
import net.tnemc.item.component.impl.TooltipStyleComponent;
import net.tnemc.item.component.impl.TrimComponent;
import net.tnemc.item.component.impl.UnbreakableComponent;
import net.tnemc.item.component.impl.UseCooldownComponent;
import net.tnemc.item.component.impl.WeaponComponent;
import net.tnemc.item.component.impl.WritableBookContentComponent;
import net.tnemc.item.component.impl.WrittenBookContentComponent;
import net.tnemc.item.persistent.PersistentDataHolder;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.SkullProfile;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BukkitItemStack
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitItemStack implements AbstractItemStack<ItemStack> {

  private final Map<String, SerialComponent<AbstractItemStack<ItemStack>, ItemStack>> components = new HashMap<>();

  private final List<String> flags = new ArrayList<>();

  private final PersistentDataHolder holder = new PersistentDataHolder();

  private int slot = 0;
  private String material;
  private int amount = 1;
  private boolean debug = false;

  //item providers
  private String itemProvider = BukkitItemPlatform.instance().defaultProviderIdentifier();
  private String providerItemID = material;

  //our locale stack
  private boolean dirty = false;
  private ItemStack localeStack;

  /**
   * Creates a new item stack with the specified material and amount.
   *
   * @param material The material of the item.
   * @param amount   The number of items in the stack.
   *
   * @return A new item stack instance.
   */
  @Override
  public BukkitItemStack of(final String material, final int amount) {

    this.material = material;
    this.amount = amount;

    this.dirty = true;

    Material materialInstance = null;

    try {
      final NamespacedKey key = NamespacedKey.fromString(material);
      if(key != null) {

        materialInstance = Registry.MATERIAL.get(key);
      }
    } catch(final NoSuchMethodError ignore) {
      materialInstance = Material.matchMaterial(material);
    }

    if(materialInstance == null) {

      return this;
    }

    this.localeStack = new ItemStack(materialInstance, amount);

    //TODO: Replace with custom solution? or would this be the best solution for the defaults?
    // custom is kinda overcomplicated but what is the performance hand off of new stack -> serialized,
    // alternatively we have to do this anyways for the locale cache?
    return of(this.localeStack);
  }

  /**
   * Creates a new item stack from a locale-specific object.
   *
   * @param locale The locale-specific representation.
   *
   * @return A new item stack instance.
   */
  @Override
  public BukkitItemStack of(final ItemStack locale) {

    this.localeStack = locale;

    try {

      final NamespacedKey key = locale.getType().getKeyOrNull();
      if(key != null) {

        material = key.toString();
      }
    } catch(final NoSuchMethodError ignore) {
      material = locale.getType().getKey().toString();
    }

    this.amount = locale.getAmount();
    final ItemMeta meta = locale.getItemMeta();
    if(meta != null) {
      for(final ItemFlag flag : meta.getItemFlags()) {

        this.flags.add(flag.name());
      }
    }

    return BukkitItemPlatform.instance().serializer(this.localeStack, this);
  }

  /**
   * Creates a new item stack from a JSON representation.
   *
   * @param json The JSON object containing item stack data.
   *
   * @return A new item stack instance.
   *
   * @throws ParseException If the JSON structure is invalid.
   */
  @Override
  public BukkitItemStack of(final JSONObject json) throws ParseException {

    return this;
  }

  /**
   * Sets the item flags.
   *
   * @param flags A list of flags to apply to the item.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack flags(final List<String> flags) {

    this.flags.addAll(flags);
    this.dirty = true;
    return this;
  }

  /**
   * Sets the lore (descriptive text) of the item stack.
   *
   * @param lore A list of components representing the lore.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack loreComponent(final List<Component> lore) {

    applyComponent(new BukkitLoreComponent(lore));

    return this;
  }

  /**
   * Adds an enchantment to the item stack.
   *
   * @param enchantment The enchantment name.
   * @param level       The level of the enchantment.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack enchant(final String enchantment, final int level) {

    applyComponent(new BukkitEnchantmentsComponent(new HashMap<>(){{
      put(enchantment, level);
    }}));
    return this;
  }

  /**
   * Adds multiple enchantments to the item stack.
   *
   * @param enchantments A map of enchantment names and levels.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack enchant(final Map<String, Integer> enchantments) {

    applyComponent(new BukkitEnchantmentsComponent(enchantments));
    return this;
  }

  /**
   * Adds enchantments to the item stack by name.
   *
   * @param enchantments A list of enchantment names.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack enchant(final List<String> enchantments) {

    final Map<String, Integer> enchants = new HashMap<>();
    for(final String enchantment : enchantments) {
      enchants.put(enchantment, 1);
    }
    applyComponent(new BukkitEnchantmentsComponent(enchants));
    return this;
  }

  /**
   * Returns the material of the item stack.
   *
   * @return The material of the item stack.
   */
  @Override
  public String material() {

    return material;
  }

  /**
   * Sets the material of the item stack.
   *
   * @param material The material name.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack material(final String material) {

    this.material = material;
    this.dirty = true;
    return this;
  }

  /**
   * The quantity of the item stack.
   *
   * @return the quantity of the item stack.
   */
  @Override
  public int amount() {

    return amount;
  }

  /**
   * Sets the quantity of the item stack.
   *
   * @param amount The number of items in the stack.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack amount(final int amount) {

    this.amount = amount;
    this.dirty = true;
    return this;
  }

  /**
   * Represents the inventory slot of the item stack.
   *
   * @return the inventory slot of the item stack.
   */
  @Override
  public int slot() {

    return slot;
  }

  /**
   * Sets the inventory slot of the item stack.
   *
   * @param slot The slot index.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack slot(final int slot) {

    this.slot = slot;
    this.dirty = true;
    return this;
  }

  /**
   * Enables or disables debug mode for the item stack.
   *
   * @param debug True to enable, false to disable.
   *
   * @return The updated item stack instance.
   */
  @Override
  public BukkitItemStack debug(final boolean debug) {

    this.debug = debug;
    return this;
  }

  public ItemStack cacheLocale() {

    return this.localeStack;
  }

  public void updateCache(final ItemStack localeStack) {

    this.localeStack = localeStack;
  }

  /**
   * Replaces the persistent data holder for the item stack.
   *
   * @param newHolder  The new persistent data holder.
   * @param replaceOld True to replace existing data, false to merge.
   *
   * @return The updated item stack instance.
   *
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack applyPersistentHolder(final PersistentDataHolder newHolder, final boolean replaceOld) {

    if(replaceOld) {
      this.holder.getData().clear();
    }

    this.holder.getData().putAll(newHolder.getData());
    this.dirty = true;
    return this;
  }

  /**
   * Retrieves the item flags.
   *
   * @return A list of flags applied to the item.
   */
  @Override
  public List<String> flags() {

    return this.flags;
  }

  /**
   * Retrieves the components applied to the item stack.
   *
   * @return A map of component types and their serialized representations.
   *
   * @since 0.2.0.0
   */
  @Override
  public Map<String, SerialComponent<AbstractItemStack<ItemStack>, ItemStack>> components() {

    return components;
  }

  /**
   * Retrieves the persistent data holder for the item stack.
   *
   * @return The persistent data holder.
   *
   * @since 0.2.0.0
   */
  @Override
  public PersistentDataHolder persistentHolder() {

    return holder;
  }

  /**
   * Resets the dirty flag, indicating that the object's state has been synchronized with the
   * database.
   */
  @Override
  public void resetDirty() {
    this.dirty = false;
  }

  /**
   * Marks the item stack as dirty, indicating changes have been made.
   */
  @Override
  public void markDirty() {

    this.dirty = true;
  }

  /**
   * Checks whether the object is dirty or has unsaved changes.
   *
   * @return true if the object is dirty, false otherwise
   */
  @Override
  public boolean isDirty() {

    return this.dirty;
  }

  /**
   * This method returns a String representing the item provider.
   *
   * @return the item provider as a String
   *
   * @see ItemProvider
   */
  @Override
  public String itemProvider() {

    return this.itemProvider;
  }

  /**
   * Sets the item provider to be used for retrieving items.
   *
   * @param itemProvider the string representing the item provider to be set
   */
  @Override
  public BukkitItemStack setItemProvider(final String itemProvider) {
    this.itemProvider = itemProvider;
    this.dirty = true;
    return this;
  }

  /**
   * Retrieves the provider item ID associated with the current object.
   *
   * @return The provider item ID of the object.
   */
  @Override
  public String providerItemID() {

    return this.providerItemID;
  }

  /**
   * Sets the provider's item ID for the current item.
   *
   * @param providerItemID the unique ID assigned by the provider for the item
   */
  @Override
  public BukkitItemStack setProviderItemID(final String providerItemID) {

    this.providerItemID = providerItemID;
    this.dirty = true;
    return this;
  }

  /**
   * This method is used to return an ItemProvider object.
   *
   * @return ItemProvider object representing the item provider.
   */
  @Override
  public ItemProvider<ItemStack> provider() {

    return BukkitItemPlatform.instance().provider(itemProvider);
  }

  /**
   * Converts the object to a JSONObject representation.
   *
   * @return A JSONObject representing the object data.
   */
  @Override
  public JSONObject toJSON() {

    return null;
  }

  @Override
  public BukkitItemStack clone() {
    final BukkitItemStack copy = new BukkitItemStack();

    //Basics
    copy.material = this.material;
    copy.amount = this.amount;
    copy.slot = this.slot;
    copy.debug = this.debug;

    //Flags
    copy.flags.addAll(this.flags);

    //Components
    for(final Map.Entry<String, SerialComponent<AbstractItemStack<ItemStack>, ItemStack>> entry : this.components.entrySet()) {

      //TODO: clone components
      //final SerialComponent<AbstractItemStack<ItemStack>, ItemStack> clonedComponent = entry.getValue().cloneComponent();
      //copy.components.put(entry.getKey(), clonedComponent);
    }

    //PersistentData
    final PersistentDataHolder clonedHolder = new PersistentDataHolder();
    clonedHolder.getData().putAll(this.holder.getData());
    copy.applyPersistentHolder(clonedHolder, true);

    //Item Provider
    copy.itemProvider = this.itemProvider;
    copy.providerItemID = this.providerItemID;

    //Mark the clone as dirty
    copy.dirty = true;

    return copy;
  }

  /**
   * Updates the attribute modifiers of the item stack.
   *
   * @param modifiers     a list of attribute modifiers
   * @param showInTooltip whether to display the modifiers in the tooltip
   *
   * @return the updated BukkitItemStack instance
   *
   * @see AttributeModifiersComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack attributeModifiers(final List<AttributeModifier> modifiers, final boolean showInTooltip) {

    return this;
  }

  /**
   * Updates the banner patterns of the item stack.
   *
   * @param patterns a list of pattern data
   *
   * @return the updated BukkitItemStack instance
   *
   * @see BannerPatternsComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack bannerPatterns(final List<PatternData> patterns) {

    return this;
  }

  /**
   * Updates the base color of the item stack.
   *
   * @param color the new base color
   *
   * @return the updated BukkitItemStack instance
   *
   * @see BaseColorComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack baseColor(final String color) {

    return this;
  }

  /**
   * Blocks attacks with the specified damage type using this item.
   *
   * @param damage the type of damage to block attacks from
   *
   * @return an AbstractItemStack object representing the item after blocking attacks
   *
   * @see BlocksAttacksComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> blocksAttacks(final ItemDamage damage) {

    return this;
  }

  /**
   * Set the break sound for this item stack.
   *
   * @param sound the sound to be played when the item is broken
   *
   * @return the updated item stack object
   *
   * @see BreakSoundComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> breakSound(final String sound) {

    return this;
  }

  /**
   * Updates the bucket entity data of the item stack.
   *
   * @param noAI             whether the entity has AI disabled
   * @param silent           whether the entity is silent
   * @param noGravity        whether the entity is affected by gravity
   * @param glowing          whether the entity is glowing
   * @param invulnerable     whether the entity is invulnerable
   * @param health           the health of the entity
   * @param age              the age of the entity
   * @param variant          the variant of the entity
   * @param huntingCooldown  the hunting cooldown of the entity
   * @param bucketVariantTag the variant tag of the bucket
   * @param type             the type of the entity
   *
   * @return the updated BukkitItemStack instance
   *
   * @see BucketEntityDataComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack bucketEntityData(final boolean noAI, final boolean silent, final boolean noGravity, final boolean glowing, final boolean invulnerable, final float health, final int age, final int variant, final long huntingCooldown, final int bucketVariantTag, final String type) {

    return this;
  }

  /**
   * Bundles a collection of {@link AbstractItemStack} instances into a single collection.
   *
   * @param items A {@link Map} containing integer keys and {@link AbstractItemStack} values to be
   *              bundled.
   *
   * @return An {@link AbstractItemStack} instance that represents the bundled items.
   *
   * @see BundleComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack bundle(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    applyComponent(new BukkitBundleComponent(items));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the blocks that the item stack can break.
   *
   * @param predicates a list of block predicates
   *
   * @return the updated BukkitItemStack instance
   *
   * @see CanBreakComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack canBreak(final List<BlockPredicate> predicates) {

    return this;
  }

  /**
   * Updates the blocks that the item stack can be placed on.
   *
   * @param predicates a list of block predicates
   *
   * @return the updated BukkitItemStack instance
   *
   * @see CanPlaceOnComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack canPlaceOn(final List<BlockPredicate> predicates) {

    return this;
  }

  /**
   * Updates the consumable properties of the item stack.
   *
   * @param consumeSeconds      the time it takes to consume the item
   * @param animation           the animation to display when consuming
   * @param sound               the sound to play when consuming
   * @param hasConsumeParticles whether to show consume particles
   * @param effects             a list of effects applied on consumption
   *
   * @return the updated BukkitItemStack instance
   *
   * @see ConsumableComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack consumable(final float consumeSeconds, final String animation, final String sound, final boolean hasConsumeParticles, final List<ComponentEffect> effects) {

    return this;
  }

  /**
   * Constructs a container with the given map of items.
   *
   * @param items a map of items where the key is the position of the item in the container and the
   *              value is the item
   *
   * @return an AbstractItemStack container with the specified items
   *
   * @see ContainerComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack container(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    applyComponent(new BukkitContainerComponent(items));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the custom name of the item stack.
   *
   * @param customName the custom name to set
   *
   * @return the updated BukkitItemStack instance
   *
   * @see CustomNameComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack customName(final Component customName) {

    applyComponent(new BukkitCustomNameComponent(customName));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the damage of the item stack.
   *
   * @param damage the damage value to set
   *
   * @return the updated BukkitItemStack instance
   *
   * @see DamageComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack damage(final int damage) {

    applyComponent(new BukkitDamageComponent(damage));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the damage-resistant types of the item stack.
   *
   * @param types a list of damage-resistant types
   *
   * @return the updated BukkitItemStack instance
   *
   * @see DamageResistantComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack damageResistant(final List<String> types) {

    return this;
  }

  /**
   * Updates the death protection effects of the item stack.
   *
   * @param deathEffects a list of death protection effects
   *
   * @return the updated BukkitItemStack instance
   *
   * @see DeathProtectionComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack deathProtection(final List<ComponentEffect> deathEffects) {

    return this;
  }

  /**
   * Updates the dyed color of the item stack.
   *
   * @param rgb the RGB color value to set
   *
   * @return the updated BukkitItemStack instance
   *
   * @see DyedColorComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack dyedColor(final int rgb) {

    applyComponent(new BukkitDyedColorComponent(rgb));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the enchantability of the item stack.
   *
   * @param value the enchantability value to set
   *
   * @return the updated BukkitItemStack instance
   *
   * @see EnchantableComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack enchantable(final int value) {

    applyComponent(new BukkitEnchantableComponent(value));
    return this;
  }

  /**
   * Updates the enchantment glint override of the item stack.
   *
   * @param glintOverride whether the enchantment glint should be overridden
   *
   * @return the updated BukkitItemStack instance
   *
   * @see EnchantmentGlintOverrideComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack enchantmentGlintOverride(final boolean glintOverride) {
    return this;
  }

  /**
   * Updates the enchantments of the item stack.
   *
   * @param levels a map of enchantments and their levels
   *
   * @return the updated BukkitItemStack instance
   *
   * @see EnchantmentsComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack enchantments(final Map<String, Integer> levels) {

    applyComponent(new BukkitEnchantmentsComponent(levels));
    this.dirty = true;
    return this;
  }

  /**
   * Generates an AbstractItemStack based on the given entity and variant.
   *
   * @param entity  the entity name to create the stack for
   * @param variant the variant of the entity
   *
   * @return an AbstractItemStack representing the entity with the specified variant
   *
   * @see EntityVariantComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> entityVariant(final String entity, final String variant) {

    return this;
  }

  /**
   * Equips an item with specified parameters.
   *
   * @param cameraKey       the key identifying the camera
   * @param equipSound      the key identifying the equip sound
   * @param modelKey        the key identifying the model
   * @param slot            the slot in which the item should be equipped
   * @param damageOnHurt    flag indicating if damage should be taken on hurt
   * @param dispensable     flag indicating if the item is dispensable
   * @param swappable       flag indicating if the item is swappable
   * @param equipOnInteract flag indicating if the item should be equipped on interact
   * @param entities        a list of entities to be equipped
   *
   * @return an BukkitItemStack object representing the equipped item
   *
   * @see EquipComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack equip(final String cameraKey, final String equipSound, final String modelKey,
                               final EquipSlot slot, final boolean damageOnHurt, final boolean dispensable,
                               final boolean swappable, final boolean equipOnInteract, final List<String> entities) {

    return this;
  }

  /**
   * Updates the firework explosion properties of the item stack.
   *
   * @param explosion the explosion data to set
   *
   * @return the updated BukkitItemStack instance
   *
   * @see FireworkExplosionComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack fireworkExplosion(final ExplosionData explosion) {

    return this;
  }

  /**
   * Updates the fireworks properties of the item stack.
   *
   * @param flightDuration the flight duration of the fireworks
   * @param explosions     a list of explosion data
   *
   * @return the updated BukkitItemStack instance
   *
   * @see FireworksComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack fireworks(final byte flightDuration, final List<ExplosionData> explosions) {

    return this;
  }

  /**
   * Updates the food properties of the item stack.
   *
   * @param noHunger   whether the item causes no hunger
   * @param saturation the saturation value
   * @param nutrition  the nutrition value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see FoodComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack food(final boolean noHunger, final float saturation, final int nutrition) {

    return this;
  }

  /**
   * Updates the item stack to enable glider functionality.
   *
   * @return the updated BukkitItemStack instance
   *
   * @see GliderComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack gliderTag() {

    return this;
  }

  /**
   * Updates the item stack to hide additional tooltip.
   *
   * @return the updated BukkitItemStack instance
   *
   * @see HideAdditionalTooltipComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack hideAdditionalTooltipTag() {

    return this;
  }

  /**
   * Updates the item stack to hide its tooltip.
   *
   * @return the updated BukkitItemStack instance
   *
   * @see HideTooltipComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack hideTooltipTag() {

    return this;
  }

  /**
   * Updates the instrument properties of the item stack.
   *
   * @param soundEvent  the sound event identifier
   * @param useDuration the duration of the sound in ticks
   * @param range       the range of the sound
   *
   * @return the updated BukkitItemStack instance
   *
   * @see InstrumentComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack instrument(final String soundEvent, final int useDuration, final int range) {

    return this;
  }

  /**
   * Updates the item stack as an intangible projectile.
   *
   * @return the updated BukkitItemStack instance
   *
   * @see IntangibleProjectileComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack intangibleProjectileTag() {

    return this;
  }

  /**
   * Updates the model properties of the item stack.
   *
   * @param model the model identifier
   *
   * @return the updated BukkitItemStack instance
   *
   * @see ItemModelComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack itemModel(final String model) {

    applyComponent(new BukkitItemModelComponent(model));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the name of the item stack.
   *
   * @param itemName the name of the item
   *
   * @return the updated BukkitItemStack instance
   *
   * @see ItemNameComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack itemName(final Component itemName) {

    applyComponent(new BukkitItemNameComponent(itemName));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the jukebox properties of the item stack.
   *
   * @param song          the song identifier
   * @param showInTooltip whether to display the song in the tooltip
   *
   * @return the updated BukkitItemStack instance
   *
   * @see JukeBoxComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack jukebox(final String song, final boolean showInTooltip) {

    return this;
  }

  /**
   * Updates the lodestone tracker properties of the item stack.
   *
   * @param target    the target identifier
   * @param pos       the position array
   * @param dimension the dimension identifier
   * @param tracked   whether the lodestone is tracked
   *
   * @return the updated BukkitItemStack instance
   *
   * @see LodestoneTrackerComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack lodestoneTracker(final String target, final int[] pos, final String dimension, final boolean tracked) {

    return this;
  }

  /**
   * Updates the lore of the item stack.
   *
   * @param lore a list of lore strings
   *
   * @return the updated BukkitItemStack instance
   *
   * @see LoreComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack lore(final List<Component> lore) {

    applyComponent(new BukkitLoreComponent(lore));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the map color of the item stack.
   *
   * @param mapColor the map color value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see MapColorComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack mapColor(final int mapColor) {

    return this;
  }

  /**
   * Updates the map ID of the item stack.
   *
   * @param mapId the map ID value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see MapIDComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack mapId(final int mapId) {

    return this;
  }

  /**
   * Updates the maximum damage of the item stack.
   *
   * @param maxDamage the maximum damage value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see MaxDamageComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack maxDamage(final int maxDamage) {

    applyComponent(new BukkitMaxDamageComponent(maxDamage));
    return this;
  }

  /**
   * Updates the maximum stack size of the item stack.
   *
   * @param maxStackSize the maximum stack size value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see MaxStackSizeComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack maxStackSize(final int maxStackSize) {

    applyComponent(new BukkitMaxStackSizeComponent(maxStackSize));
    return this;
  }

  /**
   * Updates the model data of the item stack.
   *
   * @param colours a list of color strings
   * @param floats  a list of float values
   * @param flags   a list of boolean flags
   * @param strings a list of string identifiers
   *
   * @return the updated BukkitItemStack instance
   *
   * @see ModelDataComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack modelData(final List<String> colours, final List<Float> floats, final List<Boolean> flags, final List<String> strings) {

    applyComponent(new BukkitModelDataComponent(colours, floats, flags, strings));
    this.dirty = true;
    return this;
  }

  /**
   * Retrieves the model data for a custom item stack.
   *
   * @param customModelData the custom model data to retrieve
   *
   * @return an AbstractItemStack with the specified custom model data
   *
   * @see ModelDataOldComponent
   * @since 0.2.0.0
   * @deprecated Since MC 1.21.3 Use {@link ItemModelComponent} and {@link ModelDataComponent}.
   */
  @Override
  public AbstractItemStack<ItemStack> modelDataOld(final int customModelData) {

    applyComponent(new BukkitModelDataOldComponent(customModelData));
    this.dirty = true;
    return this;
  }

  /**
   * Updates the note block sound of the item stack.
   *
   * @param soundId the identifier of the sound
   *
   * @return the updated BukkitItemStack instance
   *
   * @see NoteBlockSoundComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack noteBlockSound(final String soundId) {

    return this;
  }

  /**
   * Updates the ominous bottle amplifier of the item stack.
   *
   * @param amplifier the amplifier value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see OminousBottleAmplifierComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack ominousBottleAmplifier(final int amplifier) {

    return this;
  }

  /**
   * Updates the pot decorations of the item stack.
   *
   * @param decorations a list of decorations
   *
   * @return the updated BukkitItemStack instance
   *
   * @see PotDecorationsComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack potDecorations(final List<String> decorations) {

    return this;
  }

  /**
   * Updates the potion contents of the item stack.
   *
   * @param potionId    the ID of the potion
   * @param customColor the custom color of the potion
   * @param customName  the custom name of the potion
   * @param effects     a list of effect instances
   *
   * @return the updated BukkitItemStack instance
   *
   * @see PotionContentsComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack potionContents(final String potionId, final int customColor, final String customName, final List<EffectInstance> effects) {

    return this;
  }

  /**
   * Sets the duration of a potion effect for the item stack. Since MC Snapshot 25w02a.
   *
   * @param potionDuration the duration of the potion effect in seconds
   *
   * @return the modified BukkitItemStack object with the updated potion duration
   *
   * @see PotionDurationScaleComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack potionDuration(final float potionDuration) {

    return this;
  }

  /**
   * Profiles the given SkullProfile to the BukkitItemStack object.
   *
   * @param profile the SkullProfile to be assigned
   *
   * @return an BukkitItemStack object with the provided profile
   */
  @Override
  public BukkitItemStack profile(final SkullProfile profile) {

    applyComponent(new BukkitProfileComponent(profile));
    this.dirty = true;
    return this;
  }

  /**
   * Retrieve an AbstractItemStack object that provides a specific pattern identified by the
   * patternTag.
   *
   * @param patternTag The unique identifier of the pattern to be provided.
   *
   * @return An AbstractItemStack object that provides the specified pattern.
   *
   * @see ProvidesBannerPatternsComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> providesPattern(final String patternTag) {

    return this;
  }

  /**
   * Applies the specified trim material
   *
   * @param material the trim material
   *
   * @return an AbstractItemStack object representing the trimmed material
   *
   * @see ProvidesTrimMaterialComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> providesTrim(final String material) {

    return this;
  }

  /**
   * Updates the rarity of the item stack.
   *
   * @param rarity the rarity value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see RarityComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack rarity(final String rarity) {

    return this;
  }

  /**
   * Updates the recipes of the item stack.
   *
   * @param recipes a list of recipe identifiers
   *
   * @return the updated BukkitItemStack instance
   *
   * @see RecipesComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack recipes(final List<String> recipes) {

    return this;
  }

  /**
   * Updates the repairable items of the item stack.
   *
   * @param repairItems a list of repair item identifiers
   *
   * @return the updated BukkitItemStack instance
   *
   * @see RepairableComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack repairable(final List<String> repairItems) {

    return this;
  }

  /**
   * Updates the repair cost of the item stack.
   *
   * @param repairCost the repair cost value
   *
   * @return the updated BukkitItemStack instance
   *
   * @see RepairCostComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack repairCost(final int repairCost) {

    return this;
  }

  /**
   * Updates the stored enchantments of the item stack.
   *
   * @param enchantments a map of enchantment names to their levels
   *
   * @return the updated BukkitItemStack instance
   *
   * @see StoredEnchantmentsComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack storedEnchantments(final Map<String, Integer> enchantments) {

    return this;
  }

  /**
   * Applies the given effects to the suspicious stew item.
   *
   * @param effects a list of EffectInstance objects representing the effects to be applied to the
   *                suspicious stew
   *
   * @return an AbstractItemStack representing the suspicious stew with the applied effects
   *
   * @see SuspiciousStewEffectsComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> suspiciousStewEffects(final List<EffectInstance> effects) {

    return this;
  }

  /**
   * Applies the given effects to a suspicious stew item.
   *
   * @param effects an array of EffectInstance objects representing the effects to apply to the
   *                suspicious stew
   *
   * @return an AbstractItemStack representing the suspicious stew item with the applied effects
   *
   * @see SuspiciousStewEffectsComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> suspiciousStewEffects(final EffectInstance... effects) {

    return this;
  }

  /**
   * Constructs a new tool item with the specified characteristics.
   *
   * @param defaultSpeed             the default speed of the tool
   * @param blockDamage              the damage inflicted by the tool to blocks
   * @param canDestroyBlocksCreative if the tool can destroy blocks in creative mode
   * @param rules                    a list of rules that define the behavior of the tool
   *
   * @return an BukkitItemStack instance representing the created tool
   *
   * @see ToolComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack tool(final float defaultSpeed, final int blockDamage, final boolean canDestroyBlocksCreative, final List<ToolRule> rules) {

    return this;
  }

  /**
   * Displays tooltip for the item stack with an option to hide certain components.
   *
   * @param hideTooltip      Flag to hide the tooltip
   * @param hiddenComponents Array of components to hide in the tooltip
   *
   * @return An AbstractItemStack object with the tooltip displayed
   *
   * @see TooltipDisplayComponent
   * @since 0.2.0.0
   */
  @Override
  public AbstractItemStack<ItemStack> tooltipDisplay(final boolean hideTooltip, final String... hiddenComponents) {

    return this;
  }

  /**
   * Updates the tooltip style of the item stack.
   *
   * @param style the style to apply to the tooltip
   *
   * @return the updated BukkitItemStack instance
   *
   * @see TooltipStyleComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack tooltipStyle(final String style) {

    applyComponent(new BukkitTooltipStyleComponent(style));
    return this;
  }

  /**
   * Updates the trim properties of the item stack.
   *
   * @param pattern       the trim pattern
   * @param material      the trim material
   * @param showInTooltip whether to display the trim in the tooltip
   *
   * @return the updated BukkitItemStack instance
   *
   * @see TrimComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack trim(final String pattern, final String material, final boolean showInTooltip) {

    return this;
  }

  /**
   * Updates the unbreakable property of the item stack.
   *
   * @param showInTooltip whether to display the unbreakable property in the tooltip
   *
   * @return the updated BukkitItemStack instance
   *
   * @see UnbreakableComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack unbreakable(final boolean showInTooltip) {

    return this;
  }

  /**
   * Updates the use cooldown properties of the item stack.
   *
   * @param cooldownGroup the cooldown group identifier
   * @param seconds       the cooldown duration in seconds
   *
   * @return the updated BukkitItemStack instance
   *
   * @see UseCooldownComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack useCooldown(final String cooldownGroup, final float seconds) {

    return this;
  }

  /**
   * Represents a weapon item that can be used for combat. Since MC Snapshot 25w02a.
   *
   * @param damagePerAttack    The amount of damage this weapon inflicts per attack
   * @param canDisableBlocking Whether this weapon can disable blocking when used
   *
   * @see WeaponComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack weapon(final int damagePerAttack, final boolean canDisableBlocking) {

    return this;
  }

  /**
   * Updates the writable book content of the item stack.
   *
   * @param pages the pages to include in the writable book
   *
   * @return the updated BukkitItemStack instance
   *
   * @see WritableBookContentComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack writableBookContent(final List<String> pages) {

    return this;
  }

  /**
   * Updates the written book content of the item stack.
   *
   * @param title      the title of the book
   * @param author     the author of the book
   * @param generation the generation of the book
   * @param resolved   whether the book is resolved
   * @param pages      the pages to include in the book
   *
   * @return the updated BukkitItemStack instance
   *
   * @see WrittenBookContentComponent
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack writtenBookContent(final String title, final String author, final int generation, final boolean resolved, final List<String> pages) {

    return this;
  }
}