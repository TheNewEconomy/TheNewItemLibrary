package net.tnemc.item;


import net.tnemc.item.data.BannerData;
import net.tnemc.item.data.BookData;
import net.tnemc.item.data.EnchantStorageData;
import net.tnemc.item.data.FireworkData;
import net.tnemc.item.data.FireworkEffectData;
import net.tnemc.item.data.LeatherData;
import net.tnemc.item.data.MapData;
import net.tnemc.item.data.SerialPotionData;
import net.tnemc.item.data.ShulkerData;
import net.tnemc.item.data.SkullData;
import net.tnemc.item.data.TropicalFishBucketData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TheNewItemLibrary
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 11/10/2017.
 */
public class SerialItem {

  private Map<String, Integer> enchantments = new HashMap<>();
  private List<String> lore = new ArrayList<>();

  private int slot = 0;
  private Material material;
  private Integer amount = 1;
  private String display = "";
  private short damage = 0;
  private SerialItemData data;

  //Cache=related variables
  private ItemStack stack;

  public SerialItem(ItemStack stack) {
    this(stack, 0);
  }

  public SerialItem(ItemStack item, Integer slot) {
    stack = item;
    material = stack.getType();
    amount = stack.getAmount();
    damage = stack.getDurability();

    if(stack.hasItemMeta()) {
      display = stack.getItemMeta().getDisplayName();
      lore = stack.getItemMeta().getLore();

      if(stack.getItemMeta().hasEnchants()) {

        stack.getItemMeta().getEnchants().forEach(((enchantment, level) ->{
          enchantments.put(enchantment.getName(), level);
        }));
      }
    }
    buildData(stack);
  }

  private void buildData(ItemStack stack) {
    if(ItemCalculations.isShulker(stack.getType())) {
      data = new ShulkerData();
    } else {
      ItemMeta meta = stack.getItemMeta();
      if (meta instanceof PotionMeta) {
        data = new SerialPotionData();
      } else if (meta instanceof BookMeta) {
        data = new BookData();
      } else if (meta instanceof BannerMeta) {
        data = new BannerData();
      } else if (meta instanceof LeatherArmorMeta) {
        data = new LeatherData();
      } else if (meta instanceof SkullMeta) {
        data = new SkullData();
      } else if (meta instanceof MapMeta) {
        data = new MapData();
      } else if (meta instanceof EnchantmentStorageMeta) {
        data = new EnchantStorageData();
      } else if (meta instanceof FireworkMeta) {
        data = new FireworkData();
      } else if (meta instanceof FireworkEffectMeta) {
        data = new FireworkEffectData();
      } else if(meta instanceof TropicalFishBucketMeta) {
        data = new TropicalFishBucketData();
      }
    }
    if(data != null){
      data.initialize(stack);
    }
  }

  public int getSlot() {
    return slot;
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public Map<String, Integer> getEnchantments() {
    return enchantments;
  }

  public void setEnchantments(Map<String, Integer> enchantments) {
    this.enchantments = enchantments;
  }

  public List<String> getLore() {
    return lore;
  }

  public void setLore(List<String> lore) {
    this.lore = lore;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getDisplay() {
    return display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public short getDamage() {
    return damage;
  }

  public void setDamage(short damage) {
    this.damage = damage;
  }

  public SerialItemData getData() {
    return data;
  }

  public void setData(SerialItemData data) {
    this.data = data;
  }

  /**
   * @return An itemstack instance from this SerialItem
   */
  public ItemStack getStack() {
    if(stack == null) {
      stack = new ItemStack(material, amount, damage);
      ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
      if(display != null) {
        meta.setDisplayName(display);
      }
      meta.setLore(lore);
      enchantments.forEach((name, level)->{
        meta.addEnchant(Enchantment.getByName(name), level, true);
      });
      stack.setItemMeta(meta);
      if(data != null) {
        stack = data.build(stack);
      }
    }
    return stack;
  }

  public void setStack(ItemStack stack) {
    this.stack = stack;
  }

  /**
   * @return A JSONObject based off this SerialItem.
   */
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("slot", slot);
    json.put("material", material.name());
    json.put("amount", amount);
    if(display != null && !display.equalsIgnoreCase("")) json.put("display", display);
    json.put("damage", damage);
    if(lore != null && lore.size() > 0) json.put("lore", String.join(",", lore));

    JSONObject object = new JSONObject();
    enchantments.forEach(object::put);
    json.put("enchantments", object);
    if(data != null) {
      json.put("data", data.toJSON());
    }
    return json;
  }

  /**
   * @return A JSON String representing this SerialItem.
   */
  public String serialize() {
    return toJSON().toJSONString();
  }

  /**
   * Reverts the serialize method back to a SerialItem instance.
   * @param serialized The String to deserialize.
   * @return The SerialItem instance based on the String.
   * @throws ParseException Invalid JSON String.
   */
  public static SerialItem unserialize(String serialized) throws ParseException {
    return fromJSON((JSONObject)new JSONParser().parse(serialized));
  }

  public static SerialItem fromJSON(JSONObject json) {
    final JSONHelper helper = new JSONHelper(json);
    Material material = Material.matchMaterial(helper.getString("material"));
    ItemStack stack = new ItemStack(material, helper.getInteger("amount"));
    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
    if(helper.has("display") && helper.isNull("display") && !helper.getString("display").equalsIgnoreCase("")) {
      meta.setDisplayName(helper.getString("display"));
    }
    if(helper.has("lore")) meta.setLore(new ArrayList<>(Arrays.asList((helper.getString("lore")).split(","))));
    stack.setItemMeta(meta);

    if(json.containsKey("enchantments")) {
      JSONObject enchants = (JSONObject)json.get("enchantments");
      enchants.forEach((key, value) -> {
        stack.addUnsafeEnchantment(Enchantment.getByName(String.valueOf(key)), Integer.valueOf(String.valueOf(value)));
      });
    }

    stack.setDurability(helper.getShort("damage"));
    SerialItem serial = new SerialItem(stack, helper.getInteger("slot"));
    if(helper.has("data")) {
      serial.getData().readJSON(helper.getHelper("data"));
      serial.getData().build(stack);
    }
    return serial;
  }
}