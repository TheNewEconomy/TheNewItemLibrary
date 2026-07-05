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
import net.tnemc.item.component.impl.InstrumentComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.MusicInstrument;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * PaperInstrumentComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperInstrumentComponent extends InstrumentComponent<PaperItemStack, ItemStack> {

  public PaperInstrumentComponent() {

  }

  public PaperInstrumentComponent(final String soundEvent, final int useDuration, final int range) {

    super(soundEvent, useDuration, range);
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

    final Optional<PaperInstrumentComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final MusicInstrument instrument = PaperItemPlatform.instance().converter().convert(componentOptional.get().soundEvent(), MusicInstrument.class);
    item.setData(DataComponentTypes.INSTRUMENT, instrument);

    return item;
  }

  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final MusicInstrument instrument = item.getData(DataComponentTypes.INSTRUMENT);
    if(instrument == null) {
      return serialized;
    }

    final PaperInstrumentComponent component = (serialized.paperComponent(identifier()) instanceof final InstrumentComponent<?, ?> getComponent)?
                                               (PaperInstrumentComponent)getComponent : new PaperInstrumentComponent();

    component.soundEvent(PaperItemPlatform.instance().converter().convert(instrument, String.class));
    component.useDuration((int)instrument.getDuration());
    component.range((int)instrument.getRange());

    serialized.applyComponent(component);
    return serialized;
  }
}
