package net.tnemc.item.component.impl;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * ContainerLootComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#container_loot">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ContainerLootComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected String lootTable = "";
  protected long seed = 0L;

  public ContainerLootComponent() {

  }

  public ContainerLootComponent(final String lootTable, final long seed) {
    this.lootTable = lootTable;
    this.seed = seed;
  }

  @Override
  public String identifier() {

    return "container_loot";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("lootTable", lootTable);
    json.put("seed", seed);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    lootTable(json.getString("lootTable"));
    seed(json.getLong("seed"));
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final ContainerLootComponent<?, ?> other)) return false;
    return Objects.equals(this.lootTable, other.lootTable) && Objects.equals(this.seed, other.seed);
  }

  @Override
  public int hashCode() {

    return Objects.hash(lootTable, seed);
  }

  public String lootTable() {

    return lootTable;
  }

  public void lootTable(final String lootTable) {

    this.lootTable = lootTable;
  }

  public long seed() {

    return seed;
  }

  public void seed(final long seed) {

    this.seed = seed;
  }
}
