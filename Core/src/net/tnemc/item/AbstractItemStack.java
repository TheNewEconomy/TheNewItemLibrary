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
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.providers.SkullProfile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface AbstractItemStack<T> extends Cloneable {

  AbstractItemStack<T> of(final String material, final int amount);

  AbstractItemStack<T> of(SerialItem<T> serialItem);

  AbstractItemStack<T> of(T locale);

  AbstractItemStack<T> of(JSONObject json) throws ParseException;

  AbstractItemStack<T> flags(List<String> flags);

  AbstractItemStack<T> lore(List<Component> lore);

  AbstractItemStack<T> attribute(String name, SerialAttribute attribute);

  AbstractItemStack<T> attribute(Map<String, SerialAttribute> attributes);

  AbstractItemStack<T> enchant(String enchantment, int level);

  AbstractItemStack<T> enchant(Map<String, Integer> enchantments);

  AbstractItemStack<T> enchant(List<String> enchantments);

  AbstractItemStack<T> material(String material);

  AbstractItemStack<T> amount(final int amount);

  AbstractItemStack<T> slot(int slot);

  AbstractItemStack<T> display(Component display);

  AbstractItemStack<T> debug(boolean debug);

  AbstractItemStack<T> damage(short damage);

  AbstractItemStack<T> profile(final SkullProfile profile);

  AbstractItemStack<T> modelData(int modelData);

  AbstractItemStack<T> unbreakable(boolean unbreakable);

  //since 0.1.7.7
  AbstractItemStack<T> maxStack(int maxStack);

  //since 0.1.7.7
  AbstractItemStack<T> hideTooltip(boolean hideTooltip);

  //since 0.1.7.7
  AbstractItemStack<T> fireResistant(boolean fireResistant);

  //since 0.1.7.7
  AbstractItemStack<T> enchantGlint(boolean enchantGlint);

  //since 0.1.7.7
  AbstractItemStack<T> rarity(String rarity);

  AbstractItemStack<T> applyData(SerialItemData<T> data);

  //since 0.1.7.7
  default AbstractItemStack<T> applyComponent(final SerialComponent<T> component) {

    components().put(component.getType(), component);
    return this;
  }

  List<String> flags();

  List<Component> lore();

  Map<String, SerialAttribute> attributes();

  Map<String, Integer> enchantments();

  //since 0.1.7.7
  Map<String, SerialComponent<T>> components();

  String material();

  int amount();

  void setAmount(final int amount);

  int slot();

  Component display();

  short damage();

  Optional<SkullProfile> profile();

  int modelData();

  boolean unbreakable();

  //since 0.1.7.7
  int maxStack();

  //since 0.1.7.7
  boolean hideTooltip();

  //since 0.1.7.7
  boolean fireResistant();

  //since 0.1.7.7
  boolean enchantGlint();

  //since 0.1.7.7
  String rarity();

  void markDirty();

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

  default void unserialize(final String serialized) throws ParseException {

    parse(new JSONHelper((JSONObject)new JSONParser().parse(serialized)));
  }

  default void unserialize(final JSONObject json) throws ParseException {

    parse(new JSONHelper(json));
  }

  void parse(final JSONHelper json) throws ParseException;

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