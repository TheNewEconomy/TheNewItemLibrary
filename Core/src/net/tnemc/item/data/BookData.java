package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BookData implements SerialItemData {

  private List<String> pages = new ArrayList<>();

  private String title;
  private String author;


  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
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

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    if(json.has("title")) title = json.getString("title");
    if(json.has("author")) author = json.getString("author");
    JSONObject pagesObj = json.getJSON("pages");
    pages = new ArrayList<>();
    pagesObj.forEach((key, page)->pages.add(String.valueOf(page)));
  }
}
