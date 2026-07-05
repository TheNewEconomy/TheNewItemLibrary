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

import io.papermc.paper.block.BlockPredicate;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAdventurePredicate;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.impl.CanPlaceOnComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PaperCanPlayerOnComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperCanPlaceOnComponent extends CanPlaceOnComponent<PaperItemStack, ItemStack> {

  public PaperCanPlaceOnComponent() {

  }

  public PaperCanPlaceOnComponent(final List<net.tnemc.item.component.helper.BlockPredicate> predicates) {

    super(predicates);
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

    final Optional<PaperCanPlaceOnComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    item.setData(DataComponentTypes.CAN_PLACE_ON, ItemAdventurePredicate.itemAdventurePredicate(
            componentOptional.get().predicates().stream().map(this::paperPredicate).toList()));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final ItemAdventurePredicate adventurePredicate = item.getData(DataComponentTypes.CAN_PLACE_ON);
    if(adventurePredicate == null) {
      return serialized;
    }

    final PaperCanPlaceOnComponent component = (serialized.paperComponent(identifier()) instanceof final CanPlaceOnComponent<?, ?> getComponent)?
                                               (PaperCanPlaceOnComponent)getComponent : new PaperCanPlaceOnComponent();

    component.predicates().clear();

    for(final BlockPredicate predicate : adventurePredicate.predicates()) {
      component.predicates().add(tnilPredicate(predicate));
    }

    serialized.applyComponent(component);
    return serialized;
  }

  private BlockPredicate paperPredicate(final net.tnemc.item.component.helper.BlockPredicate predicate) {

    final List<TypedKey<BlockType>> keys = new ArrayList<>();
    final JSONObject json = predicate.toJSON();
    final JSONArray blocks = (JSONArray)json.get("blocks");

    if(blocks != null) {
      for(final Object block : blocks) {
        keys.add(TypedKey.create(RegistryKey.BLOCK, Key.key(block.toString())));
      }
    }

    return BlockPredicate.predicate()
            .blocks(RegistrySet.keySet(RegistryKey.BLOCK, keys.toArray(TypedKey[]::new)))
            .build();
  }

  private net.tnemc.item.component.helper.BlockPredicate tnilPredicate(final BlockPredicate predicate) {

    final JSONObject json = new JSONObject();
    final JSONArray blocks = new JSONArray();

    if(predicate.blocks() != null) {
      predicate.blocks().values().forEach(key->blocks.add(key.key().asString()));
    }

    json.put("blocks", blocks);
    json.put("state", new JSONObject());

    final net.tnemc.item.component.helper.BlockPredicate tnilPredicate = new net.tnemc.item.component.helper.BlockPredicate();
    tnilPredicate.readJSON(new JSONHelper(json));

    return tnilPredicate;
  }
}