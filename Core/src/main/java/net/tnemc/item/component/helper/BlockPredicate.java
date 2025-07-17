package net.tnemc.item.component.helper;
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

import net.tnemc.item.JSONHelper;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BlockPredicate
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */

public class BlockPredicate {

  protected final List<String> blocks = new ArrayList<>();
  protected final Map<String, String> state = new HashMap<>();

  /**
   * Converts this block predicate to a JSON object.
   *
   * @return The JSONObject representing this block predicate.
   *
   * @since 0.2.0.0
   */
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("blocks", blocks);

    final JSONObject stateJson = new JSONObject();
    for(final Map.Entry<String, String> entry : state.entrySet()) {
      stateJson.put(entry.getKey(), entry.getValue());
    }
    json.put("state", stateJson);

    return json;
  }

  /**
   * Reads JSON data and populates this block predicate.
   *
   * @param json The JSONHelper instance of the JSON data.
   *
   * @since 0.2.0.0
   */
  public void readJSON(final JSONHelper json) {

    blocks.clear();
    blocks.addAll(json.getStringList("blocks"));

    state.clear();
    final JSONObject stateJson = json.getJSON("state");
    if(stateJson != null) {
      for(final Object key : stateJson.keySet()) {
        state.put(key.toString(), stateJson.get(key).toString());
      }
    }
  }

  @Override
  public boolean equals(final Object obj) {

    if(!(obj instanceof final BlockPredicate other)) return false;

    return blocks.equals(other.blocks) && state.equals(other.state);
  }

  @Override
  public int hashCode() {

    return blocks.hashCode() + state.hashCode();
  }

  @Override
  public BlockPredicate clone() throws CloneNotSupportedException {

    final BlockPredicate copy = new BlockPredicate();
    copy.blocks.addAll(this.blocks);
    copy.state.putAll(this.state);
    return copy;
  }
}
