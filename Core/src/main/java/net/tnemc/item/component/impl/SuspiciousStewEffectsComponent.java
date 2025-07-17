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
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * SuspiciousStewEffectsComponent - The effects applied when consuming this suspicious stew.
 *
 * @author creatorfromhell
 * @see <a
 * href="https://minecraft.wiki/w/Data_component_format#suspicious_stew_effects">Reference</a>
 * @see <a href="https://minecraft.wiki/w/Suspicious_Stew">Suspicious_Stew</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class SuspiciousStewEffectsComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<EffectInstance> effects = new ArrayList<>();

  public SuspiciousStewEffectsComponent() {

  }

  public SuspiciousStewEffectsComponent(final List<EffectInstance> effects) {

    this.effects.addAll(effects);
  }

  public SuspiciousStewEffectsComponent(final EffectInstance... effects) {

    this.effects.addAll(Arrays.asList(effects));
  }

  @Override
  public String identifier() {

    return "suspicious_stew_effects";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    final JSONArray effectsArray = new JSONArray();
    for(final EffectInstance effect : effects) {
      effectsArray.add(effect.toJSON());
    }
    json.put("custom_effects", effectsArray);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    effects.clear();
    final JSONArray effectsArray = (JSONArray)json.getObject().get("custom_effects");
    for(final Object obj : effectsArray) {

      final EffectInstance effect = new EffectInstance();
      effect.readJSON(new JSONHelper((JSONObject)obj));
      effects.add(effect);
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final SuspiciousStewEffectsComponent<?, ?> other)) return false;
    return Objects.equals(this.effects, other.effects);
  }

  @Override
  public int hashCode() {

    return Objects.hash(effects);
  }

  public List<EffectInstance> effects() {

    return effects;
  }

  public void effects(final List<EffectInstance> effects) {

    this.effects.clear();
    this.effects.addAll(effects);
  }

  public void effects(final EffectInstance... effects) {

    this.effects.clear();
    this.effects.addAll(Arrays.asList(effects));
  }
}