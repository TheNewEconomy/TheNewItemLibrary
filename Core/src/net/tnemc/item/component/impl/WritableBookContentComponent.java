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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * WrittableBookContentComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#writable_book_content">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class WritableBookContentComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> pages = new ArrayList<>();

  public WritableBookContentComponent() {

  }

  public WritableBookContentComponent(final List<String> pages) {

    this.pages.addAll(pages);
  }

  public WritableBookContentComponent(final String... pages) {

    this.pages.addAll(Arrays.asList(pages));
  }

  @Override
  public String identifier() {
    return "writable_book_content";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    final JSONArray pagesArray = new JSONArray();
    pagesArray.addAll(pages);
    json.put("pages", pagesArray);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {
    pages.clear();
    pages.addAll(json.getStringList("pages"));
  }

  @Override
  public boolean equals(final SerialComponent<I, T> component) {
    if (!(component instanceof final WritableBookContentComponent<?, ?> other)) return false;
    return Objects.equals(this.pages, other.pages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pages);
  }

  public List<String> pages() {

    return pages;
  }

  public void pages(final List<String> pages) {

    this.pages.clear();
    this.pages.addAll(pages);
  }

  public void pages(final String... pages) {

    this.pages.clear();
    this.pages.addAll(Arrays.asList(pages));
  }
}