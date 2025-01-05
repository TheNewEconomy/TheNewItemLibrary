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

import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * EffectInstance
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class EffectInstance {

  protected String id;
  protected int amplifier = 0;
  protected int duration = 1;
  protected boolean ambient = false;
  protected boolean showParticles = true;
  protected boolean showIcon = true;

  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("amplifier", amplifier);
    json.put("duration", duration);
    json.put("ambient", ambient);
    json.put("show_particles", showParticles);
    json.put("show_icon", showIcon);
    return json;
  }

  public void readJSON(final JSONObject json) {
    id = json.getString("id");
    amplifier = json.getInteger("amplifier");
    duration = json.getInteger("duration");
    ambient = json.getBoolean("ambient");
    showParticles = json.getBoolean("show_particles");
    showIcon = json.getBoolean("show_icon");
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof final EffectInstance other)) return false;

    return Objects.equals(this.id, other.id) &&
           this.amplifier == other.amplifier &&
           this.duration == other.duration &&
           this.ambient == other.ambient &&
           this.showParticles == other.showParticles &&
           this.showIcon == other.showIcon;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amplifier, duration, ambient, showParticles, showIcon);
  }
}