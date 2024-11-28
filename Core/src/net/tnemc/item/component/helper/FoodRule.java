package net.tnemc.item.component.helper;
/*
 * The New Item Library
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

import net.tnemc.item.JSONHelper;
import net.tnemc.item.data.potion.PotionEffectData;
import org.json.simple.JSONObject;

/**
 * FoodRule
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class FoodRule {

  protected PotionEffectData potionEffect;
  protected float chance;

  public FoodRule() {

  }

  public FoodRule(final PotionEffectData potionEffect, final float chance) {

    this.potionEffect = potionEffect;
    this.chance = chance;
  }

  public JSONObject toJSON() {

    final JSONObject rule = new JSONObject();
    rule.put("effect", potionEffect.toJSON());
    rule.put("chance", chance);

    return rule;
  }

  public static FoodRule readJSON(final JSONHelper json) {

    final FoodRule rule = new FoodRule();

    if(json.has("effect")) {
      rule.setPotionEffect(PotionEffectData.readJSON(json.getHelper("effect")));
    }

    if(json.has("chance")) {
      rule.setChance(json.getFloat("chance"));
    }

    return rule;
  }

  public PotionEffectData getPotionEffect() {

    return potionEffect;
  }

  public void setPotionEffect(final PotionEffectData potionEffect) {

    this.potionEffect = potionEffect;
  }

  public float getChance() {

    return chance;
  }

  public void setChance(final float chance) {

    this.chance = chance;
  }
}