package net.tnemc.item;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Optional;

public class SerialItem {

  private AbstractItemStack stack;

  public SerialItem(AbstractItemStack stack) {
    this.stack = stack;
  }

  public SerialItem() {
  }

  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("slot", stack.slot());
    json.put("material", stack.material());
    json.put("amount", stack.amount());
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

    if(stack.data() != null) {
      json.put("data", stack.data().toJSON());
    }
    return json;
  }

  public String serialize() {
    return toJSON().toJSONString();
  }

  public static Optional<SerialItem> unserialize(String serialized) throws ParseException {
    return new SerialItem().parse((JSONObject)new JSONParser().parse(serialized));
  }

  public static Optional<SerialItem> unserialize(JSONObject json) throws ParseException {
    return new SerialItem().parse(json);
  }

  public Optional<SerialItem> parse(JSONObject json) {
    return Optional.empty();
  }
}