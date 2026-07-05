package net.tnemc.item.component.helper;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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

/**
 * KineticWeaponCondition
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class KineticWeaponCondition {

  protected int maxDurationTicks = 0;
  protected float minSpeed = 0.0f;
  protected float minRelativeSpeed = 0.0f;

  public KineticWeaponCondition() {

  }

  public KineticWeaponCondition(final int maxDurationTicks,
                                final float minSpeed,
                                final float minRelativeSpeed) {

    this.maxDurationTicks = maxDurationTicks;
    this.minSpeed = minSpeed;
    this.minRelativeSpeed = minRelativeSpeed;
  }

  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    json.put("maxDurationTicks", maxDurationTicks);
    json.put("minSpeed", minSpeed);
    json.put("minRelativeSpeed", minRelativeSpeed);

    return json;
  }

  public void readJSON(final JSONObject json) {

    if(json.containsKey("maxDurationTicks")) {
      maxDurationTicks = Integer.parseInt(json.get("maxDurationTicks").toString());
    }

    if(json.containsKey("minSpeed")) {
      minSpeed = Float.parseFloat(json.get("minSpeed").toString());
    }

    if(json.containsKey("minRelativeSpeed")) {
      minRelativeSpeed = Float.parseFloat(json.get("minRelativeSpeed").toString());
    }
  }
}