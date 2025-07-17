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
import net.tnemc.item.component.helper.effect.ComponentEffect;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * DeathProtectionComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#death_protection">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class DeathProtectionComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<ComponentEffect> deathEffects = new ArrayList<>();

  @Override
  public String identifier() {

    return "death_protection";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    final JSONArray effectsArray = new JSONArray();
    for(final ComponentEffect effect : deathEffects) {
      effectsArray.add(effect.toJSON());
    }
    json.put("death_effects", effectsArray);

    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    deathEffects.clear();

    if(json.has("death_effects")) {
      final JSONArray effectsArray = (JSONArray)json.getObject().get("death_effects");

      for(final Object obj : effectsArray) {
        final JSONObject effectJson = (JSONObject)obj;
        final String type = effectJson.get("type").toString();

        // Get the effect class from the platform's reviveEffects map
        final Class<? extends ComponentEffect> effectClass = platform.effects().get(type);

        if(effectClass != null) {
          try {
            // Instantiate the effect dynamically
            final ComponentEffect effect = effectClass.getDeclaredConstructor().newInstance();
            effect.readJSON(new JSONHelper(effectJson));
            deathEffects.add(effect);
          } catch(final ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate ComponentEffect for type: " + type, e);
          }
        }
      }
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final DeathProtectionComponent<?, ?> other)) return false;

    return Objects.equals(this.deathEffects, other.deathEffects);
  }

  @Override
  public int hashCode() {

    return Objects.hash(deathEffects);
  }

  /**
   * Gets the list of death effects.
   *
   * @return A list of `ReviveEffect` objects.
   *
   * @since 0.2.0.0
   */
  public List<ComponentEffect> deathEffects() {

    return deathEffects;
  }

  /**
   * Applies death effects to the component's list of effects.
   *
   * @param deathEffects The list of ComponentEffect to apply as death effects.
   *
   * @since 0.2.0.0
   */
  public void deathEffects(final List<ComponentEffect> deathEffects) {

    this.deathEffects.clear();
    this.deathEffects.addAll(deathEffects);
  }

  /**
   * Adds one or more ComponentEffect objects to the list of death effects for this
   * DeathProtectionComponent.
   *
   * @param effects One or more ComponentEffect objects to be added as death effects.
   *
   * @since 0.2.0.0
   */
  public void deathEffect(final ComponentEffect... effects) {

    this.deathEffects.addAll(Arrays.asList(effects));
  }
}