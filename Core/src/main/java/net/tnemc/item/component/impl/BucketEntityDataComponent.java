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
 * BucketEntityDataComponent - NBT applied to an entity when placed from this bucket. Only tags
 * below are applied.
 *
 * @author creatorfromhell
 * @see <a href="https://minecraft.wiki/w/Data_component_format#bucket_entity_data">Reference</a>
 * <p>
 * @since 0.2.0.0
 */
public abstract class BucketEntityDataComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

  protected boolean noAI;
  protected boolean silent;
  protected boolean noGravity;
  protected boolean glowing;
  protected boolean invulnerable;
  protected float health = 0.0f; // Default to 0
  protected int age = 0; // Default to 0 for axolotls and tadpoles.
  protected int variant = 0; // Default to 0 for axolotls.
  protected long huntingCooldown = 0L; // Default to 0 for axolotls.
  protected int bucketVariantTag = 0; // Default to 0 - for tropical fish
  protected String type = ""; // for salmon

  public BucketEntityDataComponent() {

  }

  public BucketEntityDataComponent(final boolean noAI, final boolean silent, final boolean noGravity,
                                   final boolean glowing, final boolean invulnerable) {

    this.noAI = noAI;
    this.silent = silent;
    this.noGravity = noGravity;
    this.glowing = glowing;
    this.invulnerable = invulnerable;
  }

  public BucketEntityDataComponent(final boolean noAI, final boolean silent, final boolean noGravity,
                                   final boolean glowing, final boolean invulnerable, final float health,
                                   final int age, final int variant, final long huntingCooldown,
                                   final int bucketVariantTag, final String type) {

    this.noAI = noAI;
    this.silent = silent;
    this.noGravity = noGravity;
    this.glowing = glowing;
    this.invulnerable = invulnerable;
    this.health = health;
    this.age = age;
    this.variant = variant;
    this.huntingCooldown = huntingCooldown;
    this.bucketVariantTag = bucketVariantTag;
    this.type = type;
  }

  @Override
  public String identifier() {

    return "bucket_entity_data";
  }

  @Override
  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("NoAI", noAI);
    json.put("Silent", silent);
    json.put("NoGravity", noGravity);
    json.put("Glowing", glowing);
    json.put("Invulnerable", invulnerable);
    json.put("Health", health);
    json.put("Age", age);
    json.put("Variant", variant);
    json.put("HuntingCooldown", huntingCooldown);
    json.put("BucketVariantTag", bucketVariantTag);
    json.put("type", type);
    return json;
  }

  @Override
  public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

    noAI = json.getBoolean("NoAI");
    silent = json.getBoolean("Silent");
    noGravity = json.getBoolean("NoGravity");
    glowing = json.getBoolean("Glowing");
    invulnerable = json.getBoolean("Invulnerable");
    health = json.getFloat("Health");
    age = json.getInteger("Age");
    variant = json.getInteger("Variant");
    huntingCooldown = json.getLong("HuntingCooldown");
    bucketVariantTag = json.getInteger("BucketVariantTag");
    type = json.getString("type");
  }

  @Override
  public boolean similar(final SerialComponent<?, ?> component) {

    if(!(component instanceof final BucketEntityDataComponent<?, ?> other)) return false;
    return this.noAI == other.noAI &&
           this.silent == other.silent &&
           this.noGravity == other.noGravity &&
           this.glowing == other.glowing &&
           this.invulnerable == other.invulnerable &&
           Float.compare(this.health, other.health) == 0 &&
           this.age == other.age &&
           this.variant == other.variant &&
           this.huntingCooldown == other.huntingCooldown &&
           this.bucketVariantTag == other.bucketVariantTag &&
           Objects.equals(this.type, other.type);
  }

  @Override
  public int hashCode() {

    return Objects.hash(noAI, silent, noGravity, glowing, invulnerable, health, age, variant, huntingCooldown, bucketVariantTag, type);
  }

  public boolean noAI() {

    return noAI;
  }

  public void noAI(final boolean noAI) {

    this.noAI = noAI;
  }

  public boolean silent() {

    return silent;
  }

  public void silent(final boolean silent) {

    this.silent = silent;
  }

  public boolean noGravity() {

    return noGravity;
  }

  public void noGravity(final boolean noGravity) {

    this.noGravity = noGravity;
  }

  public boolean glowing() {

    return glowing;
  }

  public void glowing(final boolean glowing) {

    this.glowing = glowing;
  }

  public boolean invulnerable() {

    return invulnerable;
  }

  public void invulnerable(final boolean invulnerable) {

    this.invulnerable = invulnerable;
  }

  public float health() {

    return health;
  }

  public void health(final float health) {

    this.health = health;
  }

  public int age() {

    return age;
  }

  public void age(final int age) {

    this.age = age;
  }

  public int variant() {

    return variant;
  }

  public void variant(final int variant) {

    this.variant = variant;
  }

  public long huntingCooldown() {

    return huntingCooldown;
  }

  public void huntingCooldown(final long huntingCooldown) {

    this.huntingCooldown = huntingCooldown;
  }

  public int bucketVariantTag() {

    return bucketVariantTag;
  }

  public void bucketVariantTag(final int bucketVariantTag) {

    this.bucketVariantTag = bucketVariantTag;
  }

  public String type() {

    return type;
  }

  public void type(final String type) {

    this.type = type;
  }
}