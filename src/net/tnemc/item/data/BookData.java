package net.tnemc.item.data;


import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * TheNewItemLibrary
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 11/10/2017.
 */
public class BookData implements SerialItemData {

  private List<String> pages = new ArrayList<>();

  private String title;
  private String author;

  private boolean valid = false;

  @Override
  public SerialItemData initialize(ItemStack stack) {
    ItemMeta meta = stack.getItemMeta();
    if(meta instanceof BookMeta) {
      BookMeta bookMeta = (BookMeta)meta;
      valid = true;

      title = bookMeta.getTitle();
      author = bookMeta.getAuthor();
      pages = bookMeta.getPages();
    }
    return this;
  }

  @Override
  public ItemStack build(ItemStack stack) {
    if(valid) {
      BookMeta meta = (BookMeta) stack.getItemMeta();
      if(title != null) meta.setTitle(title);
      if(author != null) meta.setAuthor(author);
      meta.setPages(pages);
      stack.setItemMeta(meta);
    }
    return stack;
  }

  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "book");
    if(title != null) json.put("title", title);
    if(author != null) json.put("author", author);

    JSONObject pagesObj = new JSONObject();
    for(int i = 0; i < pages.size(); i++) {
      pagesObj.put(i, pages.get(i));
    }
    json.put("pages", pagesObj);
    return json;
  }

  @Override
  public void readJSON(JSONHelper json) {
    valid = true;
    if(json.has("title")) title = json.getString("title");
    if(json.has("author")) author = json.getString("author");
    JSONObject pagesObj = json.getJSON("pages");
    pages = new ArrayList<>();
    pagesObj.forEach((key, page)->pages.add(String.valueOf(page)));
  }
}