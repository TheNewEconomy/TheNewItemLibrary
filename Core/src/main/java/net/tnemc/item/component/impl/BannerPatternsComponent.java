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
import net.tnemc.item.component.helper.PatternData;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * BannerComponent - List of all patterns applied to the banner or the shield.
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#banner_patterns">Reference</a>
 * @since 0.2.0.0
 */

public abstract class BannerPatternsComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<PatternData> patterns = new ArrayList<>();

  /**
   * Represents a component that handles banner patterns for an object.
   *
   * @since 0.2.0.0
   */
  public BannerPatternsComponent() {

  }

  /**
   * Creates a new BannerPatternsComponent with the provided list of PatternData objects.
   *
   * @param patterns The list of PatternData objects to initialize the component with.
   *
   * @since 0.2.0.0
   */
  public BannerPatternsComponent(final List<PatternData> patterns) {

    this.patterns.addAll(patterns);
  }

  /**
   * @return the type of component this is.
   *
   * @since 0.2.0.0
   */
  @Override
  public String identifier() {

    return "banner_patterns";
  }

  /**
   * Converts this component's data to a JSON object.
   *
   * @return The JSONObject representing this component's data.
   *
   * @since 0.2.0.0
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    final JSONArray patternsArray = new JSONArray();
    for(final PatternData pattern : patterns) {

      final JSONObject patternJson = new JSONObject();
      patternJson.put("color", pattern.getColor());
      patternJson.put("pattern", pattern.getPattern());
      patternsArray.add(patternJson);
    }
    json.put("patterns", patternsArray);

    return json;
  }

  /**
   * Reads JSON data and converts it back to this component's data.
   *
   * @param json     The JSONHelper instance of the JSON data.
   * @param platform The ItemPlatform instance.
   *
   * @since 0.2.0.0
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    patterns.clear();

    final JSONArray patternsArray = (JSONArray)json.getObject().get("patterns");
    if(patternsArray != null) {
      for(final Object obj : patternsArray) {

        final JSONObject patternJson = (JSONObject)obj;
        final String color = patternJson.get("color").toString();
        final String pattern = patternJson.get("pattern").toString();
        patterns.add(new PatternData(color, pattern));
      }
    }
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final BannerPatternsComponent<?, ?> other)) return false;

    return Objects.equals(this.patterns, other.patterns);
  }

  @Override
  public int hashCode() {

    return Objects.hash(patterns);
  }

  /**
   * Retrieve the list of PatternData objects associated with this BannerPatternsComponent.
   *
   * @return List of PatternData objects representing the patterns.
   *
   * @since 0.2.0.0
   */
  public List<PatternData> patterns() {

    return patterns;
  }

  /**
   * Sets the list of patterns for this object by replacing the existing patterns with the provided
   * list.
   *
   * @param patterns List of PatternData objects to set as new patterns
   *
   * @since 0.2.0.0
   */
  public void patterns(final List<PatternData> patterns) {

    this.patterns.clear();
    this.patterns.addAll(patterns);
  }

  public void patterns(final PatternData... patterns) {

    this.patterns.clear();
    this.patterns.addAll(Arrays.asList(patterns));
  }

  /**
   * Adds a pattern to the list of patterns for this BannerPatternsComponent.
   *
   * @param pattern The PatternData to add.
   *
   * @since 0.2.0.0
   */
  public void patterns(final PatternData pattern) {

    this.patterns.add(pattern);
  }
}
