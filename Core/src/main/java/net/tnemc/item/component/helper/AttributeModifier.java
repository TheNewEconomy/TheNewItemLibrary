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

/**
 * AttributeModifiers
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class AttributeModifier {

  protected String type;
  protected EquipSlot slot = EquipSlot.ANY;
  protected String id;
  protected double amount = 0.0;
  protected String operation;

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

  @Override
  public AttributeModifier clone() throws CloneNotSupportedException {

    final AttributeModifier copy = new AttributeModifier(this.type, this.id, this.operation);
    copy.setAmount(this.amount);
    copy.setSlot(this.slot);
    return copy;
  }
}