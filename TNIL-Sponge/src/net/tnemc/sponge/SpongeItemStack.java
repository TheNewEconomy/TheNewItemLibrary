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
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Color;

import java.util.*;
import java.util.List;

public class SpongeItemStack implements AbstractItemStack<ItemStack> {
    private final Map<String, SerialItemData<ItemStack>> data = new HashMap<>();

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

    private int color = -1;

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

        this.stack = stack.stack;

        return this;
    }

    @Override
    public SpongeItemStack of(ItemStack locale) {
        this.stack = locale;

        this.resource = stack.type().toString();
        this.amount = stack.quantity();

        final Optional<Component> display = stack.get(Keys.CUSTOM_NAME);
        display.ifPresent(component -> this.display = component.toString());

        final Optional<List<Enchantment>> enchants = stack.get(Keys.APPLIED_ENCHANTMENTS);

        if(enchants.isPresent()) {
            for(Enchantment enchant : enchants.get()) {
                enchantments.put(enchant.type().key(RegistryTypes.ENCHANTMENT_TYPE).formatted(), enchant.level());
            }
        }

        final Optional<Color> color = stack.get(Keys.COLOR);
        color.ifPresent(color1 -> this.color = color1.rgb());

        data.putAll(ParsingUtil.parseMeta(locale));

        return this;
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
        this.data.put(data.getClass().getSimpleName(), data);
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
        return Optional.ofNullable(data.get(0));
    }

    public Map<String, SerialItemData<ItemStack>> allData() {
        return data;
    }



    /**
     * Returns true if the provided item is similar to this.
     * An item is similar if the basic information is the same, except for the amount.
     * What this includes:
     * - material
     * - display
     * - modelData
     * - flags
     * - lore
     * - attributes
     * - enchantments
     * <p>
     * What this does not include:
     * - Item Data.
     *
     * @param compare The stack to compare.
     *
     * @return True if the two are similar, otherwise false.
     */
    @Override
    public boolean similar(AbstractItemStack<? extends ItemStack> compare) {
        if(stack == null || !(compare instanceof SpongeItemStack)) return false;
        //return stack.isSimilar(compare.locale());
        return similarStack((SpongeItemStack) compare);
    }

    public boolean similarStack(SpongeItemStack stack) {

        if(!resource.equals(stack.resource)) return false;
        if(!display.equals(stack.display)) return false;
        if(!Objects.equals(damage, stack.damage)) return false;
        if(!Objects.equals(customModelData, stack.customModelData)) return false;
        if(unbreakable != stack.unbreakable) return false;

        if(!listsEquals(lore, stack.lore)) return false;
        if(!listsEquals(flags, stack.flags)) return false;
        if(!attributes.equals(stack.attributes)) return false;
        if(!enchantments.equals(stack.enchantments)) return false;
        if(color != stack.color) return false;
        if(!setsEquals(data.keySet(), stack.data.keySet())) return false;

        for(Map.Entry<String, SerialItemData<ItemStack>> entry : data.entrySet()) {
            final SerialItemData<ItemStack> compare = stack.data.get(entry.getKey());
            if(compare == null || !entry.getValue().similar(compare)) return false;
        }

        return true;
    }

    @Override
    public ItemStack locale() {
        if(stack == null) {
            stack = ItemStack.builder().itemType((ItemType) ItemTypes.registry().value(fromString())).quantity(amount).build();

            if(!display.equalsIgnoreCase("")) {
                stack.offer(Keys.CUSTOM_NAME, Component.text(display));
            }

            if(customModelData > 0) {
                stack.offer(Keys.CUSTOM_MODEL_DATA, customModelData);
            }

            if(color != -1) {
                stack.offer(Keys.COLOR, Color.ofRgb(color));
            }

            final List<Enchantment> enchants = new ArrayList<>();
            for(final Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                enchants.add(Enchantment.of((EnchantmentType) EnchantmentTypes.registry().value(ResourceKey.resolve(entry.getKey())), entry.getValue()));
            }
            stack.offer(Keys.APPLIED_ENCHANTMENTS, enchants);

            for(SerialItemData<ItemStack> serialData : data.values()) {
                serialData.apply(stack);
            }
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
