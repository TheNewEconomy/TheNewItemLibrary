package net.tnemc.item.paper.platform.impl;
/*
 * The New Economy
 * Copyright (C) 2025 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.paper.platform.PaperItemPlatform;
import net.tnemc.item.providers.VersionUtil;

/**
 * PaperSerialComponent
 *
 * @author creatorfromhell
 * @since 1.0.0.0
 */
public interface PaperSerialComponent<I extends AbstractItemStack<T>, T> extends SerialComponent<I, T> {

  /**
   * Checks if the serial component has modern features enabled.
   *
   * @return true if modern features are enabled, false otherwise
   */
  default boolean hasModern() {

    return true;
  }
  /**
   * @param version the version being used when this deserializer is called.
   *
   * @return true if this deserializer is enabled for the version, otherwise false
   * @since 0.2.0.0
   */
  default boolean modernEnabled(final String version) {

    return VersionUtil.isOneTwentyOneFour(version);
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   * @since 0.2.0.0
   */
  I serializeModern(final T item, I serialized);

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   * @since 0.2.0.0
   */
  I serializeLegacy(final T item, I serialized);

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  T applyModern(final I serialized, T item);

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   * @since 0.2.0.0
   */
  T applyLegacy(final I serialized, T item);

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   *
   * @since 0.2.0.0
   */
  @Override
  default I serialize(final T item, final I serialized) {
    if(hasModern() && modernEnabled(PaperItemPlatform.instance().version())) {
      return serializeModern(item, serialized);
    }
    return serializeLegacy(item, serialized);
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
  default T apply(final I serialized, final T item) {
    if(hasModern() && modernEnabled(PaperItemPlatform.instance().version())) {
      return applyModern(serialized, item);
    }
    return applyLegacy(serialized, item);
  }
}