package net.tnemc.item.data;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.banner.PatternData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BannerData<T> implements SerialItemData<T> {

  private List<PatternData> patterns = new ArrayList<>();
  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  @Override
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "banner");


    int i = 0;
    JSONObject patternsObject = new JSONObject();
    for(PatternData pattern : patterns) {
      JSONObject patternObj = new JSONObject();
      patternObj.put("colour", pattern.getColor());
      patternObj.put("pattern", pattern.getPattern());
      patternsObject.put(i, patternObj);
      i++;
    }
    json.put("patterns", patternsObject);
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  @Override
  public void readJSON(JSONHelper json) {
    json.getJSON("patterns").forEach((key, value)->{
      JSONHelper helperObj = new JSONHelper((JSONObject)value);
      final PatternData pattern = new PatternData(helperObj.getInteger("colour"),
                                                  helperObj.getString("pattern"));
      patterns.add(pattern);
    });
  }
}