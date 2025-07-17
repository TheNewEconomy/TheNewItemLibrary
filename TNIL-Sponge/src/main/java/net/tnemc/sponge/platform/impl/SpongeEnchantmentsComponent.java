package net.tnemc.sponge.platform.impl;/*
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

import net.tnemc.item.component.impl.EnchantmentsComponent;
import net.tnemc.sponge.SpongeItemStack;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * SpongeEnchantmentsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class SpongeEnchantmentsComponent extends EnchantmentsComponent<SpongeItemStack, ItemStack> {

  public SpongeEnchantmentsComponent() {

  }

  public SpongeEnchantmentsComponent(final Map<String, Integer> levels) {

    super(levels);
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

    try {

      final Key<ListValue<Enchantment>> enchantments = Keys.APPLIED_ENCHANTMENTS;
      return true;
    } catch(final NoSuchElementException ignore) {

      return false;
    }
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

    return item.supports(Keys.APPLIED_ENCHANTMENTS);
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
  public ItemStack apply(final SpongeItemStack serialized, final ItemStack item) {

    final Optional<SpongeEnchantmentsComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      final List<Enchantment> enchants = new ArrayList<>();
      for(final Map.Entry<String, Integer> entry : component.levels.entrySet()) {

        final Optional<EnchantmentType> type = EnchantmentTypes.registry().findValue(ResourceKey.resolve(entry.getKey()));
        type.ifPresent(enchantmentType->{

          enchants.add(Enchantment.of(enchantmentType, entry.getValue()));
        });
      }

      item.offer(Keys.APPLIED_ENCHANTMENTS, enchants);
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
  public SpongeItemStack serialize(final ItemStack item, final SpongeItemStack serialized) {

    final Optional<List<Enchantment>> keyOptional = item.get(Keys.APPLIED_ENCHANTMENTS);
    keyOptional.ifPresent((key->{

      final SpongeEnchantmentsComponent component = (serialized.spongeComponent(identifier()) instanceof final EnchantmentsComponent<?, ?> getComponent)?
                                                    (SpongeEnchantmentsComponent)getComponent : new SpongeEnchantmentsComponent();


      for(final Enchantment enchantment : key) {

        component.levels.put(enchantment.type().key(enchantment.type().registryType()).asString(), enchantment.level());
      }
    }));
    return serialized;
  }
}
