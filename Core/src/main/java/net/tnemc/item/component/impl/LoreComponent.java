package net.tnemc.item.component.impl;
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
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import net.tnemc.item.providers.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * LoreComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#lore">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class LoreComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<Component> lore = new ArrayList<>();

  public LoreComponent() {

  }

  public LoreComponent(final List<Component> lore) {

    this.lore.addAll(lore);
  }

  public LoreComponent(final Component... lore) {

    this.lore.addAll(Arrays.asList(lore));
  }

  @Override
  public String identifier() {

    return "lore";
  }

  /**
   * Checks if the object is empty.
   *
   * @return True if the object is empty, false otherwise.
   */
  @Override
  public boolean empty() {

    return lore.isEmpty();
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    final JSONArray loreArray = new JSONArray();

    for(final Component component : lore) {
      loreArray.add(LegacyComponentSerializer.legacySection().serialize(component));
    }

    json.put("lore", loreArray);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    lore.clear();

    for(final String str : json.getStringList("lore")) {
      lore.add(LegacyComponentSerializer.legacySection().deserialize(str));
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    //System.out.println("Lore equals");

    if(!(component instanceof final LoreComponent<?, ?> other)) {
      //System.out.println("incompatible component");
      return false;
    }

    return Util.textComponentsEqual(this.lore, other.lore);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), lore);
  }

  public List<Component> lore() {

    return lore;
  }

  public void lore(final List<Component> lore) {

    this.lore.clear();
    this.lore.addAll(lore);
  }

  public void lore(final Component... lore) {

    this.lore.clear();
    this.lore.addAll(Arrays.asList(lore));
  }

  @Override
  public String toString() {

    return "LoreComponent{" +
           "lore=" + lore +
           '}';
  }
}