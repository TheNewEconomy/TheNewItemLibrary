package net.tnemc.item;

/*
 * The New Item Library Minecraft Server Plugin
 *
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
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
import net.tnemc.item.component.impl.ProfileComponent;
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
import net.tnemc.item.persistent.PersistentDataType;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.SkullProfile;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a generic abstraction for an item stack with various attributes and properties.
 * This interface allows for chaining multiple properties and supports serialization.
 *
 * @param <T> The implementation-specific type of this item stack.
 * @author creatorfromhell
 */
public interface AbstractItemStack<T> extends Cloneable {

  /**
   * Creates a new item stack with the specified material and amount.
   *
   * @param material The material of the item.
   * @param amount   The number of items in the stack.
   * @return A new item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> of(final String material, final int amount);

  /**
   * Creates a new item stack from a locale-specific object.
   *
   * @param locale The locale-specific representation.
   * @return A new item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> of(T locale);

  /**
   * Creates a new item stack from a JSON representation.
   *
   * @param json The JSON object containing item stack data.
   * @return A new item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @throws ParseException If the JSON structure is invalid.
   */
  AbstractItemStack<T> of(JSONObject json) throws ParseException;

  /**
   * Sets the item flags.
   *
   * @param flags A list of flags to apply to the item.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> flags(List<String> flags);

  /**
   * Sets the lore (descriptive text) of the item stack.
   *
   * @param lore A list of components representing the lore.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> loreComponent(List<Component> lore);

  /**
   * Adds an enchantment to the item stack.
   *
   * @param enchantment The enchantment name.
   * @param level       The level of the enchantment.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> enchant(String enchantment, int level);

  /**
   * Adds multiple enchantments to the item stack.
   *
   * @param enchantments A map of enchantment names and levels.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> enchant(Map<String, Integer> enchantments);

  /**
   * Adds enchantments to the item stack by name.
   *
   * @param enchantments A list of enchantment names.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> enchant(List<String> enchantments);

  /**
   * Returns the material of the item stack.
   *
   * @return The material of the item stack.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  String material();

  /**
   * Sets the material of the item stack.
   *
   * @param material The material name.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> material(String material);

  /**
   * The quantity of the item stack.
   *
   * @return the quantity of the item stack.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  int amount();

  /**
   * Sets the quantity of the item stack.
   *
   * @param amount The number of items in the stack.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> amount(final int amount);

  /**
   * Subtracts the specified amount from the current item stack.
   *
   * @param amount the amount to subtract from the item stack
   * @return a new item stack with the specified amount subtracted
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  default AbstractItemStack<T> subtract(final int amount) {

    return amount(amount() - amount);
  }

  /**
   * Adds the specified amount to the current amount of this item stack.
   *
   * @param amount the amount to be added to the item stack
   * @return a new AbstractItemStack with the updated amount
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  default AbstractItemStack<T> add(final int amount) {

    return amount(amount() + amount);
  }

  /**
   * Represents the inventory slot of the item stack.
   *
   * @return the inventory slot of the item stack.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  int slot();

  /**
   * Sets the inventory slot of the item stack.
   *
   * @param slot The slot index.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> slot(int slot);

  /**
   * Enables or disables debug mode for the item stack.
   *
   * @param debug True to enable, false to disable.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> debug(boolean debug);

  /**
   * Checks if a component with the specified identifier is present.
   *
   * @param identifier The identifier of the component to check.
   * @return true if a component with the specified identifier is present, false otherwise.
   */
  default boolean hasComponent(final String identifier) {

    return components().containsKey(identifier);
  }

  /**
   * Applies a serialized component to the item stack.
   *
   * @param component The component to apply.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  default <C extends SerialComponent<? extends AbstractItemStack<T>, T>> AbstractItemStack<T> applyComponent(final C component) {

    components().put(component.identifier(), component);
    markDirty();
    return this;
  }

  /**
   * Retrieves a SerialComponent associated with the specified identifier.
   *
   * @param identifier the identifier of the SerialComponent to retrieve
   * @return an Optional containing the SerialComponent if found, empty Optional otherwise
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  default <C extends SerialComponent<? extends AbstractItemStack<T>, T>> Optional<C> component(final String identifier) {
    return (Optional<C>)Optional.ofNullable(components().get(identifier));
  }

  /**
   * Applies persistent data to the item stack.
   *
   * @param data The persistent data to apply.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  default AbstractItemStack<T> applyPersistent(final PersistentDataType<?> data) {
    persistentHolder().getData().put(data.identifier(), data);
    markDirty();
    return this;
  }

  /**
   * Replaces the persistent data holder for the item stack.
   *
   * @param newHolder The new persistent data holder.
   * @param replaceOld True to replace existing data, false to merge.
   * @return The updated item stack instance.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  AbstractItemStack<T> applyPersistentHolder(final PersistentDataHolder newHolder, boolean replaceOld);

  /**
   * Retrieves the item flags.
   *
   * @return A list of flags applied to the item.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  List<String> flags();

  /**
   * Retrieves the components applied to the item stack.
   *
   * @return A map of component types and their serialized representations.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  <C extends SerialComponent<? extends AbstractItemStack<T>, T>> Map<String, C> components();

  /**
   * Retrieves the persistent data holder for the item stack.
   *
   * @return The persistent data holder.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  PersistentDataHolder persistentHolder();

  /**
   * Resets the dirty flag, indicating that the object's state has been synchronized with the database.
   */
  void resetDirty();

  /**
   * Marks the item stack as dirty, indicating changes have been made.
   */
  void markDirty();

  /**
   * Checks whether the object is dirty or has unsaved changes.
   *
   * @return true if the object is dirty, false otherwise
   */
  boolean isDirty();

  /**
   * This method returns a String representing the item provider.
   *
   * @see net.tnemc.item.providers.ItemProvider
   * @return the item provider as a String
   */
  String itemProvider();

  /**
   * Sets the item provider to be used for retrieving items.
   *
   * @param itemProvider the string representing the item provider to be set
   */
  AbstractItemStack<T> setItemProvider(final String itemProvider);

  /**
   * Retrieves the provider item ID associated with the current object.
   *
   * @return The provider item ID of the object.
   */
  String providerItemID();

  /**
   * Sets the provider's item ID for the current item.
   *
   * @param providerItemID the unique ID assigned by the provider for the item
   */
  AbstractItemStack<T> setProviderItemID(final String providerItemID);

