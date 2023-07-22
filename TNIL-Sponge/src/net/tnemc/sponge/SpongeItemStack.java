package net.tnemc.sponge;

import net.kyori.adventure.text.Component;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.attribute.AttributeModifier;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.*;

public class SpongeItemStack implements AbstractItemStack<ItemStack> {

    private final List<String> flags = new ArrayList<>();
    private final Map<String, AttributeModifier> attributes = new HashMap<>();
    private final Map<String, Integer> enchantments = new HashMap<>();
    private final List<String> lore = new ArrayList<>();
    private String resource = "";

    private int slot = 0;
    private Integer amount = 1;
    private String display = "";
    private short damage = 0;
    private int customModelData = -1;
    private boolean unbreakable = false;
    private SerialItemData<ItemStack> data;

    //our locale stack
    private ItemStack stack;

    @Override
    public SpongeItemStack of(String material, int amount) {
        this.resource = material;
         this.amount = amount;
        return this;
    }

    @Override
    public SpongeItemStack of(SerialItem<ItemStack> serialItem) {

        final SpongeItemStack stack = (SpongeItemStack)serialItem.getStack();

        flags.addAll(stack.flags);
        attributes.putAll(stack.attributes);
        enchantments.putAll(stack.enchantments);
        lore.addAll(stack.lore);

        slot = stack.slot;
        resource = stack.resource;
        amount = stack.amount;
        display = stack.display;
        damage = stack.damage;
        customModelData = stack.customModelData;
        unbreakable = stack.unbreakable;
        data = stack.data;
        this.stack = stack.stack;

        return this;
    }

    @Override
    public SpongeItemStack of(ItemStack locale) {
        this.stack = locale;

        this.resource = stack.type().toString();
        this.amount = stack.quantity();

        final Optional<Component> display = stack.get(Keys.DISPLAY_NAME);
        display.ifPresent(component -> this.display = component.toString());



        return null;
    }

    @Override
    public SpongeItemStack of(JSONObject json) {

        try {
            final Optional<SerialItem<ItemStack>> serialStack = SerialItem.unserialize(json);

            if(serialStack.isPresent()) {
                return of(serialStack.get());
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public SpongeItemStack flags(List<String> flags) {
        this.flags.clear();
        this.flags.addAll(flags);
        return this;
    }

    @Override
    public SpongeItemStack lore(List<String> lore) {
        this.lore.clear();
        this.lore.addAll(lore);
        return this;
    }

    @Override
    public SpongeItemStack attribute(String name, SerialAttribute attribute) {
        return this;
    }

    @Override
    public SpongeItemStack attribute(Map<String, SerialAttribute> attributes) {
        return this;
    }

    @Override
    public SpongeItemStack enchant(String enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    @Override
    public SpongeItemStack enchant(Map<String, Integer> enchantments) {
        this.enchantments.clear();
        this.enchantments.putAll(enchantments);
        return this;
    }

    @Override
    public SpongeItemStack enchant(List<String> enchantments) {
        this.enchantments.clear();
        for(String str : enchantments) {
            this.enchantments.put(str, 1);
        }
        return this;
    }

    @Override
    public SpongeItemStack material(String material) {
        this.resource = material;
        return this;
    }

    @Override
    public SpongeItemStack amount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public SpongeItemStack slot(int slot) {
        this.slot = slot;
        return this;
    }

    @Override
    public SpongeItemStack display(String display) {
        this.display = display;
        return this;
    }

    @Override
    public SpongeItemStack damage(short damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public SpongeItemStack modelData(int modelData) {
        this.customModelData = modelData;
        return this;
    }

    @Override
    public SpongeItemStack unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    @Override
    public SpongeItemStack applyData(SerialItemData<ItemStack> data) {
        this.data = data;
        return this;
    }

    @Override
    public List<String> flags() {
        return flags;
    }

    @Override
    public List<String> lore() {
        return lore;
    }

    @Override
    public Map<String, SerialAttribute> attributes() {
        final Map<String, SerialAttribute> serialAttributes = new HashMap<>();
        return serialAttributes;
    }

    @Override
    public Map<String, Integer> enchantments() {
        return enchantments;
    }

    @Override
    public String material() {
        return resource;
    }

    @Override
    public int amount() {
        return amount;
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public String display() {
        return display;
    }

    @Override
    public short damage() {
        return damage;
    }

    @Override
    public int modelData() {
        return customModelData;
    }

    @Override
    public boolean unbreakable() {
        return unbreakable;
    }

    @Override
    public Optional<SerialItemData<ItemStack>> data() {
        return Optional.ofNullable(data);
    }

    @Override
    public boolean similar(AbstractItemStack<? extends ItemStack> compare) {
        return false;
    }

    @Override
    public ItemStack locale() {
        if(stack == null) {
            ItemStack.Builder builder = ItemStack.builder().itemType((ItemType) ItemTypes.registry().value(fromString())).quantity(amount);
        }
        return stack;
    }

    private ResourceKey fromString() {
        final String[] split = resource.split("\\:");

        final String namespace = (split.length >= 2)? split[0] : "minecraft";
        final String value = (split.length >= 2)? split[1] : split[0];
        return ResourceKey.of(namespace, value);
    }
}
