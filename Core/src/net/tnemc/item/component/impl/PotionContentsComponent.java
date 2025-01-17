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
import net.tnemc.item.component.helper.revive.EffectInstance;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * PotionContentsComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class PotionContentsComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<EffectInstance> customEffects = new ArrayList<>();

  protected String potionId;
  protected int customColor;
  protected String customName;

  public PotionContentsComponent() {

  }

  public PotionContentsComponent(final String potionId) {

    this.potionId = potionId;
  }

  public PotionContentsComponent(final String potionId, final int customColor) {

    this.potionId = potionId;
    this.customColor = customColor;
  }

  public PotionContentsComponent(final String potionId, final int customColor, final String customName) {

    this.potionId = potionId;
    this.customColor = customColor;
    this.customName = customName;
  }

  public PotionContentsComponent(final String potionId, final int customColor, final String customName, final List<EffectInstance> effects) {

    this.potionId = potionId;
    this.customColor = customColor;
    this.customName = customName;

    this.customEffects.addAll(effects);
  }

  public PotionContentsComponent(final String potionId, final int customColor, final String customName, final EffectInstance... effects) {

    this.potionId = potionId;
    this.customColor = customColor;
    this.customName = customName;

    this.customEffects.addAll(Arrays.asList(effects));
  }

  @Override
  public String identifier() {
    return "potion_contents";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("potion", potionId);
    json.put("custom_color", customColor);
    json.put("custom_name", customName);

    final JSONArray effectsArray = new JSONArray();
    for (final EffectInstance effect : customEffects) {
      effectsArray.add(effect.toJSON());
    }
    json.put("custom_effects", effectsArray);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    potionId = json.getString("potion");
    customColor = json.getInteger("custom_color");
    customName = json.getString("custom_name");

    customEffects.clear();
    final JSONArray effectsArray = (JSONArray) json.getObject().get("custom_effects");
    for (final Object obj : effectsArray) {
      final EffectInstance effect = new EffectInstance();
      effect.readJSON(new JSONHelper((JSONObject) obj));
      customEffects.add(effect);
    }
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final PotionContentsComponent<?, ?> other)) return false;
    return Objects.equals(this.potionId, other.potionId) &&
           this.customColor == other.customColor &&
           Objects.equals(this.customName, other.customName) &&
           Objects.equals(this.customEffects, other.customEffects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(potionId, customColor, customName, customEffects);
  }

  public String potionId() {

    return potionId;
  }

  public void potionId(final String potionId) {

    this.potionId = potionId;
  }

  public int customColor() {

    return customColor;
  }

  public void customColor(final int customColor) {

    this.customColor = customColor;
  }

  public String customName() {

    return customName;
  }

  public void customName(final String customName) {

    this.customName = customName;
  }

  public List<EffectInstance> customEffects() {

    return customEffects;
  }

  public void customEffects(final List<EffectInstance> effects) {

    this.customEffects.clear();
    this.customEffects.addAll(effects);
  }

  public void customEffects(final EffectInstance... effects) {

    this.customEffects.clear();
    this.customEffects.addAll(Arrays.asList(effects));
  }
}