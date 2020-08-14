package net.tnemc.item;

import net.tnemc.item.attribute.SerialAttribute;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface AbstractItemStack {

  AbstractItemStack of(final String material, final int amount);

  AbstractItemStack of(SerialItem serialItem);

  AbstractItemStack of(Object locale);

  AbstractItemStack of(JSONObject json);

  AbstractItemStack flags(List<String> flags);

  AbstractItemStack lore(List<String> lore);

  AbstractItemStack attribute(String name, SerialAttribute attribute);

  AbstractItemStack attribute(Map<String, SerialAttribute> attributes);

  AbstractItemStack enchant(String enchantment, int level);

  AbstractItemStack enchant(Map<String, Integer> enchantments);

  AbstractItemStack material(String material);

  AbstractItemStack amount(final int amount);

  AbstractItemStack slot(int slot);

  AbstractItemStack display(String display);

  AbstractItemStack damage(short damage);

  AbstractItemStack modelData(int modelData);

  AbstractItemStack applyData(SerialItemData data);

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

  SerialItemData data();

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  Object locale();
}