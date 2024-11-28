package net.tnemc.item;

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
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.persistent.PersistentDataHolder;
import net.tnemc.item.persistent.PersistentDataType;
import net.tnemc.item.providers.SkullProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
   */
  AbstractItemStack<T> of(final String material, final int amount);

  /**
   * Creates a new item stack from a locale-specific object.
   *
   * @param locale The locale-specific representation.
   * @return A new item stack instance.
   */
  AbstractItemStack<T> of(T locale);

  /**
   * Creates a new item stack from a JSON representation.
   *
   * @param json The JSON object containing item stack data.
   * @return A new item stack instance.
   * @throws ParseException If the JSON structure is invalid.
   */
  AbstractItemStack<T> of(JSONObject json) throws ParseException;

  /**
   * Sets the resistance properties of the item stack.
   *
   * @param resistence A set of resistance types.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> resistence(HashSet<String> resistence);

  /**
   * Sets the item flags.
   *
   * @param flags A list of flags to apply to the item.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> flags(List<String> flags);

  /**
   * Sets the lore (descriptive text) of the item stack.
   *
   * @param lore A list of components representing the lore.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> lore(List<Component> lore);

  /**
   * Adds an attribute to the item stack.
   *
   * @param name      The name of the attribute.
   * @param attribute The attribute to add.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> attribute(String name, SerialAttribute attribute);

  /**
   * Adds multiple attributes to the item stack.
   *
   * @param attributes A map of attribute names and values.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> attribute(Map<String, SerialAttribute> attributes);

  /**
   * Adds an enchantment to the item stack.
   *
   * @param enchantment The enchantment name.
   * @param level       The level of the enchantment.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> enchant(String enchantment, int level);

  /**
   * Adds multiple enchantments to the item stack.
   *
   * @param enchantments A map of enchantment names and levels.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> enchant(Map<String, Integer> enchantments);

  /**
   * Adds enchantments to the item stack by name.
   *
   * @param enchantments A list of enchantment names.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> enchant(List<String> enchantments);

  /**
   * Sets the material of the item stack.
   *
   * @param material The material name.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> material(String material);

  /**
   * Sets the quantity of the item stack.
   *
   * @param amount The number of items in the stack.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> amount(final int amount);

  /**
   * Sets the inventory slot of the item stack.
   *
   * @param slot The slot index.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> slot(int slot);

  /**
   * Sets the display name of the item stack.
   *
   * @param display The display component.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> display(Component display);

  /**
   * Enables or disables debug mode for the item stack.
   *
   * @param debug True to enable, false to disable.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> debug(boolean debug);

  /**
   * Sets the damage value of the item stack.
   *
   * @param damage The damage value.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> damage(short damage);

  /**
   * Sets the skull profile of the item stack.
   *
   * @param profile The skull profile to apply.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> profile(final SkullProfile profile);

  /**
   * Sets the custom model data of the item stack.
   *
   * @param modelData The model data value.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> modelData(int modelData);

  /**
   * Sets the model identifier for the item stack.
   *
   * @param model The model identifier.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> model(String model);

  /**
   * Sets the unbreakable status of the item stack.
   *
   * @param unbreakable True if the item should be unbreakable.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> unbreakable(boolean unbreakable);

  /**
   * Sets the maximum stack size of the item.
   *
   * @param maxStack The maximum stack size.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> maxStack(int maxStack);

  /**
   * Controls the visibility of tooltips.
   *
   * @param hideTooltip True to hide tooltips.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> hideTooltip(boolean hideTooltip);

  /**
   * Sets the tooltip style for the item stack.
   *
   * @param tooltipStyle The tooltip style identifier.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> tooltipStyle(@NotNull final String tooltipStyle);

  /**
   * Sets whether the item stack should have an enchantment glint effect.
   *
   * @param enchantGlint True to enable the glint effect, false to disable.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> enchantGlint(boolean enchantGlint);

  /**
   * Sets the enchantability level of the item stack.
   *
   * @param enchantable The enchantability level.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> enchantable(final int enchantable);

  /**
   * Sets the rarity of the item stack.
   *
   * @param rarity The rarity identifier.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> rarity(String rarity);

  /**
   * Sets whether the item stack can function as a glider.
   *
   * @param glider True if the item should act as a glider, false otherwise.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> glider(boolean glider);

  /**
   * Sets the item that remains after the current item stack is used.
   *
   * @param remainder The remaining item stack.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> remainder(@Nullable final AbstractItemStack<T> remainder);

  /**
   * Applies custom data to the item stack.
   *
   * @param data The serialized item data.
   * @return The updated item stack instance.
   */
  AbstractItemStack<T> applyData(SerialItemData<T> data);

  /**
   * Applies a serialized component to the item stack.
   *
   * @param component The component to apply.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  default AbstractItemStack<T> applyComponent(final SerialComponent<T> component) {
    components().put(component.getType(), component);
    return this;
  }

  /**
   * Applies persistent data to the item stack.
   *
   * @param data The persistent data to apply.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  default AbstractItemStack<T> applyPersistent(final PersistentDataType<?> data) {
    persistentHolder().getData().put(data.identifier(), data);
    return this;
  }

  /**
   * Replaces the persistent data holder for the item stack.
   *
   * @param newHolder The new persistent data holder.
   * @param replaceOld True to replace existing data, false to merge.
   * @return The updated item stack instance.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> applyPersistentHolder(final PersistentDataHolder newHolder, boolean replaceOld);

  /**
   * Retrieves the resistance types applied to the item stack.
   *
   * @return A set of resistance types.
   * @since 0.1.7.7
   */
  HashSet<String> resistence();

  /**
   * Retrieves the item flags.
   *
   * @return A list of flags applied to the item.
   */
  List<String> flags();

  /**
   * Retrieves the lore (descriptive text) of the item stack.
   *
   * @return A list of lore components.
   */
  List<Component> lore();

  /**
   * Retrieves the attributes applied to the item stack.
   *
   * @return A map of attribute names and values.
   */
  Map<String, SerialAttribute> attributes();

  /**
   * Retrieves the enchantments applied to the item stack.
   *
   * @return A map of enchantment names and levels.
   */
  Map<String, Integer> enchantments();

  /**
   * Retrieves the components applied to the item stack.
   *
   * @return A map of component types and their serialized representations.
   * @since 0.1.7.7
   */
  Map<String, SerialComponent<T>> components();

  /**
   * Retrieves the persistent data holder for the item stack.
   *
   * @return The persistent data holder.
   * @since 0.1.7.7
   */
  PersistentDataHolder persistentHolder();

  /**
   * Retrieves the material of the item stack.
   *
   * @return The material name.
   */
  String material();

  /**
   * Retrieves the amount of items in the stack.
   *
   * @return The quantity of items.
   */
  int amount();

  /**
   * Updates the quantity of items in the stack.
   *
   * @param amount The new quantity.
   */
  void setAmount(final int amount);

  /**
   * Retrieves the inventory slot of the item stack.
   *
   * @return The slot index.
   */
  int slot();

  /**
   * Retrieves the display name of the item stack.
   *
   * @return The display component.
   */
  Component display();

  /**
   * Retrieves the damage value of the item stack.
   *
   * @return The damage value.
   */
  short damage();

  /**
   * Retrieves the skull profile of the item stack, if applicable.
   *
   * @return An optional skull profile.
   */
  Optional<SkullProfile> profile();

  /**
   * Retrieves the custom model data of the item stack.
   *
   * @return The model data value.
   */
  int modelData();

  /**
   * Retrieves the model identifier of the item stack.
   *
   * @return The model identifier.
   * @since 0.1.7.7
   */
  String model();

  /**
   * Checks if the item stack is unbreakable.
   *
   * @return True if the item is unbreakable, otherwise false.
   */
  boolean unbreakable();

  /**
   * Retrieves the maximum stack size for the item stack.
   *
   * @return The maximum stack size.
   * @since 0.1.7.7
   */
  int maxStack();

  /**
   * Checks if tooltips are hidden for the item stack.
   *
   * @return True if tooltips are hidden, otherwise false.
   * @since 0.1.7.7
   */
  boolean hideTooltip();

  /**
   * Retrieves the tooltip style of the item stack.
   *
   * @return The tooltip style identifier.
   * @since 0.1.7.7
   */
  String tooltipStyle();

  /**
   * Checks if the item stack has an enchantment glint effect.
   *
   * @return True if the enchantment glint is enabled, otherwise false.
   * @since 0.1.7.7
   */
  boolean enchantGlint();

  /**
   * Retrieves the enchantability level of the item stack.
   *
   * @return The enchantability level.
   * @since 0.1.7.7
   */
  int enchantable();

  /**
   * Retrieves the rarity of the item stack.
   *
   * @return The rarity identifier.
   * @since 0.1.7.7
   */
  String rarity();

  /**
   * Checks if the item stack functions as a glider.
   *
   * @return True if the item acts as a glider, otherwise false.
   * @since 0.1.7.7
   */
  boolean glider();

  /**
   * Retrieves the remaining item stack after the current stack is used.
   *
   * @return The remainder item stack.
   * @since 0.1.7.7
   */
  AbstractItemStack<T> remainder();

  /**
   * Marks the item stack as dirty, indicating changes have been made.
   */
  void markDirty();

  /**
   * Retrieves the serialized item data, if present.
   *
   * @return An optional serialized item data.
   */
  Optional<SerialItemData<T>> data();

  /**
   * Returns true if the provided item is similar to this. An item is similar if the basic
   * information is the same, except for the amount. What this includes: - material - display -
   * modelData - flags - lore - attributes - enchantments
   *
   * What this does not include: - Item Data.
   *
   * @param compare The stack to compare.
   *
   * @return True if the two are similar, otherwise false.
   */
  boolean similar(AbstractItemStack<? extends T> compare);

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  T locale();

  //Since 0.1.7.7
  default JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("slot", slot());
    json.put("material", material());
    json.put("amount", amount());
    json.put("unbreakable", unbreakable());
    if(display() != null && !Component.EQUALS.test(display(), Component.empty())) {
      json.put("display", JSONComponentSerializer.json().serialize(display()));
    }
    json.put("damage", damage());
    if(modelData() != -1) json.put("modelData", modelData());
    if(lore() != null && !lore().isEmpty()) {

      final LinkedList<String> str = new LinkedList<>();
      for(final Component comp : lore()) {
        str.add(JSONComponentSerializer.json().serialize(comp));
      }

      json.put("lore", String.join(",", str));
    }

    if(flags() != null && !flags().isEmpty()) {
      json.put("flags", String.join(",", flags()));
    }

    final JSONObject object = new JSONObject();
    enchantments().forEach(object::put);
    json.put("enchantments", object);

    if(!attributes().isEmpty()) {
      final JSONObject attr = new JSONObject();

      attributes().forEach((name, modifier)->{
        final JSONObject mod = new JSONObject();

        mod.put("id", modifier.getIdentifier().toString());
        mod.put("name", modifier.getName());
        mod.put("amount", modifier.getAmount());
        mod.put("operation", modifier.getOperation().name());
        if(modifier.getSlot() != null) mod.put("slot", modifier.getSlot().name());

        attr.put(name, mod);
      });
      json.put("attributes", attr);
    }

    if(!persistentHolder().getData().isEmpty()) {
      json.put("persistent-data", persistentHolder().toJSON());
    }

    if(data().isPresent()) {
      json.put("data", data().get().toJSON());
    }
    return json;
  }

  //Since 0.1.7.7
  default String serialize() {

    return toJSON().toJSONString();
  }

  //Since 0.1.7.7
  default void unserialize(final String serialized) throws ParseException {

    parse(new JSONHelper((JSONObject)new JSONParser().parse(serialized)));
  }

  //Since 0.1.7.7
  default void unserialize(final JSONObject json) throws ParseException {

    parse(new JSONHelper(json));
  }

  //Since 0.1.7.7
  void parse(final JSONHelper json) throws ParseException;

  default boolean jsonEquals(final AbstractItemStack<T> serial) {

    return serialize().equals(serial.serialize());
  }

  default boolean textComponentsEqual(final List<Component> list1, final List<Component> list2) {

    final LinkedList<String> list1Copy = new LinkedList<>();
    for(final Component component : list1) {
      list1Copy.add(PlainTextComponentSerializer.plainText().serialize(component));
    }

    final LinkedList<String> list2Copy = new LinkedList<>();
    for(final Component component : list2) {
      list2Copy.add(PlainTextComponentSerializer.plainText().serialize(component));
    }
    return listsEquals(list1Copy, list2Copy);
  }

  default <V> boolean listsEquals(final List<V> list1, final List<V> list2) {

    return new HashSet<>(list1).containsAll(list2) && new HashSet<>(list2).containsAll(list1);
  }

  default <V> boolean listsEquals(final List<V> list1, final List<V> list2, final boolean debug) {

    if(debug) {

      System.out.println("List 1");
      for(final V item : list1) {
        System.out.println("Item: " + item);
      }

      System.out.println("List 2");
      for(final V item : list2) {
        System.out.println("Item: " + item);
      }
    }

    return new HashSet<>(list1).containsAll(list2) && new HashSet<>(list2).containsAll(list1);
  }

  default <V> boolean setsEquals(final Set<V> list1, final Set<V> list2) {

    return new HashSet<>(list1).containsAll(list2) && new HashSet<>(list2).containsAll(list1);
  }
}