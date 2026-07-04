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
import io.papermc.paper.datacomponent.item.WrittenBookContent;
import io.papermc.paper.text.Filtered;
import net.kyori.adventure.text.Component;
import net.tnemc.item.component.impl.WrittenBookContentComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * PaperWrittenBookContentComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperWrittenBookContentComponent extends WrittenBookContentComponent<PaperItemStack, ItemStack> {

  public PaperWrittenBookContentComponent() {

  }

  public PaperWrittenBookContentComponent(final String title,
                                          final String author,
                                          final int generation,
                                          final boolean resolved,
                                          final List<Component> pages) {

    super(title, author, generation, resolved, pages);
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

    final Optional<PaperWrittenBookContentComponent> componentOptional = serialized.component(identifier());
    if (componentOptional.isEmpty()) {
      return item;
    }

    final PaperWrittenBookContentComponent component = componentOptional.get();
    final WrittenBookContent content = WrittenBookContent.writtenBookContent(component.title(), component.author())
                                                    .generation(component.generation())
                                                    .resolved(component.resolved())
                                                    .addPages(component.pages()).build();

    item.setData(DataComponentTypes.WRITTEN_BOOK_CONTENT, content);
    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final WrittenBookContent content = item.getData(DataComponentTypes.WRITTEN_BOOK_CONTENT);
    if(content == null) {
      return serialized;
    }

    final PaperWrittenBookContentComponent component = (serialized.paperComponent(identifier()) instanceof final WrittenBookContentComponent<?, ?> getComponent)?
                                                       (PaperWrittenBookContentComponent)getComponent : new PaperWrittenBookContentComponent();

    component.title(content.title().raw());
    component.author(content.author());
    component.generation(content.generation());
    component.resolved(content.resolved());

    for(final Filtered<Component> page : content.pages()) {
      component.pages.add(page.raw());
    }

    serialized.applyComponent(component);
    return serialized;
  }
}
