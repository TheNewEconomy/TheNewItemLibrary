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
import net.tnemc.item.component.helper.DamageReduction;
import net.tnemc.item.component.helper.ItemDamage;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * BlocksAttacksComponent - When present, this item can be used like a Shield to block attacks to the
 * holding player. Added in MC 1.21.5
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#blocks_attacks">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class BlocksAttacksComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected final List<DamageReduction> reductions = new ArrayList<>();

  protected final List<String> bypassedBy = new ArrayList<>();

  protected ItemDamage itemDamage = null;

  //The number of seconds that right-click must be held before successfully blocking attacks
  protected float blockDelay = 0;

  //Multiplier applied to the number of seconds that the item will be on cooldown for when attacked
  //by a disabling attack (disable_blocking_for_seconds on the weapon component)
  //If 0, this item can never be disabled by attacks
  protected float disableCooldownScale = 1;

  protected String blockSound = "";

  protected String disableSound = "";

  public BlocksAttacksComponent() {

  }

  /**
   * @return the type of component this is.
   */
  @Override
  public String identifier() {

    return "blocks_attacks";
  }

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();

    //Serialize DamageReductions
    final JSONArray reductionsArray = new JSONArray();
    for(final DamageReduction reduction : reductions) {

      final JSONObject reductionJson = new JSONObject();
      reductionJson.put("type", reduction.type());
      reductionJson.put("base", reduction.base());
      reductionJson.put("factor", reduction.factor());
      reductionJson.put("horizontalBlockingAngle", reduction.horizontalBlockingAngle());
      reductionsArray.add(reductionJson);
    }
    json.put("reductions", reductionsArray);


    //Serialize bypassedBy
    final JSONArray bypassedByArray = new JSONArray();
    for(final String bypassedBy : bypassedBy) {

      bypassedByArray.add(bypassedBy);
    }
    json.put("bypassedBy", bypassedByArray);

    // Serialize ItemDamage
    if(itemDamage != null) {

      final JSONObject itemDamageJson = new JSONObject();

      itemDamageJson.put("threshold", itemDamage.threshold());
      itemDamageJson.put("base", itemDamage.base());
      itemDamageJson.put("factor", itemDamage.factor());
      json.put("item_damage", itemDamageJson);
    }

    // Serialize other properties
    json.put("block_delay", blockDelay);
    json.put("disable_cooldown_scale", disableCooldownScale);
    json.put("block_sound", blockSound);
    json.put("disable_sound", disableSound);

    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T> platform) {

    //Deserialize DamageReductions
    final JSONArray reductionsArray = (JSONArray)json.getObject().get("reductions");
    reductions.clear();

    if(reductionsArray != null) {

      for(final Object obj : reductionsArray) {

        final JSONObject reductionJson = (JSONObject)obj;
        final String type = reductionJson.get("type").toString();
        final float base = Float.parseFloat(reductionJson.get("base").toString());
        final float factor = Float.parseFloat(reductionJson.get("factor").toString());
        final float horizontalBlockingAngle = Float.parseFloat(reductionJson.get("horizontalBlockingAngle").toString());
        reductions.add(new DamageReduction(type, base, factor, horizontalBlockingAngle));
      }
    }

    //Deserialize bypassBy
    final JSONArray bypassByArray = (JSONArray)json.getObject().get("bypassedBy");
    bypassedBy.clear();

    if(bypassByArray != null) {

      for(final Object obj : bypassByArray) {

        bypassedBy.add(obj.toString());
      }
    }

    //Deserialize ItemDamage
    final JSONObject itemDamageJson = (JSONObject) json.getObject().get("item_damage");
    if(itemDamageJson != null) {

      final float threshold = Float.parseFloat(itemDamageJson.get("threshold").toString());
      final float base = Float.parseFloat(itemDamageJson.get("base").toString());
      final float factor = Float.parseFloat(itemDamageJson.get("factor").toString());
      itemDamage = new ItemDamage(threshold, base, factor);
    }

    //Deserialize other properties
    blockDelay = json.getFloat("block_delay");
    disableCooldownScale = json.getFloat("disable_cooldown_scale");
    blockSound = json.getString("block_sound");
    disableSound = json.getString("disable_sound");
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact
   * copy of this data. For instance, book copies will return false when compared to the original.
   *
   * @param component The component to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(final SerialComponent<I, T> component) {

    if(!(component instanceof final BlocksAttacksComponent<?, ?> other)) return false;

    return Objects.equals(this.reductions, other.reductions) &&
           Objects.equals(this.bypassedBy, other.bypassedBy) &&
           Objects.equals(this.itemDamage, other.itemDamage) &&
           Float.compare(this.blockDelay, other.blockDelay) == 0 &&
           Float.compare(this.disableCooldownScale, other.disableCooldownScale) == 0 &&
           Objects.equals(this.blockSound, other.blockSound) &&
           Objects.equals(this.disableSound, other.disableSound);
  }

  @Override
  public int hashCode() {

    return Objects.hash(reductions, bypassedBy, itemDamage, blockDelay, disableCooldownScale, blockSound, disableSound);
  }

  public List<DamageReduction> reductions() {

    return reductions;
  }

  public void reductions(final List<DamageReduction> reductions) {

    this.reductions.clear();
    this.reductions.addAll(reductions);
  }

  public void reductions(final DamageReduction... reductions) {

    this.reductions.clear();
    this.reductions.addAll(Arrays.asList(reductions));
  }

  public List<String> bypassedBy() {

    return bypassedBy;
  }

  public void bypassedBy(final List<String> bypassedBy) {

    this.bypassedBy.clear();
    this.bypassedBy.addAll(bypassedBy);
  }

  public void bypassedBy(final String... bypassedBy) {

    this.bypassedBy.clear();
    this.bypassedBy.addAll(Arrays.asList(bypassedBy));
  }

  public ItemDamage itemDamage() {

    return itemDamage;
  }

  public void itemDamage(final ItemDamage itemDamage) {

    this.itemDamage = itemDamage;
  }

  public float blockDelay() {

    return blockDelay;
  }

  public void blockDelay(final float blockDelay) {

    this.blockDelay = blockDelay;
  }

  public float disableCooldownScale() {

    return disableCooldownScale;
  }

  public void disableCooldownScale(final float disableCooldownScale) {

    this.disableCooldownScale = disableCooldownScale;
  }

  public String blockSound() {

    return blockSound;
  }

  public void blockSound(final String blockSound) {

    this.blockSound = blockSound;
  }

  public String disableSound() {

    return disableSound;
  }

  public void disableSound(final String disableSound) {

    this.disableSound = disableSound;
  }
}