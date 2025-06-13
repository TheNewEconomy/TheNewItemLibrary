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
 * RecipesComponent
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#recipes">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class RecipesComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> recipes = new ArrayList<>();

  public RecipesComponent() {

  }

  public RecipesComponent(final String recipe) {

    this.recipes.add(recipe);
  }

  public RecipesComponent(final List<String> recipes) {

    this.recipes.addAll(recipes);
  }

  @Override
  public String identifier() {
    return "recipes";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    final JSONArray recipesArray = new JSONArray();
    recipesArray.addAll(recipes);
    json.put("recipes", recipesArray);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    recipes.clear();
    recipes.addAll(json.getStringList("recipes"));
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {
    if(!(component instanceof final RecipesComponent<?, ?> other)) return false;
    return Objects.equals(this.recipes, other.recipes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipes);
  }

  public List<String> recipes() {

    return recipes;
  }

  public void recipes(final List<String> recipes) {

    this.recipes.addAll(recipes);
  }

  public void recipes(final String... recipes) {

    this.recipes.addAll(Arrays.asList(recipes));
  }
}