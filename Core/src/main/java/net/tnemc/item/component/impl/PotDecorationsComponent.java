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
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

/**
 * PotDecorationsComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#pot_decorations">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class PotDecorationsComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final String[] decorations = new String[4];

  public PotDecorationsComponent() {

  }

  public PotDecorationsComponent(final String... decorations) {

    decorations(decorations);
  }

  @Override
  public String identifier() {

    return "pot_decorations";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    final JSONArray array = new JSONArray();

    for(final String decoration : decorations) {
      array.add(decoration);
    }

    json.put("decorations", array);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    Arrays.fill(decorations, null);

    final var list = json.getStringList("decorations");
    for(int i = 0; i < Math.min(4, list.size()); i++) {
      decorations[i] = list.get(i);
    }
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final PotDecorationsComponent<?, ?> other)) return false;
    return Arrays.equals(this.decorations, other.decorations);
  }

  @Override
  public int hashCode() {

    return Arrays.hashCode(decorations);
  }

  public String[] decorations() {

    return decorations.clone();
  }

  public void decorations(final String... decorations) {

    Arrays.fill(this.decorations, null);

    System.arraycopy(decorations, 0, this.decorations, 0, Math.min(4, decorations.length));
  }

  public String west() {
    return decoration(0);
  }

  public String north() {
    return decoration(1);
  }

  public String east() {
    return decoration(2);
  }

  public String south() {
    return decoration(3);
  }

  public String decoration(final int side) {

    return decorations[side];
  }

  public void west(final String decoration) {
    decoration(0, decoration);
  }

  public void north(final String decoration) {
    decoration(1, decoration);
  }

  public void east(final String decoration) {
    decoration(2, decoration);
  }

  public void south(final String decoration) {
    decoration(3, decoration);
  }

  public void decoration(final int side, final String decoration) {

    if(side < 0 || side >= 4) {
      throw new IndexOutOfBoundsException("Pot decoration side must be between 0 and 3.");
    }

    decorations[side] = decoration;
  }
}