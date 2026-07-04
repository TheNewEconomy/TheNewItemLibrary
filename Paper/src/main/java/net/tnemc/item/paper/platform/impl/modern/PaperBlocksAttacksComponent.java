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
import io.papermc.paper.datacomponent.item.BlocksAttacks;
import io.papermc.paper.datacomponent.item.blocksattacks.ItemDamageFunction;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.component.helper.DamageReduction;
import net.tnemc.item.component.helper.ItemDamage;
import net.tnemc.item.component.impl.BlocksAttacksComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperBlocksAttacksComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperBlocksAttacksComponent extends BlocksAttacksComponent<PaperItemStack, ItemStack> {

  public PaperBlocksAttacksComponent() {

  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneEleven(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperBlocksAttacksComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperBlocksAttacksComponent component = componentOptional.get();
    final BlocksAttacks.Builder builder = BlocksAttacks.blocksAttacks()
            .blockDelaySeconds(component.blockDelay())
            .disableCooldownScale(component.disableCooldownScale());

    for(final DamageReduction reduction : component.reductions()) {
      builder.addDamageReduction(reduction(reduction));
    }

    if(component.itemDamage() != null) {
      builder.itemDamage(ItemDamageFunction.itemDamageFunction().threshold(component.itemDamage().threshold())
                                 .base(component.itemDamage().base())
                                 .factor(component.itemDamage().factor()).build());
    }

    if(!component.bypassedBy().isEmpty()) {
      final TypedKey<DamageType>[] keys = component.bypassedBy().stream()
              .map(type->TypedKey.create(RegistryKey.DAMAGE_TYPE, Key.key(type)))
              .toArray(TypedKey[]::new);
      builder.bypassedBy(RegistrySet.keySet(RegistryKey.DAMAGE_TYPE, keys));
    }

    if(component.blockSound() != null && !component.blockSound().isEmpty()) {
      builder.blockSound(Key.key(component.blockSound()));
    }

    if(component.disableSound() != null && !component.disableSound().isEmpty()) {
      builder.disableSound(Key.key(component.disableSound()));
    }

    item.setData(DataComponentTypes.BLOCKS_ATTACKS, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final BlocksAttacks blocksAttacks = item.getData(DataComponentTypes.BLOCKS_ATTACKS);
    if(blocksAttacks == null) {
      return serialized;
    }

    final PaperBlocksAttacksComponent component = (serialized.paperComponent(identifier()) instanceof final BlocksAttacksComponent<?, ?> getComponent)?
                                                  (PaperBlocksAttacksComponent)getComponent : new PaperBlocksAttacksComponent();

    component.reductions().clear();
    blocksAttacks.damageReductions().forEach(reduction->component.reductions().add(
            new DamageReduction(reduction.type().values().stream()
                                        .map(type -> type.key().asString())
                                        .toList(),
                                reduction.base(),
                                reduction.factor(),
                                reduction.horizontalBlockingAngle())));

    if(blocksAttacks.itemDamage() != null) {
      component.itemDamage(new ItemDamage(blocksAttacks.itemDamage().threshold(),
                                          blocksAttacks.itemDamage().base(),
                                          blocksAttacks.itemDamage().factor()));
    }

    component.bypassedBy().clear();
    if(blocksAttacks.bypassedBy() != null) {
      blocksAttacks.bypassedBy().values().forEach(key->component.bypassedBy().add(key.key().asString()));
    }

    component.blockDelay(blocksAttacks.blockDelaySeconds());
    component.disableCooldownScale(blocksAttacks.disableCooldownScale());

    if(blocksAttacks.blockSound() != null) {
      component.blockSound(blocksAttacks.blockSound().asString());
    }

    if(blocksAttacks.disableSound() != null) {
      component.disableSound(blocksAttacks.disableSound().asString());
    }

    serialized.applyComponent(component);
    return serialized;
  }

  private io.papermc.paper.datacomponent.item.blocksattacks.DamageReduction reduction(final DamageReduction reduction) {

    return io.papermc.paper.datacomponent.item.blocksattacks.DamageReduction.damageReduction()
            .type(RegistrySet.keySet(RegistryKey.DAMAGE_TYPE,
                                     reduction.types().stream().map(id -> TypedKey.create(RegistryKey.DAMAGE_TYPE, Key.key(id))).toList())
                 )
            .base(reduction.base())
            .factor(reduction.factor())
            .horizontalBlockingAngle(reduction.horizontalBlockingAngle())
            .build();
  }
}