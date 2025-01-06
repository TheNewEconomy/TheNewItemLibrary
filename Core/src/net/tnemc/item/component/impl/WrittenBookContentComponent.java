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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WrittenBookContentComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class WrittenBookContentComponent<T> implements SerialComponent<T> {

  protected final List<String> pages = new ArrayList<>();
  protected String title;
  protected String author;
  protected int generation;
  protected boolean resolved;

  @Override
  public String getType() {
    return "written_book_content";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    final JSONArray pagesArray = new JSONArray();
    pagesArray.addAll(pages);
    json.put("pages", pagesArray);
    json.put("title", title);
    json.put("author", author);
    json.put("generation", generation);
    json.put("resolved", resolved);
    return json;
  }

  @Override
  public <I extends AbstractItemStack<T>> void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    pages.clear();
    pages.addAll(json.getStringList("pages"));
    if (json.has("title")) title = json.getString("title");
    if (json.has("author")) author = json.getString("author");
    if (json.has("generation")) generation = json.getInteger("generation");
    if (json.has("resolved")) resolved = json.getBoolean("resolved");
  }

  @Override
  public boolean equals(final SerialComponent<? extends T> component) {
    if (!(component instanceof final WrittenBookContentComponent<?> other)) return false;
    return Objects.equals(this.pages, other.pages) &&
           Objects.equals(this.title, other.title) &&
           Objects.equals(this.author, other.author) &&
           this.generation == other.generation &&
           this.resolved == other.resolved;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pages, title, author, generation, resolved);
  }
}