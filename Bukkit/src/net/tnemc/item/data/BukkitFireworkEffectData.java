package net.tnemc.item.data;

import net.tnemc.item.ParsingUtil;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.firework.SerialFireworkEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

import java.util.ArrayList;
import java.util.List;

public class BukkitFireworkEffectData extends FireworkData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final FireworkEffectMeta meta = (FireworkEffectMeta)stack.getItemMeta();

    if(meta != null) {

      if(meta.hasEffect() && meta.getEffect() != null) {
        final SerialFireworkEffect effect = new SerialFireworkEffect();

        for(Color color : meta.getEffect().getColors()) {
          effect.getColors().add(color.asRGB());
        }

        for(Color color : meta.getEffect().getFadeColors()) {
          effect.getFadeColors().add(color.asRGB());
        }

        effect.setType(meta.getEffect().getType().name());
        effect.setTrail(meta.getEffect().hasTrail());
        effect.setFlicker(meta.getEffect().hasFlicker());

        effects.add(effect);
      }
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    final FireworkEffectMeta meta = (FireworkEffectMeta)ParsingUtil.buildFor(stack, FireworkEffectMeta.class);

    if(effects.size() > 0) {
      final SerialFireworkEffect effect = effects.get(0);

      final List<Color> colors = new ArrayList<>();
      for(Integer i : effect.getColors()) {
        colors.add(Color.fromRGB(i));
      }

      final List<Color> faded = new ArrayList<>();
      for(Integer i : effect.getFadeColors()) {
        faded.add(Color.fromRGB(i));
      }

      final FireworkEffect.Builder effectBuilder = FireworkEffect.builder()
          .flicker(effect.hasFlicker()).trail(effect.hasTrail()).withColor(colors).withFade(faded);

      meta.setEffect(effectBuilder.build());
    }

    return stack;
  }
}