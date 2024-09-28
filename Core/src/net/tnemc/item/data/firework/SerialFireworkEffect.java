package net.tnemc.item.data.firework;

/*
 * The New Item Library Minecraft Server Plugin
 *
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
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SerialFireworkEffect {

  private final List<Integer> colors = new ArrayList<>();
  private final List<Integer> fadeColors = new ArrayList<>();

  private String type = "";
  private boolean trail = false;
  private boolean flicker = false;

  public SerialFireworkEffect() { }

  public SerialFireworkEffect(final String type, final boolean trail, final boolean flicker) {

    this.type = type;
    this.trail = trail;
    this.flicker = flicker;
  }

  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    if(!colors.isEmpty()) {
      final JSONObject colours = new JSONObject();
      for(int i = 0; i < colors.size(); i++) {
        colours.put(i, colors.get(i));
      }
      json.put("colours", colours);
    }

    if(!fadeColors.isEmpty()) {
      final JSONObject fades = new JSONObject();
      for(int i = 0; i < fadeColors.size(); i++) {
        fades.put(i, fadeColors.get(i));
      }
      json.put("fades", fades);
    }

    final JSONObject eff = new JSONObject();
    eff.put("type", type);
    eff.put("flicker", flicker);
    eff.put("trail", trail);
    json.put("effect", eff);

    return json;
  }

  public static SerialFireworkEffect readJSON(final JSONHelper json) {

    final SerialFireworkEffect effect = new SerialFireworkEffect();

    if(json.has("effect")) {
      final JSONHelper helper = json.getHelper("effect");
      effect.setType(helper.getString("type"));
      effect.setTrail(helper.getBoolean("trail"));
      effect.setFlicker(helper.getBoolean("flicker"));
    }

    final List<Integer> colors = new ArrayList<>();
    if(json.has("colours")) {
      final JSONObject colours = json.getJSON("colours");
      colours.forEach((ignore, value)->colors.add(Integer.valueOf(String.valueOf(value))));
    }
    effect.setColors(colors);

    final List<Integer> fadeColors = new ArrayList<>();
    if(json.has("fades")) {
      final JSONObject fades = json.getJSON("fades");
      fades.forEach((ignore, value)->fadeColors.add(Integer.valueOf(String.valueOf(value))));
    }
    effect.setFadeColors(fadeColors);
    return effect;
  }

  public List<Integer> getColors() {

    return colors;
  }

  public void setColors(final List<Integer> colors) {

    this.colors.clear();
    this.colors.addAll(colors);
  }

  public List<Integer> getFadeColors() {

    return fadeColors;
  }

  public void setFadeColors(final List<Integer> fadeColors) {

    this.fadeColors.clear();
    this.fadeColors.addAll(fadeColors);
  }

  public String getType() {

    return type;
  }

  public void setType(final String type) {

    this.type = type;
  }

  public boolean hasTrail() {

    return trail;
  }

  public void setTrail(final boolean trail) {

    this.trail = trail;
  }

  public boolean hasFlicker() {

    return flicker;
  }

  public void setFlicker(final boolean flicker) {

    this.flicker = flicker;
  }
}