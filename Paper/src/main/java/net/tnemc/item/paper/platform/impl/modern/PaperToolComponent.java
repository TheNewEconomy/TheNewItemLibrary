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
import io.papermc.paper.datacomponent.item.Tool;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.util.TriState;
import net.tnemc.item.component.helper.ToolRule;
import net.tnemc.item.component.impl.ToolComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperToolComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperToolComponent extends ToolComponent<PaperItemStack, ItemStack> {

  public PaperToolComponent() {

    super(0);
  }

  public PaperToolComponent(final float defaultSpeed, final int blockDamage, final boolean canDestroyBlocksCreative) {

    super(defaultSpeed, blockDamage, canDestroyBlocksCreative);
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

    final Optional<PaperToolComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final PaperToolComponent component = componentOptional.get();
    final Tool.Builder builder = Tool.tool()
            .defaultMiningSpeed(component.defaultSpeed())
            .damagePerBlock(component.blockDamage())
            .canDestroyBlocksInCreative(component.canDestroyBlocksCreative());

    for(final ToolRule rule : component.rules()) {
      builder.addRule(rule(rule));
    }

    item.setData(DataComponentTypes.TOOL, builder);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final Tool tool = item.getData(DataComponentTypes.TOOL);
    if(tool == null) {
      return serialized;
    }

    final PaperToolComponent component = (serialized.paperComponent(identifier()) instanceof final ToolComponent<?, ?> getComponent)?
                                         (PaperToolComponent)getComponent : new PaperToolComponent();

    component.defaultSpeed(tool.defaultMiningSpeed());
    component.blockDamage(tool.damagePerBlock());
    component.canDestroyBlocksCreative(tool.canDestroyBlocksInCreative());
    component.rules().clear();

    for(final Tool.Rule rule : tool.rules()) {
      final ToolRule tnilRule = new ToolRule();
      tnilRule.setSpeed(rule.speed() == null? component.defaultSpeed() : rule.speed());
      tnilRule.setDrops(rule.correctForDrops() == TriState.TRUE);

      if(rule.blocks() != null) {
        rule.blocks().values().forEach(key->tnilRule.getMaterials().add(key.key().asString()));
      }

      component.rules().add(tnilRule);
    }

    serialized.applyComponent(component);
    return serialized;
  }

  private Tool.Rule rule(final ToolRule rule) {

    final TypedKey<BlockType>[] keys = rule.getMaterials().stream()
            .map(material->TypedKey.create(RegistryKey.BLOCK, Key.key(material)))
            .toArray(TypedKey[]::new);

    return Tool.rule(RegistrySet.keySet(RegistryKey.BLOCK, keys),
                     rule.getSpeed(),
                     rule.isDrops()? TriState.TRUE : TriState.FALSE);
  }
}