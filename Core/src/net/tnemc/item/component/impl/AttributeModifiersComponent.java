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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.TooltippableSerialComponent;
import net.tnemc.item.component.helper.AttributeModifier;
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * AttributeModifiesComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#attribute_modifiers">Reference</a>
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class AttributeModifiersComponent<I extends AbstractItemStack<T>, T> extends TooltippableSerialComponent<I, T> {

  protected final List<AttributeModifier> modifiers = new ArrayList<>();

  /**
   * Constructor for AttributeModifiersComponent.
   * Initializes an empty list of AttributeModifiers.
   */
  public AttributeModifiersComponent() {
  }

  /**
   * Initializes an AttributeModifiersComponent with the specified showInTooltip flag.
   *
   * @param showInTooltip A boolean flag indicating whether to show the component in tooltip.
   */
  public AttributeModifiersComponent(final boolean showInTooltip) {

    this.showInTooltip = showInTooltip;
  }

  /**
   * Constructor for AttributeModifiersComponent.
   * Initializes the component with a list of AttributeModifiers and a boolean flag to show in tooltip.
   *
   * @param modifiers The list of AttributeModifiers to associate with this component.
   * @param showInTooltip A boolean flag indicating whether to show the component in tooltip.
   */
  public AttributeModifiersComponent(final List<AttributeModifier> modifiers,
                                     final boolean showInTooltip) {

    this.modifiers.addAll(modifiers);
    this.showInTooltip = showInTooltip;
  }

  /**
   * @return the type of component this is.
   */
  @Override
  public String identifier() {
    return "attribute_modifiers";
  }

  /**
   * Converts this component's data to a JSON object.
   *
   * @return The JSONObject representing this component's data.
   */
  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("showInTooltip", showInTooltip);

    final JSONArray modifiersArray = new JSONArray();
    for(final AttributeModifier modifier : modifiers) {

      final JSONObject modifierJson = new JSONObject();
      modifierJson.put("type", modifier.getType());
      modifierJson.put("slot", modifier.getSlot().name());
      modifierJson.put("id", modifier.getId());
      modifierJson.put("amount", modifier.getAmount());
      modifierJson.put("operation", modifier.getOperation());
      modifiersArray.add(modifierJson);
    }
    json.put("modifiers", modifiersArray);

    return json;
  }

  /**
   * Reads JSON data and converts it back to this component's data.
   *
   * @param json The JSONHelper instance of the JSON data.
   * @param platform The ItemPlatform instance.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    super.readJSON(json, platform);

    modifiers.clear();

    final JSONArray modifiersArray = (JSONArray) json.getObject().get("modifiers");
    if(modifiersArray != null) {

      for(final Object obj : modifiersArray) {

        final JSONObject modifierJson = (JSONObject) obj;

        final String type = modifierJson.get("type").toString();
        final String id = modifierJson.get("id").toString();
        final String operation = modifierJson.get("operation").toString();

        final AttributeModifier modifier = new AttributeModifier(type, id, operation);

        if(modifierJson.containsKey("slot")) {
          modifier.setSlot(EquipSlot.valueOf(modifierJson.get("slot").toString()));
        }

        if(modifierJson.containsKey("amount")) {
          modifier.setAmount(Double.parseDouble(modifierJson.get("amount").toString()));
        }

        modifiers.add(modifier);
      }
    }
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data.
   *
   * @param component The component to compare.
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if(!(component instanceof final AttributeModifiersComponent<?, ?> other)) return false;
    return this.showInTooltip == other.showInTooltip &&
           Objects.equals(this.modifiers, other.modifiers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modifiers, showInTooltip);
  }

  /**
   * Retrieves the list of AttributeModifiers associated with this component.
   *
   * @return The list of AttributeModifiers.
   */
  public List<AttributeModifier> modifiers() {

    return modifiers;
  }

  /**
   * Sets the list of AttributeModifiers for this component.
   *
   * @param modifiers The list of AttributeModifiers to set.
   */
  public void modifiers(final List<AttributeModifier> modifiers) {
    this.modifiers.clear();
    this.modifiers.addAll(modifiers);
  }

  public void modifiers(final AttributeModifier... modifiers) {
    this.modifiers.clear();
    this.modifiers.addAll(Arrays.asList(modifiers));
  }

  /**
   * Adds a new AttributeModifier to the list of modifiers for this component.
   *
   * @param modifier The AttributeModifier to add to the list of modifiers.
   */
  public void modifiers(final AttributeModifier modifier) {
    this.modifiers.add(modifier);
  }
}