package net.tnemc.item.data.firework;

import net.tnemc.item.JSONHelper;
import net.tnemc.item.SerialItemData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FireworkEffectData {

  private List<Integer> colors = new ArrayList<>();
  private List<Integer> fadeColors = new ArrayList<>();

  private String type;
  private boolean trail;
  private boolean flicker;

  private boolean hasEffect = false;

  public FireworkEffectData(List<Integer> colors, List<Integer> fadeColors, String type,
                            boolean trail, boolean flicker, boolean hasEffect) {
    this.colors = colors;
    this.fadeColors = fadeColors;
    this.type = type;
    this.trail = trail;
    this.flicker = flicker;
    this.hasEffect = hasEffect;
  }

  /**
   * Converts the {@link SerialItemData} to a JSON object.
   *
   * @return The JSONObject representing this {@link SerialItemData}.
   */
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("name", "fireworkeffect");

    colors = new ArrayList<>();
    if(colors.size() > 0) {
      JSONObject colours = new JSONObject();
      for (int i = 0; i < colors.size(); i++) {
        colours.put(i, colors.get(i));
      }
      json.put("colours", colours);
    }

    fadeColors = new ArrayList<>();
    if(fadeColors.size() > 0) {
      JSONObject fades = new JSONObject();
      for (int i = 0; i < fadeColors.size(); i++) {
        fades.put(i, fadeColors.get(i));
      }
      json.put("fades", fades);
    }

    if(hasEffect) {
      JSONObject effect = new JSONObject();
      effect.put("type", type);
      effect.put("trail", trail);
      effect.put("flicker", flicker);
      json.put("effect", effect);
    }
    return json;
  }

  /**
   * Reads JSON data and converts it back to a {@link SerialItemData} object.
   *
   * @param json The JSONHelper instance of the json data.
   */
  public static FireworkEffectData readJSON(JSONHelper json) {

    List<Integer> colors = new ArrayList<>();
    if(json.has("colours")) {
      JSONObject colours = json.getJSON("colours");
      colours.forEach((ignore, value)->colors.add(Integer.valueOf(String.valueOf(value))));
    }

    List<Integer> fadeColors = new ArrayList<>();
    if(json.has("fades")) {
      JSONObject fades = json.getJSON("fades");
      fades.forEach((ignore, value)->fadeColors.add(Integer.valueOf(String.valueOf(value))));
    }
    String type = null;
    boolean trail = false;
    boolean flicker = false;
    boolean hasEffect = false;
    if(json.has("effect")) {
      hasEffect = true;
      JSONHelper helper = json.getHelper("effect");
      type = helper.getString("type");
      trail = helper.getBoolean("trail");
      flicker = helper.getBoolean("flicker");
    }

    return new FireworkEffectData(colors, fadeColors, type, trail, flicker, hasEffect);
  }

  public List<Integer> getColors() {
    return colors;
  }

  public void setColors(List<Integer> colors) {
    this.colors = colors;
  }

  public List<Integer> getFadeColors() {
    return fadeColors;
  }

  public void setFadeColors(List<Integer> fadeColors) {
    this.fadeColors = fadeColors;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean hasTrail() {
    return trail;
  }

  public void setTrail(boolean trail) {
    this.trail = trail;
  }

  public boolean hasFlicker() {
    return flicker;
  }

  public void setFlicker(boolean flicker) {
    this.flicker = flicker;
  }

  public boolean hasEffect() {
    return hasEffect;
  }

  public void setHasEffect(boolean hasEffect) {
    this.hasEffect = hasEffect;
  }
}