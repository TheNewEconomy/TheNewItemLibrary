package net.tnemc.item.persistent;
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

import net.tnemc.item.JSONHelper;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PersistentDataHolder
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class PersistentDataHolder {

  private final Map<String, PersistentDataType<?>> dataMap = new ConcurrentHashMap<>();

  /**
   * Decodes the {@link JSONHelper JSON object} and sets the values in the current instance.
   *
   * @param json The {@link JSONHelper JSON object} to be decoded
   */
  public void readJSON(final JSONObject json, final ItemPlatform<?, ?> platform) {

    json.forEach((key, value)->{
      final String identifier = String.valueOf(key);

      final Optional<PersistentDataType<?>> data = decode(new JSONHelper((JSONObject)value), platform);
      data.ifPresent(dataentry->dataMap.put(identifier, dataentry));
    });
  }

  /**
   * Convert a PersistentDataHolder object to a JSONObject.
   *
   * @return The JSONObject representation of the PersistentDataHolder object.
   */
  public JSONObject toJSON() {

    final JSONObject persistentData = new JSONObject();
    for(final Map.Entry<String, PersistentDataType<?>> entry : dataMap.entrySet()) {
      persistentData.put(entry.getKey(), entry.getValue().toJSON());
    }

    return persistentData;
  }

  /**
   * Decodes the provided JSON object and returns an Optional containing the decoded
   * PersistentDataType.
   *
   * @param json The JSONHelper object to be decoded
   *
   * @return An Optional containing the decoded PersistentDataType if it exists, otherwise an empty
   * Optional
   */
  public Optional<PersistentDataType<?>> decode(final JSONHelper json, final ItemPlatform<?, ?> platform) {

    final String type = json.getString("type");
    final String namespace = json.getString("namespace");
    final String key = json.getString("key");

    final Class<? extends PersistentDataType<?>> dataClass = platform.getClasses().get(type);
    if(dataClass != null) {

      try {
        final MethodType constructorType = MethodType.methodType(void.class, String.class, String.class);
        final MethodHandle constructorHandle = MethodHandles.lookup().findConstructor(dataClass, constructorType);

        final PersistentDataType<?> persistentData = (PersistentDataType<?>)constructorHandle.invoke(namespace, key);
        persistentData.readJSON(json);

        return Optional.of(persistentData);

      } catch(final Throwable ignore) {

        return Optional.empty();
      }
    }
    return Optional.empty();
  }

  public Map<String, PersistentDataType<?>> getData() {

    return dataMap;
  }
}