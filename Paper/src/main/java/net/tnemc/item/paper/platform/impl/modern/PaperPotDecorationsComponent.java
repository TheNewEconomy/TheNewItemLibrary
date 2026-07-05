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
import io.papermc.paper.datacomponent.item.PotDecorations;
import net.tnemc.item.component.impl.PotDecorationsComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import java.util.Optional;

/**
 * PaperPotDecorationsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperPotDecorationsComponent extends PotDecorationsComponent<PaperItemStack, ItemStack> {

  public PaperPotDecorationsComponent() {

  }

  public PaperPotDecorationsComponent(final String... decorations) {

    super(decorations);
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

    final Optional<PaperPotDecorationsComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final Material[] materials = new Material[4];
    final String[] decorations = componentOptional.get().decorations();

    for(int i = 0; i < decorations.length; i++) {
      if(decorations[i] != null) {
        materials[i] = PaperItemPlatform.instance().converter().convert(decorations[i], Material.class);
      }
    }

    item.setData(DataComponentTypes.POT_DECORATIONS, PotDecorations.potDecorations(
            (componentOptional.get().south() == null)? null
                                                     : PaperItemPlatform.instance().converter().convert(componentOptional.get().south(), ItemType.class),
            (componentOptional.get().west() == null)? null
                                                     : PaperItemPlatform.instance().converter().convert(componentOptional.get().west(), ItemType.class),
            (componentOptional.get().east() == null)? null
                                                     : PaperItemPlatform.instance().converter().convert(componentOptional.get().east(), ItemType.class),
            (componentOptional.get().north() == null)? null
                                                     : PaperItemPlatform.instance().converter().convert(componentOptional.get().north(), ItemType.class))
                );
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final PotDecorations decorations = item.getData(DataComponentTypes.POT_DECORATIONS);
    if(decorations == null) {
      return serialized;
    }

    final PaperPotDecorationsComponent component = (serialized.paperComponent(identifier()) instanceof final PotDecorationsComponent<?, ?> getComponent)?
                                                   (PaperPotDecorationsComponent)getComponent : new PaperPotDecorationsComponent();

    //TODO: Convert ItemType.
    component.south((decorations.back() == null)? null : PaperItemPlatform.instance().converter().convert(decorations.back(), String.class));
    component.west((decorations.left() == null)? null : PaperItemPlatform.instance().converter().convert(decorations.left(), String.class));
    component.east((decorations.right() == null)? null : PaperItemPlatform.instance().converter().convert(decorations.right(), String.class));
    component.north((decorations.front() == null)? null : PaperItemPlatform.instance().converter().convert(decorations.front(), String.class));

    serialized.applyComponent(component);
    return serialized;
  }
}