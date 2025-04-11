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
import net.tnemc.item.component.helper.EquipSlot;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * EquippableComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#equippable">Reference</a>
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class EquipComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> entities = new ArrayList<>();

  protected String cameraKey;
  protected String equipSound;
  protected String modelKey;
  protected EquipSlot slot;
  protected boolean damageOnHurt;
  protected boolean dispensable;
  protected boolean swappable;
  protected boolean equipOnInteract; //as of SnapShot 25w03a

  public EquipComponent(final String cameraKey) {

    this.cameraKey = cameraKey;
  }

  public EquipComponent(final String cameraKey, final String equipSound) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey,
                        final EquipSlot slot) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
    this.slot = slot;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey,
                        final EquipSlot slot, final boolean damageOnHurt) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
    this.slot = slot;
    this.damageOnHurt = damageOnHurt;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey,
                        final EquipSlot slot, final boolean damageOnHurt, final boolean dispensable) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
    this.slot = slot;
    this.damageOnHurt = damageOnHurt;
    this.dispensable = dispensable;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey,
                        final EquipSlot slot, final boolean damageOnHurt, final boolean dispensable,
                        final boolean swappable) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
    this.slot = slot;
    this.damageOnHurt = damageOnHurt;
    this.dispensable = dispensable;
    this.swappable = swappable;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey,
                        final EquipSlot slot, final boolean damageOnHurt, final boolean dispensable,
                        final boolean swappable, final boolean equipOnInteract) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
    this.slot = slot;
    this.damageOnHurt = damageOnHurt;
    this.dispensable = dispensable;
    this.swappable = swappable;
    this.equipOnInteract = equipOnInteract;
  }

  public EquipComponent(final String cameraKey, final String equipSound, final String modelKey,
                        final EquipSlot slot, final boolean damageOnHurt, final boolean dispensable,
                        final boolean swappable, final boolean equipOnInteract, final List<String> entities) {

    this.cameraKey = cameraKey;
    this.equipSound = equipSound;
    this.modelKey = modelKey;
    this.slot = slot;
    this.damageOnHurt = damageOnHurt;
    this.dispensable = dispensable;
    this.swappable = swappable;
    this.equipOnInteract = equipOnInteract;
    this.entities.addAll(entities);
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "equip";
  }

  /**
   * Converts the {@link SerialComponent} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialComponent}.
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject equip = new JSONObject();
    equip.put("name", "equip-component");
    equip.put("cameraKey", cameraKey);
    equip.put("equipSound", equipSound);
    equip.put("modelKey", modelKey);
    equip.put("slot", slot.name());
    equip.put("damageWithEntity", damageOnHurt);
    equip.put("dispensable", dispensable);
    equip.put("swappable", swappable);
    equip.put("equipOnInteract", equipOnInteract);

    if(!entities.isEmpty()) {

      final JSONObject entitiesOBJ = new JSONObject();
      for(int i = 0; i < entities.size(); i++) {
        entitiesOBJ.put(i, entities.get(i));
      }
      equip.put("entities", entitiesOBJ);
    }
    return equip;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialComponent} object.
   *
   * @param json The JSONHelper instance of the json data.
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    cameraKey = json.getString("cameraKey");
    equipSound = json.getString("equipSound");
    modelKey = json.getString("modelKey");
    slot = EquipSlot.valueOf(json.getString("slot"));
    damageOnHurt = json.getBoolean("damageWithEntity");
    dispensable = json.getBoolean("dispensable");
    swappable = json.getBoolean("swappable");
    equipOnInteract = json.getBoolean("equipOnInteract");

    entities.clear();
    entities.addAll(json.getStringList("entities"));
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   * @since 0.2.0.0
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {

    if(component instanceof final EquipComponent<?, ?> equipComponent) {

      return Objects.equals(this.cameraKey, equipComponent.cameraKey) &&
             Objects.equals(this.equipSound, equipComponent.equipSound) &&
             Objects.equals(this.modelKey, equipComponent.modelKey) &&
             Objects.equals(this.slot.name(), equipComponent.slot.name()) &&
             this.damageOnHurt == equipComponent.damageOnHurt &&
             this.dispensable == equipComponent.dispensable &&
             this.swappable == equipComponent.swappable &&
             this.equipOnInteract == equipComponent.equipOnInteract &&
             this.entities.equals(equipComponent.entities);

    }
    return false;
  }

  public List<String> entities() {

    return entities;
  }

  public void entities(final List<String> entities) {

    this.entities.clear();
    this.entities.addAll(entities);
  }

  public void entities(final String... entities) {

    this.entities.clear();
    this.entities.addAll(Arrays.asList(entities));
  }

  public String cameraKey() {

    return cameraKey;
  }

  public void cameraKey(final String cameraKey) {

    this.cameraKey = cameraKey;
  }

  public String equipSound() {

    return equipSound;
  }

  public void equipSound(final String equipSound) {

    this.equipSound = equipSound;
  }

  public String modelKey() {

    return modelKey;
  }

  public void modelKey(final String modelKey) {

    this.modelKey = modelKey;
  }

  public EquipSlot slot() {

    return slot;
  }

  public void slot(final EquipSlot slot) {

    this.slot = slot;
  }

  public boolean damageOnHurt() {

    return damageOnHurt;
  }

  public void damageOnHurt(final boolean damageOnHurt) {

    this.damageOnHurt = damageOnHurt;
  }

  public boolean dispensable() {

    return dispensable;
  }

  public void dispensable(final boolean dispensable) {

    this.dispensable = dispensable;
  }

  public boolean swappable() {

    return swappable;
  }

  public void swappable(final boolean swappable) {

    this.swappable = swappable;
  }

  public boolean equipOnInteract() {

    return equipOnInteract;
  }

  public void equipOnInteract(final boolean equipOnInteract) {

    this.equipOnInteract = equipOnInteract;
  }
}