package net.tnemc.item.paper.platform.impl.modern;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.component.impl.PotionContentsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.Optional;

/**
 * PaperPotionContentsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperPotionContentsComponent extends PotionContentsComponent<PaperItemStack, ItemStack> {

  public PaperPotionContentsComponent() {

  }

  public PaperPotionContentsComponent(final String potionId) {

    super(potionId);
  }

  public PaperPotionContentsComponent(final String potionId, final int customColor) {

    super(potionId, customColor);
  }

  public PaperPotionContentsComponent(final String potionId,
                                      final int customColor,
                                      final String customName) {

    super(potionId, customColor, customName);
  }

  public PaperPotionContentsComponent(final String potionId,
                                      final int customColor,
                                      final String customName,
                                      final List<EffectInstance> effects) {

    super(potionId, customColor, customName, effects);
  }

  public PaperPotionContentsComponent(final String potionId,
                                      final int customColor,
                                      final String customName,
                                      final EffectInstance... effects) {

    super(potionId, customColor, customName, effects);
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperPotionContentsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperPotionContentsComponent component = componentOptional.get();
    final PotionContents.Builder builder = PotionContents.potionContents();

    if(component.potionId() != null && !component.potionId().isEmpty()) {
      builder.potion(PaperItemPlatform.instance().converter().convert(component.potionId(), PotionType.class));
    }

    if(component.customColor() > 0) {
      builder.customColor(Color.fromRGB(component.customColor()));
    }

    if(component.customName() != null && !component.customName().isEmpty()) {
      builder.customName(component.customName());
    }

    for(final EffectInstance effect : component.customEffects()) {
      builder.addCustomEffect(potionEffect(effect));
    }

    item.setData(DataComponentTypes.POTION_CONTENTS, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final PotionContents contents = item.getData(DataComponentTypes.POTION_CONTENTS);
    if(contents == null) {
      return serialized;
    }

    final PaperPotionContentsComponent component = (serialized.paperComponent(identifier()) instanceof final PotionContentsComponent<?, ?> getComponent)?
                                                   (PaperPotionContentsComponent)getComponent : new PaperPotionContentsComponent();

    if(contents.potion() != null) {
      component.potionId(PaperItemPlatform.instance().converter().convert(contents.potion(), String.class));
    }

    if(contents.customColor() != null) {
      component.customColor(contents.customColor().asRGB());
    }

    component.customName(contents.customName());
    component.customEffects(contents.customEffects().stream().map(this::effectInstance).toList());

    serialized.applyComponent(component);
    return serialized;
  }

  private PotionEffect potionEffect(final EffectInstance effect) {

    final PotionEffectType type = PaperItemPlatform.instance().converter().convert(effect.id(), PotionEffectType.class);

    return new PotionEffect(type,
                            effect.duration(),
                            effect.amplifier(),
                            effect.ambient(),
                            effect.showParticles(),
                            effect.showIcon());
  }

  private EffectInstance effectInstance(final PotionEffect effect) {

    return new EffectInstance(PaperItemPlatform.instance().converter().convert(effect.getType(), String.class),
                              effect.getAmplifier(),
                              effect.getDuration(),
                              effect.hasParticles(),
                              effect.isAmbient(),
                              effect.hasIcon());
  }
}