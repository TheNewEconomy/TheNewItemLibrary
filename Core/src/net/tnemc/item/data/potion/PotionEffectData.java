package net.tnemc.item.data.potion;

public class PotionEffectData {

  private String name;
  private int amplifier;
  private int duration;

  private boolean particles;
  private boolean ambient;
  private boolean icon;

  public PotionEffectData(String name, int amplifier, int duration, boolean particles, boolean ambient, boolean icon) {
    this.name = name;
    this.amplifier = amplifier;
    this.duration = duration;
    this.particles = particles;
    this.ambient = ambient;
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAmplifier() {
    return amplifier;
  }

  public void setAmplifier(int amplifier) {
    this.amplifier = amplifier;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public boolean hasParticles() {
    return particles;
  }

  public void setParticles(boolean particles) {
    this.particles = particles;
  }

  public boolean isAmbient() {
    return ambient;
  }

  public void setAmbient(boolean ambient) {
    this.ambient = ambient;
  }

  public boolean hasIcon() {
    return icon;
  }

  public void setIcon(boolean icon) {
    this.icon = icon;
  }
}