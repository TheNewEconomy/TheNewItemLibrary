package net.tnemc.item;

import net.tnemc.item.attribute.SerialAttribute;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an ItemStack object related to the Bukkit API.
 */
public class BukkitItemStack implements AbstractItemStack<ItemStack> {

  private final List<String> flags = new ArrayList<>();
  private final Map<String, AttributeModifier> attributes = new HashMap<>();
  private final Map<String, Integer> enchantments = new HashMap<>();
  private final List<String> lore = new ArrayList<>();

  private int slot = 0;
  private Material material;
  private Integer amount = 1;
  private String display = "";
  private short damage = 0;
  private int customModelData = -1;
  private boolean unbreakable = false;
  private SerialItemData<ItemStack> data;

  //our locale stack
  private ItemStack stack;

  @Override
  public BukkitItemStack of(String material, int amount) {
    this.material = Material.getMaterial(material);
    this.amount = amount;
    return this;
  }

  @Override
  public BukkitItemStack of(SerialItem<ItemStack> serialItem) {


    return this;
  }

  @Override
  public BukkitItemStack of(ItemStack locale) {
    this.stack = locale;
    //TODO: parse stack into the variables above.

    //Parse the meta data.
    ParsingUtil.parseMeta(locale)
               .ifPresent(itemStackSerialItemData->this.data = itemStackSerialItemData);

    return this;
  }

  @Override
  public BukkitItemStack of(JSONObject json) {

    try {
      final Optional<SerialItem<ItemStack>> serialStack = SerialItem.unserialize(json);

      if(serialStack.isPresent()) {
        return of(serialStack.get());
      }
    } catch(ParseException e) {
      e.printStackTrace();
    }

    return this;
  }

  @Override
  public BukkitItemStack flags(List<String> flags) {
    this.flags.clear();
    this.flags.addAll(flags);
    return this;
  }

  @Override
  public BukkitItemStack lore(List<String> lore) {
    this.lore.clear();
    this.lore.addAll(lore);
    return this;
  }

  @Override
  public BukkitItemStack attribute(String name, SerialAttribute attribute) {
    final AttributeModifier attr = new AttributeModifier(attribute.getIdentifier(),
                                                         attribute.getName(),
                                                         attribute.getAmount(),
                                                         ParsingUtil.attributeOperation(attribute.getOperation()),
                                                         ParsingUtil.attributeSlot(attribute.getSlot()));


    attributes.put(name, attr);
    return this;
  }

  @Override
  public BukkitItemStack attribute(Map<String, SerialAttribute> attributes) {

    for(Map.Entry<String, SerialAttribute> entry : attributes.entrySet()) {

      final SerialAttribute attribute = entry.getValue();
      final AttributeModifier attr = new AttributeModifier(attribute.getIdentifier(),
                                                           attribute.getName(),
                                                           attribute.getAmount(),
                                                           ParsingUtil.attributeOperation(attribute.getOperation()),
                                                           ParsingUtil.attributeSlot(attribute.getSlot()));


      this.attributes.put(entry.getKey(), attr);
    }
    return this;
  }

  @Override
  public BukkitItemStack enchant(String enchantment, int level) {
    enchantments.put(enchantment, level);
    return this;
  }

  @Override
  public BukkitItemStack enchant(Map<String, Integer> enchantments) {
    this.enchantments.clear();
    this.enchantments.putAll(enchantments);
    return this;
  }

  @Override
  public BukkitItemStack material(String material) {
    this.material = Material.getMaterial(material);
    return this;
  }

  @Override
  public BukkitItemStack amount(int amount) {
    this.amount = amount;
    return this;
  }

  @Override
  public BukkitItemStack slot(int slot) {
    this.slot = slot;
    return this;
  }

  @Override
  public BukkitItemStack display(String display) {
    this.display = display;
    return this;
  }

  @Override
  public BukkitItemStack damage(short damage) {
    this.damage = damage;
    return this;
  }

  @Override
  public BukkitItemStack modelData(int modelData) {
    this.customModelData = modelData;
    return this;
  }

  @Override
  public BukkitItemStack unbreakable(boolean unbreakable) {
    this.unbreakable = unbreakable;
    return this;
  }

  @Override
  public BukkitItemStack applyData(SerialItemData<ItemStack> data) {
    this.data = data;
    return this;
  }

  @Override
  public List<String> flags() {
    return flags;
  }

  @Override
  public List<String> lore() {
    return lore;
  }

  @Override
  public Map<String, SerialAttribute> attributes() {
    final Map<String, SerialAttribute> serialAttributes = new HashMap<>();

    for(Map.Entry<String, AttributeModifier> entry : attributes.entrySet()) {

      final AttributeModifier attribute = entry.getValue();

      final SerialAttribute attr = new SerialAttribute(attribute.getUniqueId(),
                                                       attribute.getName(),
                                                       attribute.getAmount(),
                                                       ParsingUtil.attributeOperation(attribute.getOperation()));
      if(attribute.getSlot() != null) {
        attr.setSlot(ParsingUtil.attributeSlot(attribute.getSlot()));
      }
      serialAttributes.put(entry.getKey(), attr);
    }
    return serialAttributes;
  }

  @Override
  public Map<String, Integer> enchantments() {
    return enchantments;
  }

  @Override
  public String material() {
    return material.getKey().toString();
  }

  @Override
  public int amount() {
    return amount;
  }

  @Override
  public int slot() {
    return slot;
  }

  @Override
  public String display() {
    return display;
  }

  @Override
  public short damage() {
    return damage;
  }

  @Override
  public int modelData() {
    return customModelData;
  }

  @Override
  public boolean unbreakable() {
    return unbreakable;
  }

  @Override
  public SerialItemData<ItemStack> data() {
    return data;
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
    return stack.isSimilar(compare.locale());
  }

  public static BukkitItemStack locale(ItemStack stack) {
    return new BukkitItemStack().of(stack);
  }

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   */
  @Override
  public ItemStack locale() {

    //TODO: build stack.
    return stack;
  }
}