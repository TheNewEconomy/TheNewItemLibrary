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
import io.papermc.paper.datacomponent.item.SulfurCubeContent;
import net.tnemc.item.component.impl.SingleItemContainerComponent;
import net.tnemc.item.component.impl.SulfurCubeContentComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperSulfurCubeContentComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperSulfurCubeContentComponent extends SulfurCubeContentComponent<PaperItemStack, ItemStack> {

  public PaperSulfurCubeContentComponent() {

  }

  public PaperSulfurCubeContentComponent(final PaperItemStack item) {

    super(item);
  }

  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isTwentySixTwo(version);
  }

  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperSulfurCubeContentComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty() || componentOptional.get().item() == null) {
      return item;
    }

    item.setData(DataComponentTypes.SULFUR_CUBE_CONTENT, SulfurCubeContent.sulfurCubeContent(componentOptional.get().item().provider().locale(serialized)));

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final SulfurCubeContent content = item.getData(DataComponentTypes.SULFUR_CUBE_CONTENT);
    if(content == null) {
      return serialized;
    }

    final PaperSulfurCubeContentComponent component = (serialized.paperComponent(identifier()) instanceof final SingleItemContainerComponent<?, ?> getComponent)?
                                                      (PaperSulfurCubeContentComponent)getComponent : new PaperSulfurCubeContentComponent();

    final PaperItemStack contentStack = new PaperItemStack().of(content.absorbedItem());
    PaperItemPlatform.instance().providerApplies(contentStack, content.absorbedItem());

    component.item(contentStack);

    serialized.applyComponent(component);
    return serialized;
  }
}