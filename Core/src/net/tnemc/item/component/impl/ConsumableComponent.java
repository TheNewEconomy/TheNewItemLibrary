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
import net.tnemc.item.component.helper.revive.ComponentEffect;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ConsumableComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class ConsumableComponent<T> implements SerialComponent<T> {

  protected float consumeSeconds = 1.6f; // Default to 1.6 seconds
  protected String animation = "eat"; // Default animation
  protected String sound = "entity.generic.eat"; // Default sound
  protected boolean hasConsumeParticles = true; // Default true
  protected final List<ComponentEffect> onConsumeEffects = new ArrayList<>();

  @Override
  public String getType() {
    return "consumable";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("consume_seconds", consumeSeconds);
    json.put("animation", animation);
    json.put("sound", sound);
    json.put("has_consume_particles", hasConsumeParticles);

    final JSONArray effectsArray = new JSONArray();
    for (final ComponentEffect effect : onConsumeEffects) {
      effectsArray.add(effect.toJSON());
    }
    json.put("on_consume_effects", effectsArray);

    return json;
  }

  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    consumeSeconds = json.getFloat("consume_seconds");
    animation = json.getString("animation");
    sound = json.getString("sound");
    hasConsumeParticles = json.getBoolean("has_consume_particles");

    onConsumeEffects.clear();
    if (json.has("on_consume_effects")) {

      final JSONArray effectsArray = (JSONArray) json.getObject().get("on_consume_effects");
      for (final Object obj : effectsArray) {
        final JSONObject effectJson = (JSONObject) obj;
        final String type = effectJson.get("type").toString();

        // Get the effect class from the platform's reviveEffects map
        final Class<? extends ComponentEffect> effectClass = platform.effects().get(type);

        if (effectClass != null) {
          try {
            // Instantiate the effect dynamically
            final ComponentEffect effect = effectClass.getDeclaredConstructor().newInstance();
            effect.readJSON(new JSONHelper(effectJson));
            onConsumeEffects.add(effect);
          } catch (final ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate ComponentEffect for type: " + type, e);
          }
        }
      }
    }
  }

  @Override
  public boolean equals(final SerialComponent<? extends T> component) {
    if (!(component instanceof final ConsumableComponent<?> other)) return false;
    return Float.compare(this.consumeSeconds, other.consumeSeconds) == 0 &&
           Objects.equals(this.animation, other.animation) &&
           Objects.equals(this.sound, other.sound) &&
           this.hasConsumeParticles == other.hasConsumeParticles &&
           Objects.equals(this.onConsumeEffects, other.onConsumeEffects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consumeSeconds, animation, sound, hasConsumeParticles, onConsumeEffects);
  }
}