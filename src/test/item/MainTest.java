package test.item;

import net.tnemc.item.SerialItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.parser.ParseException;

/**
 * Created by creatorfromhell.
 *
 * The New Item Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0
 * International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/
 * or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
public class MainTest {

  public static void main(String[] args) {
    //First we setup our itemstack.
    ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
    ItemMeta meta = stack.getItemMeta();
    meta.setDisplayName(ChatColor.GOLD + "Non-Epic Sword");
    stack.addEnchantment(Enchantment.FIRE_ASPECT, 2);
    stack.addEnchantment(Enchantment.KNOCKBACK, 2);
    stack.addEnchantment(Enchantment.DAMAGE_ALL, 5);

    //Now we Serialize our ItemStack.
    final String serialized = new SerialItem(stack).serialize();
    System.out.println(serialized);

    //Now we unserialize it.
    try {
      stack = SerialItem.unserialize(serialized).getStack();
    } catch(ParseException ignore) {
      //Invalid JSON String
    }

    //Now we re-serialize for our sanity check.
    final String reserialized = new SerialItem(stack).serialize();
    System.out.println(reserialized);
  }
}