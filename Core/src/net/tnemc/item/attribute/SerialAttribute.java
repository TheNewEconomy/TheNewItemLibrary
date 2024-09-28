package net.tnemc.item.attribute;
/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import java.util.UUID;

public class SerialAttribute {

  private UUID identifier;
  private String name;
  private double amount;
  private SerialAttributeSlot slot = null;
  private SerialAttributeOperation operation;

  public SerialAttribute(final String name, final double amount, final SerialAttributeOperation operation) {

    this(UUID.randomUUID(), name, amount, operation);
  }

  public SerialAttribute(final UUID identifier, final String name, final double amount, final SerialAttributeOperation operation) {

    this(identifier, name, amount, operation, null);
  }

  public SerialAttribute(final UUID identifier, final String name, final double amount, final SerialAttributeOperation operation, final SerialAttributeSlot slot) {

    this.identifier = identifier;
    this.name = name;
    this.amount = amount;
    this.operation = operation;
    if(slot != null) {
      this.slot = slot;
    }
  }

  public UUID getIdentifier() {

    return identifier;
  }

  public void setIdentifier(final UUID identifier) {

    this.identifier = identifier;
  }

  public String getName() {

    return name;
  }

  public void setName(final String name) {

    this.name = name;
  }

  public double getAmount() {

    return amount;
  }

  public void setAmount(final double amount) {

    this.amount = amount;
  }

  public SerialAttributeSlot getSlot() {

    return slot;
  }

  public void setSlot(final SerialAttributeSlot slot) {

    this.slot = slot;
  }

  public SerialAttributeOperation getOperation() {

    return operation;
  }

  public void setOperation(final SerialAttributeOperation operation) {

    this.operation = operation;
  }
}