  /**
   * This method is used to return an ItemProvider object.
   *
   * @return ItemProvider object representing the item provider.
   */
  ItemProvider<T> provider();

  /**
   * Converts the object to a JSONObject representation.
   *
   * @return A JSONObject representing the object data.
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  JSONObject toJSON();

  //Our component getters/setters
  /**
   * Retrieves the AttributeModifiersComponent of the item stack if present.
   *
   * @return an Optional containing the AttributeModifiersComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see AttributeModifiersComponent
   */
  default Optional<AttributeModifiersComponent<AbstractItemStack<T>, T>> attributeModifiers() {
    return Optional.ofNullable((AttributeModifiersComponent<AbstractItemStack<T>, T>) components().get("attribute_modifiers"));
  }

  /**
   * Updates the attribute modifiers of the item stack.
   *
   * @param modifiers     a list of attribute modifiers
   * @param showInTooltip whether to display the modifiers in the tooltip
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see AttributeModifiersComponent
   */
  AbstractItemStack<T> attributeModifiers(List<AttributeModifier> modifiers, boolean showInTooltip);

  /**
   * Retrieves the BannerPatternsComponent of the item stack if present.
   *
   * @return an Optional containing the BannerPatternsComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BannerPatternsComponent
   */
  default Optional<BannerPatternsComponent<AbstractItemStack<T>, T>> bannerPatterns() {
    return Optional.ofNullable((BannerPatternsComponent<AbstractItemStack<T>, T>) components().get("banner_patterns"));
  }

  /**
   * Updates the banner patterns of the item stack.
   *
   * @param patterns a list of pattern data
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BannerPatternsComponent
   */
  AbstractItemStack<T> bannerPatterns(List<PatternData> patterns);

  /**
   * Retrieves the BaseColorComponent of the item stack if present.
   *
   * @return an Optional containing the BaseColorComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BaseColorComponent
   */
  default Optional<BaseColorComponent<AbstractItemStack<T>, T>> baseColor() {
    return Optional.ofNullable((BaseColorComponent<AbstractItemStack<T>, T>) components().get("base_color"));
  }

  /**
   * Updates the base color of the item stack.
   *
   * @param color the new base color
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BaseColorComponent
   */
  AbstractItemStack<T> baseColor(String color);

  /**
   * Retrieves the BlocksAttacksComponent associated with this method.
   *
   * @return an Optional containing the BlocksAttacksComponent if found, otherwise an empty Optional
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BlocksAttacksComponent
   */
  default Optional<BlocksAttacksComponent<AbstractItemStack<T>, T>> blocksAttacks() {
    return Optional.ofNullable((BlocksAttacksComponent<AbstractItemStack<T>, T>) components().get("blocks_attacks"));
  }

  /**
   * Blocks attacks with the specified damage type using this item.
   *
   * @param damage the type of damage to block attacks from
   * @return an AbstractItemStack object representing the item after blocking attacks
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BlocksAttacksComponent
   */
  AbstractItemStack<T> blocksAttacks(ItemDamage damage);

  /**
   * Retrieves the break sound component associated with the object.
   *
   * @return an Optional containing the BreakSoundComponent if it exists, else an empty Optional
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BreakSoundComponent
   */
  default Optional<BreakSoundComponent<AbstractItemStack<T>, T>> breakSound() {
    return Optional.ofNullable((BreakSoundComponent<AbstractItemStack<T>, T>) components().get("break_sound"));
  }

  /**
   * Set the break sound for this item stack.
   *
   * @param sound the sound to be played when the item is broken
   * @return the updated item stack object
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BreakSoundComponent
   */
  AbstractItemStack<T> breakSound(final String sound);

