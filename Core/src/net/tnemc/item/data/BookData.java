package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class BookData<T> implements SerialItemData<T> {
  private List<String> pages = new ArrayList<>();

  private String title;
  private String author;
  private String generation;


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
    if(author != null) json.put("generation", generation);

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
    if(json.has("generation")) generation = json.getString("generation");
    JSONObject pagesObj = json.getJSON("pages");
    pages = new LinkedList<>();
    pagesObj.forEach((key, page)->pages.add(String.valueOf(page)));
  }

  /**
   * Used to determine if some data is equal to this data. This means that it has to be an exact copy
   * of this data. For instance, book copies will return false when compared to the original.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean equals(SerialItemData<? extends T> data) {

    if(data instanceof BookData) {
      final BookData<?> bookData = (BookData<?>)data;

      return Objects.equals(author, bookData.author) && Objects.equals(title, bookData.title)
          && Objects.equals(generation, bookData.generation) && pages.equals(bookData.pages);
    }
    return false;
  }

  /**
   * Used to determine if some data is similar to this data. This means that it doesn't have to be a
   * strict equals. For instance, book copies would return true when compared to the original, etc.
   *
   * @param data The data to compare.
   *
   * @return True if similar, otherwise false.
   */
  @Override
  public boolean similar(SerialItemData<? extends T> data) {

    if(data instanceof BookData) {
      final BookData<?> bookData = (BookData<?>)data;

      return Objects.equals(author, bookData.author) && Objects.equals(title, bookData.title)
          && pages.equals(bookData.pages);
    }
    return false;
  }
}
