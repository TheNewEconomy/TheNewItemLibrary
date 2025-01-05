package net.tnemc.item.component.helper.revive;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ApplyEffectsReviveEffect
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class ApplyEffectsReviveEffect extends ReviveEffect {

  private final List<EffectInstance> effects = new ArrayList<>();

  @Override
  public String getType() {
    return "apply_effects";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("type", getType());
    json.put("probability", probability);

    final JSONArray effectsArray = new JSONArray();
    for (final EffectInstance effect : effects) {
      effectsArray.add(effect.toJSON());
    }
    json.put("effects", effectsArray);

    return json;
  }

  @Override
  public void readJSON(final JSONObject json) {
    probability = Float.parseFloat(json.get("probability").toString());

    effects.clear();
    final JSONArray effectsArray = (JSONArray) json.get("effects");
    if (effectsArray != null) {
      for (final Object obj : effectsArray) {
        final EffectInstance effect = new EffectInstance();
        effect.readJSON((JSONObject) obj);
        effects.add(effect);
      }
    }
  }

  public List<EffectInstance> getEffects() {
    return effects;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof final ApplyEffectsReviveEffect other)) return false;

    return super.equals(obj) && Objects.equals(this.effects, other.effects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), effects);
  }
}