  /**
   * Retrieves the BucketEntityDataComponent of the item stack if present.
   *
   * @return an Optional containing the BucketEntityDataComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BucketEntityDataComponent
   */
  default Optional<BucketEntityDataComponent<AbstractItemStack<T>, T>> bucketEntityData() {
    return Optional.ofNullable((BucketEntityDataComponent<AbstractItemStack<T>, T>) components().get("bucket_entity_data"));
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
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BucketEntityDataComponent
   */
  AbstractItemStack<T> bucketEntityData(boolean noAI, boolean silent, boolean noGravity, boolean glowing,
                                              boolean invulnerable, float health, int age, int variant,
                                              long huntingCooldown, int bucketVariantTag, String type);

  /**
   * Retrieves the bundle component from the components map.
   *
   * @return An Optional containing the BundleComponent of AbstractItemStack with type T,
   *          or an empty Optional if the bundle component does not exist in the components map.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BundleComponent
   */
  default Optional<BundleComponent<AbstractItemStack<T>, T>> bundle() {
    return Optional.ofNullable((BundleComponent<AbstractItemStack<T>, T>) components().get("bundle"));
  }

  /**
   * Bundles a collection of {@link AbstractItemStack} instances into a single collection.
   *
   * @param items A {@link Map} containing integer keys and {@link AbstractItemStack} values to be bundled.
   * @return An {@link AbstractItemStack} instance that represents the bundled items.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see BundleComponent
   */
  AbstractItemStack<T> bundle(final Map<Integer, AbstractItemStack<T>> items);

  /**
   * Retrieves the CanBreakComponent of the item stack if present.
   *
   * @return an Optional containing the CanBreakComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see CanBreakComponent
   */
  default Optional<CanBreakComponent<AbstractItemStack<T>, T>> canBreak() {
    return Optional.ofNullable((CanBreakComponent<AbstractItemStack<T>, T>) components().get("can_break"));
  }

  /**
   * Updates the blocks that the item stack can break.
   *
   * @param predicates a list of block predicates
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see CanBreakComponent
   */
  AbstractItemStack<T> canBreak(List<BlockPredicate> predicates);

  /**
   * Retrieves the CanPlaceOnComponent of the item stack if present.
   *
   * @return an Optional containing the CanPlaceOnComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see CanPlaceOnComponent
   */
  default Optional<CanPlaceOnComponent<AbstractItemStack<T>, T>> canPlaceOn() {
    return Optional.ofNullable((CanPlaceOnComponent<AbstractItemStack<T>, T>) components().get("can_place_on"));
  }

  /**
   * Updates the blocks that the item stack can be placed on.
   *
   * @param predicates a list of block predicates
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see CanPlaceOnComponent
   */
  AbstractItemStack<T> canPlaceOn(List<BlockPredicate> predicates);

  /**
   * Retrieves the ConsumableComponent of the item stack if present.
   *
   * @return an Optional containing the ConsumableComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ConsumableComponent
   */
  default Optional<ConsumableComponent<AbstractItemStack<T>, T>> consumable() {
    return Optional.ofNullable((ConsumableComponent<AbstractItemStack<T>, T>) components().get("consumable"));
  }

  /**
   * Updates the consumable properties of the item stack.
   *
   * @param consumeSeconds     the time it takes to consume the item
   * @param animation          the animation to display when consuming
   * @param sound              the sound to play when consuming
   * @param hasConsumeParticles whether to show consume particles
   * @param effects            a list of effects applied on consumption
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ConsumableComponent
   */
  AbstractItemStack<T> consumable(float consumeSeconds, String animation, String sound, boolean hasConsumeParticles, List<ComponentEffect> effects);

  /**
   * Returns an {@link Optional} containing the {@link ContainerComponent} with type parameters {@link AbstractItemStack} and T stored in this container.
   *
   * @return an {@link Optional} object containing the {@link ContainerComponent} with type parameters {@link AbstractItemStack} and T, or an empty {@link Optional} if the component
   *  is not found
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ContainerComponent
   */
  default Optional<ContainerComponent<AbstractItemStack<T>, T>> container() {
    return Optional.ofNullable((ContainerComponent<AbstractItemStack<T>, T>) components().get("container"));
  }

  /**
   * Constructs a container with the given map of items.
   *
   * @param items a map of items where the key is the position of the item in the container and the value is the item
   * @return an AbstractItemStack container with the specified items
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ContainerComponent
   */
  AbstractItemStack<T> container(final Map<Integer, AbstractItemStack<T>> items);

  /**
   * Retrieves the CustomNameComponent of the item stack if present.
   *
   * @return an Optional containing the CustomNameComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see CustomNameComponent
   */
  default Optional<CustomNameComponent<AbstractItemStack<T>, T>> customName() {
    return Optional.ofNullable((CustomNameComponent<AbstractItemStack<T>, T>) components().get("custom_name"));
  }

  /**
   * Updates the custom name of the item stack.
   *
   * @param customName the custom name to set
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see CustomNameComponent
   */
  AbstractItemStack<T> customName(Component customName);

  /**
   * Retrieves the DamageComponent of the item stack if present.
   *
   * @return an Optional containing the DamageComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DamageComponent
   */
  default Optional<DamageComponent<AbstractItemStack<T>, T>> damage() {
    return Optional.ofNullable((DamageComponent<AbstractItemStack<T>, T>) components().get("damage"));
  }

  /**
   * Updates the damage of the item stack.
   *
   * @param damage the damage value to set
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DamageComponent
   */
  AbstractItemStack<T> damage(int damage);

  /**
   * Retrieves the DamageResistantComponent of the item stack if present.
   *
   * @return an Optional containing the DamageResistantComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DamageResistantComponent
   */
  default Optional<DamageResistantComponent<AbstractItemStack<T>, T>> damageResistant() {
    return Optional.ofNullable((DamageResistantComponent<AbstractItemStack<T>, T>) components().get("damage_resistant"));
  }

  /**
   * Updates the damage-resistant types of the item stack.
   *
   * @param types a list of damage-resistant types
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DamageResistantComponent
   */
  AbstractItemStack<T> damageResistant(List<String> types);

  /**
   * Retrieves the DeathProtectionComponent of the item stack if present.
   *
   * @return an Optional containing the DeathProtectionComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DeathProtectionComponent
   */
  default Optional<DeathProtectionComponent<AbstractItemStack<T>, T>> deathProtection() {
    return Optional.ofNullable((DeathProtectionComponent<AbstractItemStack<T>, T>) components().get("death_protection"));
  }

  /**
   * Updates the death protection effects of the item stack.
   *
   * @param deathEffects a list of death protection effects
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DeathProtectionComponent
   */
  AbstractItemStack<T> deathProtection(List<ComponentEffect> deathEffects);

  /**
   * Retrieves the DyedColorComponent of the item stack if present.
   *
   * @return an Optional containing the DyedColorComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DyedColorComponent
   */
  default Optional<DyedColorComponent<AbstractItemStack<T>, T>> dyedColor() {
    return Optional.ofNullable((DyedColorComponent<AbstractItemStack<T>, T>) components().get("dyed_color"));
  }

  /**
   * Updates the dyed color of the item stack.
   *
   * @param rgb the RGB color value to set
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see DyedColorComponent
   */
  AbstractItemStack<T> dyedColor(int rgb);

  /**
   * Retrieves the EnchantableComponent of the item stack if present.
   *
   * @return an Optional containing the EnchantableComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EnchantableComponent
   */
  default Optional<EnchantableComponent<AbstractItemStack<T>, T>> enchantable() {
    return Optional.ofNullable((EnchantableComponent<AbstractItemStack<T>, T>) components().get("enchantable"));
  }

  /**
   * Updates the enchantability of the item stack.
   *
   * @param value the enchantability value to set
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EnchantableComponent
   */
  AbstractItemStack<T> enchantable(int value);

  /**
   * Retrieves the EnchantmentGlintOverrideComponent of the item stack if present.
   *
   * @return an Optional containing the EnchantmentGlintOverrideComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EnchantmentGlintOverrideComponent
   */
  default Optional<EnchantmentGlintOverrideComponent<AbstractItemStack<T>, T>> enchantmentGlintOverride() {
    return Optional.ofNullable((EnchantmentGlintOverrideComponent<AbstractItemStack<T>, T>) components().get("enchantment_glint_override"));
  }

  /**
   * Updates the enchantment glint override of the item stack.
   *
   * @param glintOverride whether the enchantment glint should be overridden
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EnchantmentGlintOverrideComponent
   */
  AbstractItemStack<T> enchantmentGlintOverride(boolean glintOverride);

  /**
   * Retrieves the EnchantmentsComponent of the item stack if present.
   *
   * @return an Optional containing the EnchantmentsComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EnchantmentsComponent
   */
  default Optional<EnchantmentsComponent<AbstractItemStack<T>, T>> enchantments() {
    return Optional.ofNullable((EnchantmentsComponent<AbstractItemStack<T>, T>) components().get("enchantments"));
  }

  /**
   * Updates the enchantments of the item stack.
   *
   * @param levels a map of enchantments and their levels
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EnchantmentsComponent
   */
  AbstractItemStack<T> enchantments(Map<String, Integer> levels);

  /**
   * Retrieves the Entity Variant Component for the current stack.
   *
   * @return An optional containing the Entity Variant Component if present in the components map, otherwise empty.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EntityVariantComponent
   */
  default Optional<EntityVariantComponent<AbstractItemStack<T>, T>> entityVariant() {
    return Optional.ofNullable((EntityVariantComponent<AbstractItemStack<T>, T>) components().get("entity_variant"));
  }

  /**
   * Generates an AbstractItemStack based on the given entity and variant.
   *
   * @param entity the entity name to create the stack for
   * @param variant the variant of the entity
   * @return an AbstractItemStack representing the entity with the specified variant
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EntityVariantComponent
   */
  AbstractItemStack<T> entityVariant(final String entity, final String variant);

  /**
   * Retrieves the EquipComponent of the item stack if present.
   *
   * @return an Optional containing the EquipComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EquipComponent
   */
  default Optional<EquipComponent<AbstractItemStack<T>, T>> equip() {
    return Optional.ofNullable((EquipComponent<AbstractItemStack<T>, T>) components().get("equip"));
  }

  /**
   * Equips an item with specified parameters.
   *
   * @param cameraKey the key identifying the camera
   * @param equipSound the key identifying the equip sound
   * @param modelKey the key identifying the model
   * @param slot the slot in which the item should be equipped
   * @param damageOnHurt flag indicating if damage should be taken on hurt
   * @param dispensable flag indicating if the item is dispensable
   * @param swappable flag indicating if the item is swappable
   * @param equipOnInteract flag indicating if the item should be equipped on interact
   * @param entities a list of entities to be equipped
   *
   * @return an AbstractItemStack object representing the equipped item
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see EquipComponent
   */
  AbstractItemStack<T> equip(String cameraKey, String equipSound, String modelKey, EquipSlot slot,
                                   boolean damageOnHurt, boolean dispensable, boolean swappable,
                                   boolean equipOnInteract, List<String> entities);

  /**
   * Retrieves the FireworkExplosionComponent of the item stack if present.
   *
   * @return an Optional containing the FireworkExplosionComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see FireworkExplosionComponent
   */
  default Optional<FireworkExplosionComponent<AbstractItemStack<T>, T>> fireworkExplosion() {
    return Optional.ofNullable((FireworkExplosionComponent<AbstractItemStack<T>, T>) components().get("firework_explosion"));
  }

  /**
   * Updates the firework explosion properties of the item stack.
   *
   * @param explosion the explosion data to set
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see FireworkExplosionComponent
   */
  AbstractItemStack<T> fireworkExplosion(ExplosionData explosion);

  /**
   * Retrieves the FireworksComponent of the item stack if present.
   *
   * @return an Optional containing the FireworksComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see FireworksComponent
   */
  default Optional<FireworksComponent<AbstractItemStack<T>, T>> fireworks() {
    return Optional.ofNullable((FireworksComponent<AbstractItemStack<T>, T>) components().get("fireworks"));
  }

  /**
   * Updates the fireworks properties of the item stack.
   *
   * @param flightDuration the flight duration of the fireworks
   * @param explosions     a list of explosion data
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see FireworksComponent
   */
  AbstractItemStack<T> fireworks(byte flightDuration, List<ExplosionData> explosions);

  /**
   * Retrieves the FoodComponent of the item stack if present.
   *
   * @return an Optional containing the FoodComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see FoodComponent
   */
  default Optional<FoodComponent<AbstractItemStack<T>, T>> food() {
    return Optional.ofNullable((FoodComponent<AbstractItemStack<T>, T>) components().get("food"));
  }

  /**
   * Updates the food properties of the item stack.
   *
   * @param noHunger   whether the item causes no hunger
   * @param saturation the saturation value
   * @param nutrition  the nutrition value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see FoodComponent
   */
  AbstractItemStack<T> food(boolean noHunger, float saturation, int nutrition);

  /**
   * Retrieves the GliderComponent of the item stack if present.
   *
   * @return an Optional containing the GliderComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see GliderComponent
   */
  default Optional<GliderComponent<AbstractItemStack<T>, T>> glider() {
    return Optional.ofNullable((GliderComponent<AbstractItemStack<T>, T>) components().get("glider"));
  }

  /**
   * Updates the item stack to enable glider functionality.
   *
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see GliderComponent
   */
  AbstractItemStack<T> gliderTag();

  /**
   * Retrieves the HideAdditionalTooltipComponent of the item stack if present.
   *
   * @return an Optional containing the HideAdditionalTooltipComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see HideAdditionalTooltipComponent
   */
  default Optional<HideAdditionalTooltipComponent<AbstractItemStack<T>, T>> hideAdditionalTooltip() {
    return Optional.ofNullable((HideAdditionalTooltipComponent<AbstractItemStack<T>, T>) components().get("hide_additional_tooltip"));
  }

  /**
   * Updates the item stack to hide additional tooltip.
   *
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see HideAdditionalTooltipComponent
   */
  AbstractItemStack<T> hideAdditionalTooltipTag();

  /**
   * Retrieves the HideTooltipComponent of the item stack if present.
   *
   * @return an Optional containing the HideTooltipComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see HideTooltipComponent
   */
  default Optional<HideTooltipComponent<AbstractItemStack<T>, T>> hideTooltip() {
    return Optional.ofNullable((HideTooltipComponent<AbstractItemStack<T>, T>) components().get("hide_tooltip"));
  }

  /**
   * Updates the item stack to hide its tooltip.
   *
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see HideTooltipComponent
   */
  AbstractItemStack<T> hideTooltipTag();

  /**
   * Retrieves the InstrumentComponent of the item stack if present.
   *
   * @return an Optional containing the InstrumentComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see InstrumentComponent
   */
  default Optional<InstrumentComponent<AbstractItemStack<T>, T>> instrument() {
    return Optional.ofNullable((InstrumentComponent<AbstractItemStack<T>, T>) components().get("instrument"));
  }

  /**
   * Updates the instrument properties of the item stack.
   *
   * @param soundEvent the sound event identifier
   * @param useDuration the duration of the sound in ticks
   * @param range the range of the sound
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see InstrumentComponent
   */
  AbstractItemStack<T> instrument(String soundEvent, int useDuration, int range);

  /**
   * Retrieves the IntangibleProjectileComponent of the item stack if present.
   *
   * @return an Optional containing the IntangibleProjectileComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see IntangibleProjectileComponent
   */
  default Optional<IntangibleProjectileComponent<AbstractItemStack<T>, T>> intangibleProjectile() {
    return Optional.ofNullable((IntangibleProjectileComponent<AbstractItemStack<T>, T>) components().get("intangible_projectile"));
  }

  /**
   * Updates the item stack as an intangible projectile.
   *
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see IntangibleProjectileComponent
   */
  AbstractItemStack<T> intangibleProjectileTag();

  /**
   * Retrieves the ItemModelComponent of the item stack if present.
   *
   * @return an Optional containing the ItemModelComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ItemModelComponent
   */
  default Optional<ItemModelComponent<AbstractItemStack<T>, T>> itemModel() {
    return Optional.ofNullable((ItemModelComponent<AbstractItemStack<T>, T>) components().get("item_model"));
  }

  /**
   * Updates the model properties of the item stack.
   *
   * @param model the model identifier
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ItemModelComponent
   */
  AbstractItemStack<T> itemModel(String model);

  /**
   * Retrieves the ItemNameComponent of the item stack if present.
   *
   * @return an Optional containing the ItemNameComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ItemNameComponent
   */
  default Optional<ItemNameComponent<AbstractItemStack<T>, T>> itemName() {
    return Optional.ofNullable((ItemNameComponent<AbstractItemStack<T>, T>) components().get("item_name"));
  }

  /**
   * Updates the name of the item stack.
   *
   * @param itemName the name of the item
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ItemNameComponent
   */
  AbstractItemStack<T> itemName(Component itemName);

  /**
   * Retrieves the JukeBoxComponent of the item stack if present.
   *
   * @return an Optional containing the JukeBoxComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see JukeBoxComponent
   */
  default Optional<JukeBoxComponent<AbstractItemStack<T>, T>> jukebox() {
    return Optional.ofNullable((JukeBoxComponent<AbstractItemStack<T>, T>) components().get("jukebox"));
  }

  /**
   * Updates the jukebox properties of the item stack.
   *
   * @param song the song identifier
   * @param showInTooltip whether to display the song in the tooltip
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see JukeBoxComponent
   */
  AbstractItemStack<T> jukebox(String song, boolean showInTooltip);

  /**
   * Retrieves the LodestoneTrackerComponent of the item stack if present.
   *
   * @return an Optional containing the LodestoneTrackerComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see LodestoneTrackerComponent
   */
  default Optional<LodestoneTrackerComponent<AbstractItemStack<T>, T>> lodestoneTracker() {
    return Optional.ofNullable((LodestoneTrackerComponent<AbstractItemStack<T>, T>) components().get("lodestone_tracker"));
  }

  /**
   * Updates the lodestone tracker properties of the item stack.
   *
   * @param target the target identifier
   * @param pos the position array
   * @param dimension the dimension identifier
   * @param tracked whether the lodestone is tracked
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see LodestoneTrackerComponent
   */
  AbstractItemStack<T> lodestoneTracker(String target, int[] pos, String dimension, boolean tracked);

  /**
   * Retrieves the LoreComponent of the item stack if present.
   *
   * @return an Optional containing the LoreComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see LoreComponent
   */
  default Optional<LoreComponent<AbstractItemStack<T>, T>> lore() {
    return Optional.ofNullable((LoreComponent<AbstractItemStack<T>, T>) components().get("lore"));
  }

  /**
   * Updates the lore of the item stack.
   *
   * @param lore a list of lore strings
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see LoreComponent
   */
  AbstractItemStack<T> lore(List<Component> lore);

  /**
   * Retrieves the MapColorComponent of the item stack if present.
   *
   * @return an Optional containing the MapColorComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MapColorComponent
   */
  default Optional<MapColorComponent<AbstractItemStack<T>, T>> mapColor() {
    return Optional.ofNullable((MapColorComponent<AbstractItemStack<T>, T>) components().get("map_color"));
  }

  /**
   * Updates the map color of the item stack.
   *
   * @param mapColor the map color value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MapColorComponent
   */
  AbstractItemStack<T> mapColor(int mapColor);

  /**
   * Retrieves the MapIDComponent of the item stack if present.
   *
   * @return an Optional containing the MapIDComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MapIDComponent
   */
  default Optional<MapIDComponent<AbstractItemStack<T>, T>> mapId() {
    return Optional.ofNullable((MapIDComponent<AbstractItemStack<T>, T>) components().get("map_id"));
  }

  /**
   * Updates the map ID of the item stack.
   *
   * @param mapId the map ID value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MapIDComponent
   */
  AbstractItemStack<T> mapId(int mapId);

  /**
   * Retrieves the MaxDamageComponent of the item stack if present.
   *
   * @return an Optional containing the MaxDamageComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MaxDamageComponent
   */
  default Optional<MaxDamageComponent<AbstractItemStack<T>, T>> maxDamage() {
    return Optional.ofNullable((MaxDamageComponent<AbstractItemStack<T>, T>) components().get("max_damage"));
  }

  /**
   * Updates the maximum damage of the item stack.
   *
   * @param maxDamage the maximum damage value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MaxDamageComponent
   */
  AbstractItemStack<T> maxDamage(int maxDamage);

  /**
   * Retrieves the MaxStackSizeComponent of the item stack if present.
   *
   * @return an Optional containing the MaxStackSizeComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MaxStackSizeComponent
   */
  default Optional<MaxStackSizeComponent<AbstractItemStack<T>, T>> maxStackSize() {
    return Optional.ofNullable((MaxStackSizeComponent<AbstractItemStack<T>, T>) components().get("max_stack_size"));
  }

  /**
   * Updates the maximum stack size of the item stack.
   *
   * @param maxStackSize the maximum stack size value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see MaxStackSizeComponent
   */
  AbstractItemStack<T> maxStackSize(int maxStackSize);

  /**
   * Retrieves the ModelDataComponent of the item stack if present.
   *
   * @return an Optional containing the ModelDataComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ModelDataComponent
   */
  default Optional<ModelDataComponent<AbstractItemStack<T>, T>> modelData() {
    return Optional.ofNullable((ModelDataComponent<AbstractItemStack<T>, T>) components().get("model-data"));
  }

  /**
   * Updates the model data of the item stack.
   *
   * @param colours a list of color strings
   * @param floats  a list of float values
   * @param flags   a list of boolean flags
   * @param strings a list of string identifiers
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ModelDataComponent
   */
  AbstractItemStack<T> modelData(List<String> colours, List<Float> floats, List<Boolean> flags, List<String> strings);

  /**
   * Retrieves the ModelDataOldComponent of the item stack if present.
   *
   * @return an Optional containing the ModelDataOldComponent if it exists
   * @deprecated Since MC 1.21.3 Use {@link ItemModelComponent} and {@link ModelDataComponent}.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ModelDataComponent
   */
  default Optional<ModelDataOldComponent<AbstractItemStack<T>, T>> modelDataOld() {
    return Optional.ofNullable((ModelDataOldComponent<AbstractItemStack<T>, T>) components().get("model-data-old"));
  }

  /**
   * Retrieves the model data for a custom item stack.
   *
   * @param customModelData the custom model data to retrieve
   * @return an AbstractItemStack with the specified custom model data
   * @deprecated Since MC 1.21.3 Use {@link ItemModelComponent} and {@link ModelDataComponent}.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ModelDataOldComponent
   */
  AbstractItemStack<T> modelDataOld(int customModelData);

  /**
   * Retrieves the NoteBlockSoundComponent of the item stack if present.
   *
   * @return an Optional containing the NoteBlockSoundComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see NoteBlockSoundComponent
   */
  default Optional<NoteBlockSoundComponent<AbstractItemStack<T>, T>> noteBlockSound() {
    return Optional.ofNullable((NoteBlockSoundComponent<AbstractItemStack<T>, T>) components().get("note_block_sound"));
  }

  /**
   * Updates the note block sound of the item stack.
   *
   * @param soundId the identifier of the sound
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see NoteBlockSoundComponent
   */
  AbstractItemStack<T> noteBlockSound(String soundId);

  /**
   * Retrieves the OminousBottleAmplifierComponent of the item stack if present.
   *
   * @return an Optional containing the OminousBottleAmplifierComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see OminousBottleAmplifierComponent
   */
  default Optional<OminousBottleAmplifierComponent<AbstractItemStack<T>, T>> ominousBottleAmplifier() {
    return Optional.ofNullable((OminousBottleAmplifierComponent<AbstractItemStack<T>, T>) components().get("ominous_bottle_amplifier"));
  }

  /**
   * Updates the ominous bottle amplifier of the item stack.
   *
   * @param amplifier the amplifier value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see OminousBottleAmplifierComponent
   */
  AbstractItemStack<T> ominousBottleAmplifier(int amplifier);

  /**
   * Retrieves the PotDecorationsComponent of the item stack if present.
   *
   * @return an Optional containing the PotDecorationsComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see PotDecorationsComponent
   */
  default Optional<PotDecorationsComponent<AbstractItemStack<T>, T>> potDecorations() {
    return Optional.ofNullable((PotDecorationsComponent<AbstractItemStack<T>, T>) components().get("pot_decorations"));
  }

  /**
   * Updates the pot decorations of the item stack.
   *
   * @param decorations a list of decorations
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see PotDecorationsComponent
   */
  AbstractItemStack<T> potDecorations(List<String> decorations);

  /**
   * Retrieves the PotionContentsComponent of the item stack if present.
   *
   * @return an Optional containing the PotionContentsComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see PotionContentsComponent
   */
  default Optional<PotionContentsComponent<AbstractItemStack<T>, T>> potionContents() {
    return Optional.ofNullable((PotionContentsComponent<AbstractItemStack<T>, T>) components().get("potion_contents"));
  }

  /**
   * Updates the potion contents of the item stack.
   *
   * @param potionId   the ID of the potion
   * @param customColor the custom color of the potion
   * @param customName the custom name of the potion
   * @param effects    a list of effect instances
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see PotionContentsComponent
   */
  AbstractItemStack<T> potionContents(String potionId, int customColor, String customName, List<EffectInstance> effects);

  /**
   * Retrieves the potion duration scale component from the item's components. Since MC Snapshot 25w02a.
   *
   * @return an Optional containing the PotionDurationScaleComponent if present, otherwise an empty Optional
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see PotionDurationScaleComponent
   */
  default Optional<PotionDurationScaleComponent<AbstractItemStack<T>, T>> potionDuration() {
    return Optional.ofNullable((PotionDurationScaleComponent<AbstractItemStack<T>, T>) components().get("potion_duration_scale"));
  }

  /**
   * Sets the duration of a potion effect for the item stack. Since MC Snapshot 25w02a.
   *
   * @param potionDuration the duration of the potion effect in seconds
   * @return the modified AbstractItemStack object with the updated potion duration
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see PotionDurationScaleComponent
   */
  AbstractItemStack<T> potionDuration(final float potionDuration);

  /**
   * Retrieves the profile component from the components map if it exists.
   *
   * @return An Optional containing the profile component if found, or an empty Optional otherwise.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProfileComponent
   */
  default Optional<ProfileComponent<AbstractItemStack<T>, T>> profile() {
    return Optional.ofNullable((ProfileComponent<AbstractItemStack<T>, T>) components().get("profile"));
  }

  /**
   * Create a Profile based on the provided name and UUID.
   *
   * @param name The name for the profile.
   * @param uuid The UUID for the profile.
   * @return A new AbstractItemStack based on the created SkullProfile with the given name and UUID.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProfileComponent
   */
  default AbstractItemStack<T> profile(final String name, final UUID uuid) {
    return profile(new SkullProfile(name, uuid));
  }

  /**
   * Profiles the item stack with the given name, UUID, and texture.
   *
   * @param name The name of the profile.
   * @param uuid The UUID of the profile.
   * @param texture The texture of the profile.
   * @return The AbstractItemStack with the specified profile.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProfileComponent
   */
  default AbstractItemStack<T> profile(final String name, final UUID uuid, final String texture) {
    return profile(new SkullProfile(name, uuid, texture));
  }

  /**
   * Profiles the given SkullProfile to the AbstractItemStack object.
   *
   * @param profile the SkullProfile to be assigned
   * @return an AbstractItemStack object with the provided profile
   */
  AbstractItemStack<T> profile(final SkullProfile profile);

  /**
   * Retrieves the component that provides banner patterns.
   *
   * @return an Optional containing the ProvidesBannerPatternsComponent for the specified item stack type T,
   *         or an empty Optional if the component is not present
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProvidesBannerPatternsComponent
   */
  default Optional<ProvidesBannerPatternsComponent<AbstractItemStack<T>, T>> providesPattern() {
    return Optional.ofNullable((ProvidesBannerPatternsComponent<AbstractItemStack<T>, T>) components().get("provides_trim_material"));
  }

  /**
   * Retrieve an AbstractItemStack object that provides a specific pattern identified by the patternTag.
   *
   * @param patternTag The unique identifier of the pattern to be provided.
   * @return An AbstractItemStack object that provides the specified pattern.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProvidesBannerPatternsComponent
   */
  AbstractItemStack<T> providesPattern(final String patternTag);

  /**
   * Retrieves the component responsible for providing trim materials.
   *
   * @return An Optional containing the ProvidesTrimMaterialComponent for trim, or an empty Optional if not found.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProvidesTrimMaterialComponent
   */
  default Optional<ProvidesTrimMaterialComponent<AbstractItemStack<T>, T>> providesTrim() {
    return Optional.ofNullable((ProvidesTrimMaterialComponent<AbstractItemStack<T>, T>) components().get("provides_trim_material"));
  }

  /**
   * Applies the specified trim material
   *
   * @param material the trim material
   * @return an AbstractItemStack object representing the trimmed material
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ProvidesTrimMaterialComponent
   */
  AbstractItemStack<T> providesTrim(final String material);

  /**
   * Retrieves the RarityComponent of the item stack if present.
   *
   * @return an Optional containing the RarityComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RarityComponent
   */
  default Optional<RarityComponent<AbstractItemStack<T>, T>> rarity() {
    return Optional.ofNullable((RarityComponent<AbstractItemStack<T>, T>) components().get("rarity"));
  }

  /**
   * Updates the rarity of the item stack.
   *
   * @param rarity the rarity value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RarityComponent
   */
  AbstractItemStack<T> rarity(String rarity);

  /**
   * Retrieves the RecipesComponent of the item stack if present.
   *
   * @return an Optional containing the RecipesComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RecipesComponent
   */
  default Optional<RecipesComponent<AbstractItemStack<T>, T>> recipes() {
    return Optional.ofNullable((RecipesComponent<AbstractItemStack<T>, T>) components().get("recipes"));
  }

  /**
   * Updates the recipes of the item stack.
   *
   * @param recipes a list of recipe identifiers
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RecipesComponent
   */
  AbstractItemStack<T> recipes(List<String> recipes);

  /**
   * Retrieves the RepairableComponent of the item stack if present.
   *
   * @return an Optional containing the RepairableComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RepairableComponent
   */
  default Optional<RepairableComponent<AbstractItemStack<T>, T>> repairable() {
    return Optional.ofNullable((RepairableComponent<AbstractItemStack<T>, T>) components().get("repairable"));
  }

  /**
   * Updates the repairable items of the item stack.
   *
   * @param repairItems a list of repair item identifiers
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RepairableComponent
   */
  AbstractItemStack<T> repairable(List<String> repairItems);

  /**
   * Retrieves the RepairCostComponent of the item stack if present.
   *
   * @return an Optional containing the RepairCostComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RepairCostComponent
   */
  default Optional<RepairCostComponent<AbstractItemStack<T>, T>> repairCost() {
    return Optional.ofNullable((RepairCostComponent<AbstractItemStack<T>, T>) components().get("repair_cost"));
  }

  /**
   * Updates the repair cost of the item stack.
   *
   * @param repairCost the repair cost value
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see RepairCostComponent
   */
  AbstractItemStack<T> repairCost(int repairCost);

  /**
   * Retrieves the StoredEnchantmentsComponent of the item stack if present.
   *
   * @return an Optional containing the StoredEnchantmentsComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see StoredEnchantmentsComponent
   */
  default Optional<StoredEnchantmentsComponent<AbstractItemStack<T>, T>> storedEnchantments() {
    return Optional.ofNullable((StoredEnchantmentsComponent<AbstractItemStack<T>, T>) components().get("stored_enchantments"));
  }

  /**
   * Updates the stored enchantments of the item stack.
   *
   * @param enchantments a map of enchantment names to their levels
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see StoredEnchantmentsComponent
   */
  AbstractItemStack<T> storedEnchantments(Map<String, Integer> enchantments);

  /**
   * Retrieves the SuspiciousStewEffectsComponent of the item stack if present.
   *
   * @return an Optional containing the SuspiciousStewEffectsComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see SuspiciousStewEffectsComponent
   */
  default Optional<SuspiciousStewEffectsComponent<AbstractItemStack<T>, T>> suspiciousStewEffects() {

    return Optional.ofNullable((SuspiciousStewEffectsComponent<AbstractItemStack<T>, T>) components().get("suspicious_stew_effects"));
  }

  /**
   * Applies the given effects to the suspicious stew item.
   *
   * @param effects a list of EffectInstance objects representing the effects to be applied to the suspicious stew
   * @return an AbstractItemStack representing the suspicious stew with the applied effects
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see SuspiciousStewEffectsComponent
   */
  AbstractItemStack<T> suspiciousStewEffects(final List<EffectInstance> effects);

  /**
   * Applies the given effects to a suspicious stew item.
   *
   * @param effects an array of EffectInstance objects representing the effects to apply to the suspicious stew
   * @return an AbstractItemStack representing the suspicious stew item with the applied effects
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see SuspiciousStewEffectsComponent
   */
  AbstractItemStack<T> suspiciousStewEffects(final EffectInstance... effects);

  /**
   * Retrieves the ToolComponent of the item stack if present.
   *
   * @return an Optional containing the ToolComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ToolComponent
   */
  default Optional<ToolComponent<AbstractItemStack<T>, T>> tool() {
    return Optional.ofNullable((ToolComponent<AbstractItemStack<T>, T>) components().get("tool"));
  }

  /**
   * Constructs a new tool item with the specified characteristics.
   *
   * @param defaultSpeed the default speed of the tool
   * @param blockDamage the damage inflicted by the tool to blocks
   * @param canDestroyBlocksCreative if the tool can destroy blocks in creative mode
   * @param rules a list of rules that define the behavior of the tool
   * @return an AbstractItemStack instance representing the created tool
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see ToolComponent
   */
  AbstractItemStack<T> tool(float defaultSpeed, int blockDamage, boolean canDestroyBlocksCreative, List<ToolRule> rules);

  /**
   * Retrieves the tooltip display component associated with the item.
   *
   * @return An Optional containing the tooltip display component, or empty if none is found.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see TooltipDisplayComponent
   */
  default Optional<TooltipDisplayComponent<AbstractItemStack<T>, T>> tooltipDisplay() {
    return Optional.ofNullable((TooltipDisplayComponent<AbstractItemStack<T>, T>) components().get("tooltip_display"));
  }

  /**
   * Displays tooltip for the item stack with an option to hide certain components.
   *
   * @param hideTooltip Flag to hide the tooltip
   * @param hiddenComponents Array of components to hide in the tooltip
   * @return An AbstractItemStack object with the tooltip displayed
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see TooltipDisplayComponent
   */
  AbstractItemStack<T> tooltipDisplay(final boolean hideTooltip, final String... hiddenComponents);

  /**
   * Retrieves the TooltipStyleComponent of the item stack if present.
   *
   * @return an Optional containing the TooltipStyleComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see TooltipStyleComponent
   */
  default Optional<TooltipStyleComponent<AbstractItemStack<T>, T>> tooltipStyle() {
    return Optional.ofNullable((TooltipStyleComponent<AbstractItemStack<T>, T>) components().get("tooltip_style"));
  }

  /**
   * Updates the tooltip style of the item stack.
   *
   * @param style the style to apply to the tooltip
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see TooltipStyleComponent
   */
  AbstractItemStack<T> tooltipStyle(String style);

  /**
   * Retrieves the TrimComponent of the item stack if present.
   *
   * @return an Optional containing the TrimComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see TrimComponent
   */
  default Optional<TrimComponent<AbstractItemStack<T>, T>> trim() {
    return Optional.ofNullable((TrimComponent<AbstractItemStack<T>, T>) components().get("trim"));
  }

  /**
   * Updates the trim properties of the item stack.
   *
   * @param pattern       the trim pattern
   * @param material      the trim material
   * @param showInTooltip whether to display the trim in the tooltip
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see TrimComponent
   */
  AbstractItemStack<T> trim(String pattern, String material, boolean showInTooltip);

  /**
   * Retrieves the UnbreakableComponent of the item stack if present.
   *
   * @return an Optional containing the UnbreakableComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see UnbreakableComponent
   */
  default Optional<UnbreakableComponent<AbstractItemStack<T>, T>> unbreakable() {
    return Optional.ofNullable((UnbreakableComponent<AbstractItemStack<T>, T>) components().get("unbreakable"));
  }

  /**
   * Updates the unbreakable property of the item stack.
   *
   * @param showInTooltip whether to display the unbreakable property in the tooltip
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see UnbreakableComponent
   */
  AbstractItemStack<T> unbreakable(boolean showInTooltip);

  /**
   * Retrieves the UseCooldownComponent of the item stack if present.
   *
   * @return an Optional containing the UseCooldownComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see UseCooldownComponent
   */
  default Optional<UseCooldownComponent<AbstractItemStack<T>, T>> useCooldown() {
    return Optional.ofNullable((UseCooldownComponent<AbstractItemStack<T>, T>) components().get("use_cooldown"));
  }

  /**
   * Updates the use cooldown properties of the item stack.
   *
   * @param cooldownGroup the cooldown group identifier
   * @param seconds       the cooldown duration in seconds
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see UseCooldownComponent
   */
  AbstractItemStack<T> useCooldown(String cooldownGroup, float seconds);

  /**
   * Retrieves the weapon component associated with this item. Since MC Snapshot 25w02a.
   *
   * @return an Optional containing the weapon component if it exists, otherwise an empty Optional.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see WeaponComponent
   */
  default Optional<WeaponComponent<AbstractItemStack<T>, T>> weapon() {
    return Optional.ofNullable((WeaponComponent<AbstractItemStack<T>, T>) components().get("weapon"));
  }

  /**
   * Represents a weapon item that can be used for combat. Since MC Snapshot 25w02a.
   *
   * @param damagePerAttack The amount of damage this weapon inflicts per attack
   * @param canDisableBlocking Whether this weapon can disable blocking when used
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see WeaponComponent
   */
  AbstractItemStack<T> weapon(final int damagePerAttack, final boolean canDisableBlocking);

  /**
   * Retrieves the WritableBookContentComponent of the item stack if present.
   *
   * @return an Optional containing the WritableBookContentComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see WritableBookContentComponent
   */
  default Optional<WritableBookContentComponent<AbstractItemStack<T>, T>> writableBookContent() {
    return Optional.ofNullable((WritableBookContentComponent<AbstractItemStack<T>, T>) components().get("writable_book_content"));
  }

  /**
   * Updates the writable book content of the item stack.
   *
   * @param pages the pages to include in the writable book
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see WritableBookContentComponent
   */
  AbstractItemStack<T> writableBookContent(List<String> pages);

  /**
   * Retrieves the WrittenBookContentComponent of the item stack if present.
   *
   * @return an Optional containing the WrittenBookContentComponent if it exists
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see WrittenBookContentComponent
   */
  default Optional<WrittenBookContentComponent<AbstractItemStack<T>, T>> writtenBookContent() {
    return Optional.ofNullable((WrittenBookContentComponent<AbstractItemStack<T>, T>) components().get("written_book_content"));
  }

  /**
   * Updates the written book content of the item stack.
   *
   * @param title     the title of the book
   * @param author    the author of the book
   * @param generation the generation of the book
   * @param resolved  whether the book is resolved
   * @param pages     the pages to include in the book
   * @return the updated AbstractItemStack instance
   * @since 0.2.0.0
   * @author creatorfromhell
   * @see WrittenBookContentComponent
   */
  AbstractItemStack<T> writtenBookContent(String title, String author, int generation, boolean resolved, List<String> pages);
}
