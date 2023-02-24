package net.tnemc.item.sponge;

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import org.json.simple.JSONObject;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sponge7ItemStack implements AbstractItemStack<ItemStack> {

  private final List<String> flags = new ArrayList<>();
  private final Map<String, Integer> enchantments = new HashMap<>();
  private final List<String> lore = new ArrayList<>();

  private int slot = 0;
  private ItemType material;
  private Integer amount = 1;
  private String display = "";
  private short damage = 0;
  private int customModelData = -1;
  private boolean unbreakable = false;

  //Our locale stack builder
  ItemStack stack;


  @Override
  public AbstractItemStack<ItemStack> of(String material, int amount) {
    try {
      this.material = (ItemType)DummyObjectProvider.createFor(ItemType.class, material);
    } catch(Exception ignore) {

    }
    this.amount = amount;
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> of(SerialItem<ItemStack> serialItem) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> of(ItemStack locale) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> of(JSONObject json) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> flags(List<String> flags) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> lore(List<String> lore) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> attribute(String name, SerialAttribute attribute) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> attribute(Map<String, SerialAttribute> attributes) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(String enchantment, int level) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(Map<String, Integer> enchantments) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(List<String> enchantments) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> material(String material) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> amount(int amount) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> slot(int slot) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> display(String display) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> damage(short damage) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> modelData(int modelData) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> unbreakable(boolean unbreakable) {
    return this;
  }

  @Override
  public AbstractItemStack<ItemStack> applyData(SerialItemData<ItemStack> data) {
    return this;
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
  public boolean unbreakable() {
    return false;
  }

  @Override
  public SerialItemData<ItemStack> data() {
    return null;
  }

  @Override
  public boolean similar(AbstractItemStack<? extends ItemStack> compare) {
    return false;
  }

  @Override
  public ItemStack locale() {
    return stack;
  }
}
