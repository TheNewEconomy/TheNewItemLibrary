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
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * LodestoneTrackerComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#lodestone_tracker">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class LodestoneTrackerComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String target;
  protected int[] pos = new int[3]; // x, y, z coordinates
  protected String dimension;
  protected boolean tracked = true;

  public LodestoneTrackerComponent() {

  }

  public LodestoneTrackerComponent(final String target) {

    this.target = target;
  }

  public LodestoneTrackerComponent(final String target, final int[] pos) {

    this.target = target;
    this.pos = pos;
  }

  public LodestoneTrackerComponent(final String target, final int[] pos, final String dimension) {

    this.target = target;
    this.pos = pos;
    this.dimension = dimension;
  }

  public LodestoneTrackerComponent(final String target, final int[] pos, final String dimension, final boolean tracked) {

    this.target = target;
    this.pos = pos;
    this.dimension = dimension;
    this.tracked = tracked;
  }

  @Override
  public String identifier() {
    return "lodestone_tracker";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("target", target);
    json.put("pos", pos);
    json.put("dimension", dimension);
    json.put("tracked", tracked);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    if (json.has("target")) target = json.getString("target");
    if (json.has("pos")) pos = json.getIntArray("pos");
    if (json.has("dimension")) dimension = json.getString("dimension");
    if (json.has("tracked")) tracked = json.getBoolean("tracked");
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final LodestoneTrackerComponent<?, ?> other)) return false;
    return Objects.equals(this.target, other.target) &&
           Objects.equals(this.pos, other.pos) &&
           Objects.equals(this.dimension, other.dimension) &&
           this.tracked == other.tracked;
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, pos, dimension, tracked);
  }

  public String target() {

    return target;
  }

  public void target(final String target) {

    this.target = target;
  }

  public int[] pos() {

    return pos;
  }

  public void pos(final int[] pos) {

    this.pos = pos;
  }

  public String dimension() {

    return dimension;
  }

  public void dimension(final String dimension) {

    this.dimension = dimension;
  }

  public boolean tracked() {

    return tracked;
  }

  public void tracked(final boolean tracked) {

    this.tracked = tracked;
  }
}