package net.tnemc.item.attribute;

import java.util.UUID;

public class SerialAttribute {

  private UUID identifier;
  private String name;
  private double amount;
  private SerialAttributeSlot slot = null;
  private SerialAttributeOperation operation;

  public SerialAttribute(String name, double amount, SerialAttributeOperation operation) {
    this(UUID.randomUUID(), name, amount, operation);
  }

  public SerialAttribute(UUID identifier, String name, double amount, SerialAttributeOperation operation) {
    this(identifier, name, amount, operation, null);
  }

  public SerialAttribute(UUID identifier, String name, double amount, SerialAttributeOperation operation, SerialAttributeSlot slot) {
    this.identifier = identifier;
    this.name = name;
    this.amount = amount;
    this.operation = operation;
    this.slot = slot;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public SerialAttributeSlot getSlot() {
    return slot;
  }

  public void setSlot(SerialAttributeSlot slot) {
    this.slot = slot;
  }

  public SerialAttributeOperation getOperation() {
    return operation;
  }

  public void setOperation(SerialAttributeOperation operation) {
    this.operation = operation;
  }
}