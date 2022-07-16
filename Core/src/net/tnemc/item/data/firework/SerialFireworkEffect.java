package net.tnemc.item.data.firework;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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

  public SerialFireworkEffect(String type, boolean trail, boolean flicker, boolean hasEffect) {
    this.type = type;
    this.trail = trail;
    this.flicker = flicker;
  }

  public JSONObject toJSON() {
    
    JSONObject json = new JSONObject();
    if(colors.size() > 0) {
      JSONObject colours = new JSONObject();
      for (int i = 0; i < colors.size(); i++) {
        colours.put(i, colors.get(i));
      }
      json.put("colours", colours);
    }

    if(fadeColors.size() > 0) {
      JSONObject fades = new JSONObject();
      for (int i = 0; i < fadeColors.size(); i++) {
        fades.put(i, fadeColors.get(i));
      }
      json.put("fades", fades);
    }
    JSONObject eff = new JSONObject();
    eff.put("type", type);
    eff.put("flicker", flicker);
    eff.put("trail", trail);
    json.put("effect", eff);

    return json;
  }
  
  public static SerialFireworkEffect readJSON(JSONHelper json) {
    final SerialFireworkEffect effect = new SerialFireworkEffect();

    if(json.has("effect")) {
      JSONHelper helper = json.getHelper("effect");
      effect.setType(helper.getString("type"));
      effect.setTrail(helper.getBoolean("trail"));
      effect.setFlicker(helper.getBoolean("flicker"));
    }

    List<Integer> colors = new ArrayList<>();
    if(json.has("colours")) {
      JSONObject colours = json.getJSON("colours");
      colours.forEach((ignore, value)->colors.add(Integer.valueOf(String.valueOf(value))));
    }
    effect.setColors(colors);

    List<Integer> fadeColors = new ArrayList<>();
    if(json.has("fades")) {
      JSONObject fades = json.getJSON("fades");
      fades.forEach((ignore, value)->fadeColors.add(Integer.valueOf(String.valueOf(value))));
    }
    effect.setFadeColors(fadeColors);
    return effect;
  }

  public List<Integer> getColors() {
    return colors;
  }

  public void setColors(List<Integer> colors) {
    this.colors.clear();
    this.colors.addAll(colors);
  }

  public List<Integer> getFadeColors() {
    return fadeColors;
  }

  public void setFadeColors(List<Integer> fadeColors) {
    this.fadeColors.clear();
    this.fadeColors.addAll(fadeColors);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean hasTrail() {
    return trail;
  }

  public void setTrail(boolean trail) {
    this.trail = trail;
  }

  public boolean hasFlicker() {
    return flicker;
  }

  public void setFlicker(boolean flicker) {
    this.flicker = flicker;
  }
}