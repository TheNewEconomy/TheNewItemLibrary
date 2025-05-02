package net.tnemc.item.paper.platform.impl.modern;
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

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.tnemc.item.component.impl.ModelDataComponent;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * BukkitModelData
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PaperModelDataComponent extends ModelDataComponent<PaperItemStack, ItemStack> {

  public PaperModelDataComponent() {

  }

  public PaperModelDataComponent(final List<String> colours, final List<Float> floats, final List<Boolean> flags, final List<String> strings) {

    super(colours, floats, flags, strings);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return true;
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final PaperItemStack serialized, final ItemStack item) {

    final Optional<PaperModelDataComponent> componentOptional = serialized.component(identifier());
    if(componentOptional.isEmpty()) {
      return item;
    }

    final CustomModelData.Builder builder = CustomModelData.customModelData();

    builder.addFlags(this.flags);
    builder.addFloats(this.floats);
    builder.addStrings(this.strings);

    final List<Color> colorList = new LinkedList<>();
    for(final String colorStr : this.colours) {

      try {

        colorList.add(Color.fromARGB(Integer.parseInt(colorStr)));

      } catch(final Exception ignore){}
    }
    builder.addColors(colorList);

    item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, builder);

    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   * @since 0.2.0.0
   */
  @Override
  public PaperItemStack serialize(final ItemStack item, final PaperItemStack serialized) {

    final CustomModelData color = item.getData(DataComponentTypes.CUSTOM_MODEL_DATA);
    if(color == null) {
      return serialized;
    }

    this.flags.addAll(color.flags());
    this.floats.addAll(color.floats());
    this.strings.addAll(color.strings());

    for(final Color colorObj : color.colors()) {
      this.colours.add(String.valueOf(colorObj.asARGB()));
    }

    serialized.applyComponent(this);
    return serialized;
  }
}