package net.tnemc.item.key;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

/**
 * Platform-neutral namespaced identifier for an item.
 *
 * @param namespace namespace, e.g. minecraft or itemsadder
 * @param value item value/path, e.g. diamond_sword
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public record ItemKey(@NotNull String namespace, @NotNull String value) {

  public static final String DEFAULT_NAMESPACE = "minecraft";

  public ItemKey {

    namespace = normalize(namespace, "namespace");
    value = normalize(value, "value");
  }

  public static ItemKey of(@NotNull final String key) {

    Objects.requireNonNull(key, "key");
    final int separator = key.indexOf(':');
    if(separator < 0) {

      return new ItemKey(DEFAULT_NAMESPACE, key);
    }
    return new ItemKey(key.substring(0, separator), key.substring(separator + 1));
  }

  private static String normalize(final String input, final String name) {

    Objects.requireNonNull(input, name);
    final String normalized = input.trim().toLowerCase(Locale.ROOT);
    if(normalized.isEmpty()) {

      throw new IllegalArgumentException(name + " cannot be empty");
    }
    return normalized;
  }

  @Override
  public String toString() {
    return namespace + ':' + value;
  }
}
