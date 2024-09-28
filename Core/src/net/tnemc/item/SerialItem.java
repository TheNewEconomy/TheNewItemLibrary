package net.tnemc.item;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SerialItem<T> {

  private AbstractItemStack<T> stack;

  public SerialItem(final AbstractItemStack<T> stack) {

    this.stack = stack;
  }

  public JSONObject toJSON() {

    final JSONObject json = new JSONObject();
    json.put("slot", stack.slot());
    json.put("material", stack.material());
    json.put("amount", stack.amount());
    json.put("unbreakable", stack.unbreakable());
    if(stack.display() != null && !Component.EQUALS.test(stack.display(), Component.empty())) {
      json.put("display", JSONComponentSerializer.json().serialize(stack.display()));
    }
    json.put("damage", stack.damage());
    if(stack.modelData() != -1) json.put("modelData", stack.modelData());
    if(stack.lore() != null && !stack.lore().isEmpty()) {

      final LinkedList<String> str = new LinkedList<>();
      for(final Component comp : stack.lore()) {
        str.add(JSONComponentSerializer.json().serialize(comp));
      }

      json.put("lore", String.join(",", str));
    }

    if(stack.flags() != null && !stack.flags().isEmpty()) {
      json.put("flags", String.join(",", stack.flags()));
    }

    final JSONObject object = new JSONObject();
    stack.enchantments().forEach(object::put);
    json.put("enchantments", object);

    if(!stack.attributes().isEmpty()) {
      final JSONObject attr = new JSONObject();

      stack.attributes().forEach((name, modifier)->{
        final JSONObject mod = new JSONObject();

        mod.put("id", modifier.getIdentifier().toString());
        mod.put("name", modifier.getName());
        mod.put("amount", modifier.getAmount());
        mod.put("operation", modifier.getOperation().name());
        if(modifier.getSlot() != null) mod.put("slot", modifier.getSlot().name());

        attr.put(name, mod);
      });
      json.put("attributes", attr);
    }

    if(stack.data().isPresent()) {
      json.put("data", stack.data().get().toJSON());
    }
    return json;
  }

  public boolean jsonEquals(final SerialItem<T> serial) {

    return serialize().equals(serial.serialize());
  }

  public AbstractItemStack<T> getStack() {

    return stack;
  }

  public String serialize() {

    return toJSON().toJSONString();
  }

  public static <T extends AbstractItemStack<T>> SerialItem<T> of(final T stack) {

    return new SerialItem<>(stack);
  }
}