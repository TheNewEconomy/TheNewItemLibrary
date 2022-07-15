package net.tnemc.item;

import net.tnemc.item.attribute.SerialAttribute;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Represents an ItemStack object related to the Bukkit API.
 */
public class BukkitItemStack implements AbstractItemStack<ItemStack> {

  private ItemStack stack;

  @Override
  public BukkitItemStack of(String material, int amount) {
    return null;
  }

  @Override
  public BukkitItemStack of(SerialItem<ItemStack> serialItem) {
    return null;
  }

  @Override
  public BukkitItemStack of(ItemStack locale) {
    return null;
  }

  @Override
  public BukkitItemStack of(JSONObject json) {
    return null;
  }

  @Override
  public BukkitItemStack flags(List<String> flags) {
    return null;
  }

  @Override
  public BukkitItemStack lore(List<String> lore) {
    return null;
  }

  @Override
  public BukkitItemStack attribute(String name, SerialAttribute attribute) {
    return null;
  }

  @Override
  public BukkitItemStack attribute(Map<String, SerialAttribute> attributes) {
    return null;
  }

  @Override
  public BukkitItemStack enchant(String enchantment, int level) {
    return null;
  }

  @Override
  public BukkitItemStack enchant(Map<String, Integer> enchantments) {
    return null;
  }

  @Override
  public BukkitItemStack material(String material) {
    return null;
  }

  @Override
  public BukkitItemStack amount(int amount) {
    return null;
  }

  @Override
  public BukkitItemStack slot(int slot) {
    return null;
  }

  @Override
  public BukkitItemStack display(String display) {
    return null;
  }

  @Override
  public BukkitItemStack damage(short damage) {
    return null;
  }

  @Override
  public BukkitItemStack modelData(int modelData) {
    return null;
  }

  @Override
  public BukkitItemStack applyData(SerialItemData<ItemStack> data) {
    return null;
  }

  @Override
  public List<String> flags() {
    return null;
  }

  @Override
  public List<String> lore() {
    return null;
  }

  @Override
  public Map<String, SerialAttribute> attributes() {
    return null;
  }

  @Override
  public Map<String, Integer> enchantments() {
    return null;
  }

  @Override
  public String material() {
    return null;
  }

  @Override
  public int amount() {
    return 0;
  }

  @Override
  public int slot() {
    return 0;
  }

  @Override
  public String display() {
    return null;
  }

  @Override
  public short damage() {
    return 0;
  }

  @Override
  public int modelData() {
    return 0;
  }

  @Override
  public SerialItemData<ItemStack> data() {
    return null;
  }

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
   * <p>
   * What this does not include:
   * - Item Data.
   *
   * @param compare The stack to compare.
   *
   * @return True if the two are similar, otherwise false.
   */
  @Override
  public boolean similar(AbstractItemStack<? extends ItemStack> compare) {
    return false;
  }

  public static BukkitItemStack locale(ItemStack stack) {
    return new BukkitItemStack().of(stack);
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {
    return stack;
  }
}