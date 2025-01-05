package net.tnemc.item.component.impl;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.component.helper.BlockPredicate;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CanBreakComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#can_break">Reference</a>
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class CanBreakComponent<T> implements SerialComponent<T> {

  protected final List<BlockPredicate> predicates = new ArrayList<>();

  @Override
  public String getType() {
    return "can_break";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();

    final JSONArray predicatesArray = new JSONArray();
    for(final BlockPredicate predicate : predicates) {
      predicatesArray.add(predicate.toJSON());
    }
    json.put("predicates", predicatesArray);

    return json;
  }

  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    predicates.clear();

    final JSONArray predicatesArray = (JSONArray) json.getObject().get("predicates");
    if(predicatesArray != null) {
      for(final Object obj : predicatesArray) {
        final JSONObject predicateJson = (JSONObject) obj;
        final BlockPredicate predicate = new BlockPredicate();
        predicate.readJSON(new JSONHelper(predicateJson));
        predicates.add(predicate);
      }
    }
  }

  @Override
  public boolean equals(final SerialComponent<? extends T> component) {
    if(!(component instanceof final CanBreakComponent<?> other)) return false;

    return Objects.equals(this.predicates, other.predicates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(predicates);
  }
}
