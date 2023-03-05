package net.tnemc.item.minestom;

import net.minestom.server.item.ItemStack;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MinestomItemStack implements AbstractItemStack<ItemStack> {

  private SerialItemData<ItemStack> data;

  //our locale stack
  private ItemStack stack;

  @Override
  public AbstractItemStack<ItemStack> of(String s, int i) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> of(SerialItem<ItemStack> serialItem) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> of(ItemStack itemStack) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> of(JSONObject jsonObject) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> flags(List<String> list) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> lore(List<String> list) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> attribute(String s, SerialAttribute serialAttribute) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> attribute(Map<String, SerialAttribute> map) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(String s, int i) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(Map<String, Integer> map) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> enchant(List<String> list) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> material(String s) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> amount(int i) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> slot(int i) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> display(String s) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> damage(short i) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> modelData(int i) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> unbreakable(boolean b) {
    return null;
  }

  @Override
  public AbstractItemStack<ItemStack> applyData(SerialItemData<ItemStack> serialItemData) {
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
  public boolean unbreakable() {
    return false;
  }

  @Override
  public Optional<SerialItemData<ItemStack>> data() {
    return Optional.empty();
  }

  @Override
  public boolean similar(AbstractItemStack<? extends ItemStack> abstractItemStack) {
    return false;
  }

  @Override
  public ItemStack locale() {
    return null;
  }
}