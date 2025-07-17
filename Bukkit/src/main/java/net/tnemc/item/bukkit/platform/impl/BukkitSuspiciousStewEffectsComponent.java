package net.tnemc.item.bukkit.platform.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.component.helper.effect.EffectInstance;
import net.tnemc.item.component.impl.SuspiciousStewEffectsComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Optional;

/**
 * BukkitSuspiciousStewEffectsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitSuspiciousStewEffectsComponent extends SuspiciousStewEffectsComponent<BukkitItemStack, ItemStack> {

  public BukkitSuspiciousStewEffectsComponent(final EffectInstance... effects) {

    super(effects);
  }

  public BukkitSuspiciousStewEffectsComponent(final List<EffectInstance> effects) {

    super(effects);
  }

  public BukkitSuspiciousStewEffectsComponent() {

  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneFifteen(version);
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   *
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final BukkitItemStack serialized, final ItemStack item) {

    final Optional<BukkitSuspiciousStewEffectsComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final SuspiciousStewMeta meta) {

        componentOptional.get().effects.forEach((effect)->{

          try {

            final PotionEffectType effectType = BukkitItemPlatform.instance().converter().convert(effect.id(), PotionEffectType.class);
            if(effectType != null) {

              meta.addCustomEffect(new PotionEffect(effectType,
                                                    effect.duration(),
                                                    effect.amplifier(),
                                                    effect.ambient(),
                                                    effect.showParticles(),
                                                    effect.showIcon()), true);
            }
          } catch(final Exception ignore) { }
        });

        item.setItemMeta(meta);
      }
    });
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   *
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    if(item.hasItemMeta() && item.getItemMeta() instanceof final SuspiciousStewMeta meta) {

      final BukkitSuspiciousStewEffectsComponent component = (serialized.bukkitComponent(identifier()) instanceof final SuspiciousStewEffectsComponent<?, ?> getComponent)?
                                                             (BukkitSuspiciousStewEffectsComponent)getComponent : new BukkitSuspiciousStewEffectsComponent();

      for(final PotionEffect effect : meta.getCustomEffects()) {

        try {
          final String id = BukkitItemPlatform.instance().converter().convert(effect.getType(), String.class);
          if(id != null) {

            component.effects.add(new EffectInstance(id,
                                                     effect.getAmplifier(),
                                                     effect.getDuration(),
                                                     effect.hasParticles(),
                                                     effect.isAmbient(),
                                                     effect.hasIcon()));

          }
        } catch(final Exception ignore) { }
      }

      serialized.applyComponent(component);
    }
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasItemMeta() && item.getItemMeta() instanceof SuspiciousStewMeta;
  }
}