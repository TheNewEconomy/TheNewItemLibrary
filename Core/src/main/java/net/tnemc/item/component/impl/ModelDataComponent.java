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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ModelDataComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#custom_model_data">Minecraft Reference</a>
 * <p>
 * Note: Paper API v 1.21.4
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class ModelDataComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<String> colours = new ArrayList<>();
  protected final List<Float> floats = new ArrayList<>();
  protected final List<Boolean> flags = new ArrayList<>();
  protected final List<String> strings = new ArrayList<>();

  public ModelDataComponent() {

  }

  public ModelDataComponent(final List<String> colours, final List<Float> floats, final List<Boolean> flags, final List<String> strings) {

    this.colours.addAll(colours);
    this.floats.addAll(floats);
    this.flags.addAll(flags);
    this.strings.addAll(strings);
  }

  /**
   * @return the type of component this is.
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {
    return "model-data";
  }

  /**
   * Converts this component's data to a JSON object.
   *
   * @return The JSONObject representing this component's data.
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {
    final JSONObject json = new JSONObject();
    json.put("colours", colours);
    json.put("floats", floats);
    json.put("flags", flags);
    json.put("strings", strings);
    return json;
  }

  /**
   * Reads JSON data and converts it back to this component's data.
   *
   * @param json      The JSONHelper instance of the json data.
   * @param platform  The ItemPlatform instance.
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {
    colours.clear();
    colours.addAll(json.getStringList("colours"));

    floats.clear();
    floats.addAll(json.getFloatList("floats"));

    flags.clear();
    flags.addAll(json.getBooleanList("flags"));

    strings.clear();
    strings.addAll(json.getStringList("strings"));
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data.
   *
   * @param component The component to compare.
   * @return True if similar, otherwise false.
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final SerialComponent<?, ?> component) {
    if(!(component instanceof final ModelDataComponent<?, ?> other)) return false;
    return Objects.equals(this.colours, other.colours) &&
           Objects.equals(this.floats, other.floats) &&
           Objects.equals(this.flags, other.flags) &&
           Objects.equals(this.strings, other.strings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(colours, floats, flags, strings);
  }

  public List<String> colours() {

    return colours;
  }

  public void colours(final List<String> colours) {

    this.colours.clear();
    this.colours.addAll(colours);
  }

  public void colours(final String... colours) {

    this.colours.clear();
    this.colours.addAll(Arrays.asList(colours));
  }

  public List<Float> floats() {

    return floats;
  }

  public void floats(final List<Float> floats) {

    this.floats.clear();
    this.floats.addAll(floats);
  }

  public void floats(final Float... floats) {

    this.floats.clear();
    this.floats.addAll(Arrays.asList(floats));
  }

  public List<Boolean> flags() {

    return flags;
  }

  public void flags(final List<Boolean> flags) {

    this.flags.clear();
    this.flags.addAll(flags);
  }

  public void flags(final Boolean... flags) {

    this.flags.clear();
    this.flags.addAll(Arrays.asList(flags));
  }

  public List<String> strings() {

    return strings;
  }

  public void strings(final List<String> strings) {

    this.strings.clear();
    this.strings.addAll(strings);
  }

  public void strings(final String... strings) {

    this.strings.clear();
    this.strings.addAll(Arrays.asList(strings));
  }
}