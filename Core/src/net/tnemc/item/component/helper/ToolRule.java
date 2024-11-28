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
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ToolRule
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class ToolRule {

  protected final List<String> materials = new ArrayList<>();

  protected float speed;

  protected boolean drops;

  public ToolRule() {

  }

  public ToolRule(final float speed, final boolean drops) {

    this.speed = speed;
    this.drops = drops;
  }

  public JSONObject toJSON() {

    final JSONObject rule = new JSONObject();
    rule.put("speed", speed);
    rule.put("drops", drops);

    if(!materials.isEmpty()) {
      final JSONObject materialsObj = new JSONObject();
      for(int i = 0; i < materials.size(); i++) {
        materialsObj.put(i, materials.get(i));
      }
      rule.put("materials", materialsObj);
    }
    return rule;
  }

  public static ToolRule readJSON(final JSONHelper json) {

    final ToolRule rule = new ToolRule();

    if(json.has("speed")) {
      rule.setSpeed(json.getFloat("speed"));
    }

    if(json.has("drops")) {
      rule.setDrops(json.getBoolean("drops"));
    }

    final List<String> materials = new ArrayList<>();
    if(json.has("materials")) {
      final JSONObject materialsObj = json.getJSON("materials");
      materialsObj.forEach((ignore, value)->materials.add(String.valueOf(value)));
    }
    rule.setMaterials(materials);

    return rule;
  }

  public List<String> getMaterials() {

    return materials;
  }

  public void addMaterials(final List<String> materials) {

    this.materials.addAll(materials);
  }

  public void setMaterials(final List<String> materials) {

    this.materials.clear();
    this.materials.addAll(materials);
  }

  public float getSpeed() {

    return speed;
  }

  public void setSpeed(final float speed) {

    this.speed = speed;
  }

  public boolean isDrops() {

    return drops;
  }

  public void setDrops(final boolean drops) {

    this.drops = drops;
  }
}