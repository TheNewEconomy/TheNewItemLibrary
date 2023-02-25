package net.tnemc.item;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Optional;

public class SerialItem<T> {

  private AbstractItemStack<T> stack;

  public SerialItem(AbstractItemStack<T> stack) {
    this.stack = stack;
  }

  public SerialItem() {
  }

  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("slot", stack.slot());
    json.put("material", stack.material());
    json.put("amount", stack.amount());
    json.put("unbreakable", stack.unbreakable());
    if(stack.display() != null && !stack.display().equalsIgnoreCase("")) json.put("display", stack.display());
    json.put("damage", stack.damage());
    if(stack.modelData() != -1) json.put("modelData", stack.modelData());
    if(stack.lore() != null && stack.lore().size() > 0) json.put("lore", String.join(",", stack.lore()));

    if(stack.flags() != null && stack.flags().size() > 0) json.put("flags", String.join(",", stack.flags()));

    JSONObject object = new JSONObject();
    stack.enchantments().forEach(object::put);
    json.put("enchantments", object);

    if(stack.attributes().size() > 0) {
      JSONObject attr = new JSONObject();

      stack.attributes().forEach((name, modifier)->{
        JSONObject mod = new JSONObject();

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

  public AbstractItemStack<T> getStack() {
    return stack;
  }

  public String serialize() {
    return toJSON().toJSONString();
  }

  public static <T extends AbstractItemStack<T>> SerialItem<T> of(T stack) {
    return new SerialItem<>(stack);
  }

  public static <T> Optional<SerialItem<T>> unserialize(String serialized) throws ParseException {
    return new SerialItem().parse((JSONObject)new JSONParser().parse(serialized));
  }

  public static <T> Optional<SerialItem<T>> unserialize(JSONObject json) throws ParseException {
    return new SerialItem().parse(json);
  }

  public Optional<SerialItem<T>> parse(JSONObject json) {
    return Optional.empty();
  }
}