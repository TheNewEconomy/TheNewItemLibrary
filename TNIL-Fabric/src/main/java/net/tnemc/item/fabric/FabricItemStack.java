package net.tnemc.item.fabric;
/*
 * The New Kings
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.item.ItemStack;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * FabricItemStack
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class FabricItemStack implements AbstractItemStack<ItemStack> {

  private boolean dirty = false;
  private ItemStack stack;



  @Override
  public AbstractItemStack<ItemStack> of(String material, int amount) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> of(SerialItem<ItemStack> serialItem) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> of(ItemStack locale) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> flags(List<String> flags) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> lore(List<String> lore) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> attribute(String name, SerialAttribute attribute) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> attribute(Map<String, SerialAttribute> attributes) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(String enchantment, int level) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(Map<String, Integer> enchantments) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(List<String> enchantments) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> material(String material) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> amount(int amount) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> slot(int slot) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> display(String display) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> damage(short damage) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> modelData(int modelData) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> unbreakable(boolean unbreakable) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> applyData(SerialItemData<ItemStack> data) {
    return null;
  }

  @Override
  public List<String> flags() {
    return List.of();
  }

  @Override
  public List<String> lore() {
    return List.of();
  }

  @Override
  public Map<String, SerialAttribute> attributes() {
    return Map.of();
  }

  @Override
  public Map<String, Integer> enchantments() {
    return Map.of();
  }

  @Override
  public String material() {
    return "";
  }

  @Override
  public int amount() {
    return 0;
  }

  @Override
  public void setAmount(int amount) {

  }

  @Override
  public int slot() {
    return 0;
  }

  @Override
  public String display() {
    return "";
  }

  @Override
  public short damage() {
    return 0;
  }

  @Override
  public int modelData() {
    return 0;
  }

  @Override
  public boolean unbreakable() {
    return false;
  }

  @Override
  public void markDirty() {

  }

  @Override
  public Optional<SerialItemData<ItemStack>> data() {
    return Optional.empty();
  }

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
  @Override
  public boolean similar(AbstractItemStack<? extends ItemStack> compare) {
    return false;
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    return null;
  }

  @Override
  public <V> boolean listsEquals(List<V> list1, List<V> list2) {
    return AbstractItemStack.super.listsEquals(list1, list2);
  }

  @Override
  public <V> boolean setsEquals(Set<V> list1, Set<V> list2) {
    return AbstractItemStack.super.setsEquals(list1, list2);
  }

  @Override
  public AbstractItemStack<ItemStack> of(JSONObject json) {
    return null;
  }
}
