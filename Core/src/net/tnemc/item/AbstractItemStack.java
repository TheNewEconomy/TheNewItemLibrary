package net.tnemc.item;

import net.tnemc.item.attribute.SerialAttribute;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface AbstractItemStack<T> extends Cloneable {

  AbstractItemStack<T> of(final String material, final int amount);

  AbstractItemStack<T> of(SerialItem<T> serialItem);

  AbstractItemStack<T> of(T locale);

  AbstractItemStack<T> of(JSONObject json);

  AbstractItemStack<T> flags(List<String> flags);

  AbstractItemStack<T> lore(List<String> lore);

  AbstractItemStack<T> attribute(String name, SerialAttribute attribute);

  AbstractItemStack<T> attribute(Map<String, SerialAttribute> attributes);

  AbstractItemStack<T> enchant(String enchantment, int level);

  AbstractItemStack<T> enchant(Map<String, Integer> enchantments);

  AbstractItemStack<T> material(String material);

  AbstractItemStack<T> amount(final int amount);

  AbstractItemStack<T> slot(int slot);

  AbstractItemStack<T> display(String display);

  AbstractItemStack<T> damage(short damage);

  AbstractItemStack<T> modelData(int modelData);

  AbstractItemStack<T> applyData(SerialItemData<T> data);

  List<String> flags();

  List<String> lore();

  Map<String, SerialAttribute> attributes();

  Map<String, Integer> enchantments();

  String material();

  int amount();

  int slot();

  String display();

  short damage();

  int modelData();

  SerialItemData<T> data();

  /**
   * Returns true if the provided item is similar to this.
   * An item is similar if the basic information is the same, except for the amount.
   * What this includes:
   * - material
   * - display
   * - modelData
   * - flags
   * - lore
   * - attributes
   * - enchantments
   *
   * What this does not include:
   * - Item Data.
   *
   * @param compare The stack to compare.
   * @return True if the two are similar, otherwise false.
   */
  boolean similar(AbstractItemStack<? extends T> compare);

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  T locale();
}