package net.tnemc.item.bukkit.data.potion;

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