package net.tnemc.item.data.banner;

public class PatternData {

  private int color;
  private String pattern;

  public PatternData(int color, String pattern) {
    this.color = color;
    this.pattern = pattern;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
}