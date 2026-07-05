package net.tnemc.item.component.helper;
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

import net.kyori.adventure.text.Component;

import java.util.Optional;

/**
 * AttributeModifiers
 *
 * @author creatorfromhell
 * @since 0.1.3.0
 */
public class AttributeModifier {

  protected String id;
  protected String type;
  protected EquipSlot slot = EquipSlot.ANY;
  protected String operation;
  protected double amount = 0.0;

  //Optional:

  //@since 0.2.0.0
  protected String displayType = null;
  protected Component displayValue = null;

  public AttributeModifier(final String type, final String id, final String operation) {

    this.type = type;
    this.id = id;
    this.operation = operation;
  }

  public String getType() {

    return type;
  }

  public void setType(final String type) {

    this.type = type;
  }

  public EquipSlot getSlot() {

    return slot;
  }

  public void setSlot(final EquipSlot slot) {

    this.slot = slot;
  }

  public String getId() {

    return id;
  }

  public void setId(final String id) {

    this.id = id;
  }

  public double getAmount() {

    return amount;
  }

  public void setAmount(final double amount) {

    this.amount = amount;
  }

  public String getOperation() {

    return operation;
  }

  public void setOperation(final String operation) {

    this.operation = operation;
  }

  /**
   * Retrieves the optional display type associated with this attribute modifier.
   * The display type provides a string representation that may categorize
   * or describe the purpose or context of the attribute modifier in a user-friendly way.
   *
   * @return an {@link Optional} containing the display type if it is set, or an empty {@link Optional} if no display type is defined
   * @since 0.2.0.0
   */
  public Optional<String> displayType() {

    return Optional.ofNullable(displayType);
  }

  /**
   * Sets the display type for this attribute modifier.
   * The display type is used to categorize or describe the attribute modifier's
   * purpose or context in a more user-friendly or visual manner.
   *
   * @param displayType the string representing the display type of this attribute modifier
   * @since 0.2.0.0
   */
  public void setDisplayType(final String displayType) {

    this.displayType = displayType;
  }

  /**
   * Retrieves the optional display value associated with this attribute modifier.
   * The display value represents a visual or textual component that may describe
   * or signify the attribute modifier in a user interface or display context.
   *
   * @return an {@link Optional} containing the display value if present, or an empty {@link Optional} if no display value is set
   * @since 0.2.0.0
   */
  public Optional<Component> displayValue() {

    return Optional.ofNullable(displayValue);
  }

  /**
   * Sets the display value for this attribute modifier.
   *
   * @param displayValue the component representing the display value
   * @since 0.2.0.0
   */
  public void setDisplayValue(final Component displayValue) {

    this.displayValue = displayValue;
  }

  @Override
  public AttributeModifier clone() throws CloneNotSupportedException {

    final AttributeModifier copy = new AttributeModifier(this.type, this.id, this.operation);
    copy.setAmount(this.amount);
    copy.setSlot(this.slot);
    copy.setDisplayType(this.displayType);
    copy.setDisplayValue(this.displayValue);
    return copy;
  }
}