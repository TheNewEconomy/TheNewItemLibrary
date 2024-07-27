package net.tnemc.item.data.potion;

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

public class PotionEffectData {

  private String name;
  private int amplifier;
  private int duration;

  private boolean particles;
  private boolean ambient;
  private boolean icon;

  public PotionEffectData(String name, int amplifier, int duration, boolean particles, boolean ambient,
                          boolean icon) {
    this.name = name;
    this.amplifier = amplifier;
    this.duration = duration;
    this.particles = particles;
    this.ambient = ambient;
    this.icon = icon;
  }

  public JSONObject toJSON() {
    final JSONObject effObject = new JSONObject();
    effObject.put("name", name);
    effObject.put("amplifier", amplifier);
    effObject.put("duration", duration);
    effObject.put("ambient", ambient);
    effObject.put("particles", particles);
    effObject.put("icon", icon);

    return effObject;
  }

  public static PotionEffectData readJSON(JSONHelper json) {
    return new PotionEffectData(json.getString("name"),
            json.getInteger("amplifier"),
            json.getInteger("duration"),
            json.getBoolean("ambient"),
            json.getBoolean("particles"),
            json.getBoolean("icon"));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAmplifier() {
    return amplifier;
  }

  public void setAmplifier(int amplifier) {
    this.amplifier = amplifier;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public boolean hasParticles() {
    return particles;
  }

  public void setParticles(boolean particles) {
    this.particles = particles;
  }

  public boolean isAmbient() {
    return ambient;
  }

  public void setAmbient(boolean ambient) {
    this.ambient = ambient;
  }

  public boolean hasIcon() {
    return icon;
  }

  public void setIcon(boolean icon) {
    this.icon = icon;
  }
}