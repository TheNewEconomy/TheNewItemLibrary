package net.tnemc.sponge;

import net.kyori.adventure.text.Component;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.providers.SkullProfile;
import net.tnemc.sponge.platform.SpongeItemPlatform;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpongeItemStack implements AbstractItemStack<ItemStack> {
    private final Map<String, SerialItemData<ItemStack>> data = new HashMap<>();
    private final Map<String, SerialComponent<ItemStack>> components = new HashMap<>();

    private final List<String> flags = new ArrayList<>();
    private final Map<String, SerialAttribute> attributes = new HashMap<>();
    private final Map<String, Integer> enchantments = new HashMap<>();
    private final List<Component> lore = new ArrayList<>();
    private String resource = "";

    private SkullProfile profile = null;
    private int slot = 0;
    private Integer maxStack = 64;
    private Integer amount = 1;
    private Component display = Component.empty();
    private short damage = 0;
    private int customModelData = -1;
    private boolean unbreakable = false;
    private boolean hideTooltip = false;
    private boolean fireResistant = false;
    private boolean enchantGlint = false;
    private String rarity = "COMMON";

    private int color = -1;

    //our locale stack
    private boolean dirty = false;
    private boolean debug = false;
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
        components.putAll(stack.components);

        resource = stack.resource;
        amount = stack.amount;
        display = stack.display;
        damage = stack.damage;
        customModelData = stack.customModelData;
        unbreakable = stack.unbreakable;
        hideTooltip = stack.hideTooltip;
        fireResistant = stack.fireResistant;
        enchantGlint = stack.enchantGlint;
        rarity = stack.rarity;

        if(stack.profile != null) {
            this.profile = stack.profile;
        }

        this.stack = stack.stack;

        return this;
    }

    @Override
    public SpongeItemStack of(ItemStack locale) {
        this.stack = locale;

        return SpongeItemPlatform.PLATFORM.deserialize(this.stack, this);
    }

    @Override
    public SpongeItemStack of(JSONObject json) throws ParseException {
        final Optional<SerialItem<ItemStack>> serialStack = SerialItem.unserialize(json);

        if(serialStack.isPresent()) {
            return of(serialStack.get());
        }
        return this;
    }

    @Override
    public SpongeItemStack flags(List<String> flags) {
        this.dirty = true;
        this.flags.clear();
        this.flags.addAll(flags);
        return this;
    }

    @Override
    public SpongeItemStack lore(List<Component> lore) {
        this.dirty = true;
        this.lore.clear();
        this.lore.addAll(lore);
        return this;
    }

    @Override
    public SpongeItemStack attribute(String name, SerialAttribute attribute) {
        this.dirty = true;
        this.attributes.put(name, attribute);
        return this;
    }

    @Override
    public SpongeItemStack attribute(Map<String, SerialAttribute> attributes) {
        this.dirty = true;
        this.attributes.clear();
        this.attributes.putAll(attributes);
        return this;
    }

    @Override
    public SpongeItemStack enchant(String enchantment, int level) {
        this.dirty = true;
        enchantments.put(enchantment, level);
        return this;
    }

    @Override
    public SpongeItemStack enchant(Map<String, Integer> enchantments) {
        this.dirty = true;
        this.enchantments.clear();
        this.enchantments.putAll(enchantments);
        return this;
    }

    @Override
    public SpongeItemStack enchant(List<String> enchantments) {
        this.dirty = true;
        this.enchantments.clear();
        for(String str : enchantments) {
            this.enchantments.put(str, 1);
        }
        return this;
    }

    @Override
    public SpongeItemStack material(String material) {
        this.dirty = true;
        this.resource = material;
        return this;
    }

    @Override
    public SpongeItemStack amount(int amount) {
        this.dirty = true;
        this.amount = amount;
        return this;
    }

    @Override
    public SpongeItemStack slot(int slot) {
        this.dirty = true;
        this.slot = slot;
        return this;
    }

    @Override
    public SpongeItemStack display(Component display) {
        this.dirty = true;
        this.display = display;
        return this;
    }

    @Override
    public SpongeItemStack debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    @Override
    public SpongeItemStack damage(short damage) {
        this.dirty = true;
        this.damage = damage;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> profile(SkullProfile profile) {
        this.dirty = true;
        this.profile = profile;
        return this;
    }

    @Override
    public SpongeItemStack modelData(int modelData) {
        this.dirty = true;
        this.customModelData = modelData;
        return this;
    }

    @Override
    public SpongeItemStack unbreakable(boolean unbreakable) {
        this.dirty = true;
        this.unbreakable = unbreakable;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> maxStack(int maxStack) {
        this.dirty = true;
        this.maxStack = maxStack;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> hideTooltip(boolean hideTooltip) {
        this.dirty = true;
        this.hideTooltip = hideTooltip;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> fireResistant(boolean fireResistant) {
        this.dirty = true;
        this.fireResistant = fireResistant;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> enchantGlint(boolean enchantGlint) {
        this.dirty = true;
        this.enchantGlint = enchantGlint;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> rarity(String rarity) {
        this.dirty = true;
        this.rarity = rarity;
        return this;
    }

    @Override
    public SpongeItemStack applyData(SerialItemData<ItemStack> data) {
        this.dirty = true;
        this.data.put(data.getClass().getSimpleName(), data);
        return this;
    }

    @Override
    public List<String> flags() {
        return flags;
    }

    @Override
    public List<Component> lore() {
        return lore;
    }

    @Override
    public Map<String, SerialAttribute> attributes() {
        return attributes;
    }

    @Override
    public Map<String, Integer> enchantments() {
        return enchantments;
    }

    @Override
    public Map<String, SerialComponent<ItemStack>> components() {
        return components;
    }

    @Override
    public String material() {
        return resource;
    }

    @Override
    public int amount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;

        if(stack != null) {
            stack.setQuantity(amount);
        }
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public Component display() {
        return display;
    }

    @Override
    public short damage() {
        return damage;
    }

    @Override
    public Optional<SkullProfile> profile() {
        return Optional.ofNullable(profile);
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
    public int maxStack() {
        return maxStack;
    }

    @Override
    public boolean hideTooltip() {
        return hideTooltip;
    }

    @Override
    public boolean fireResistant() {
        return fireResistant;
    }

    @Override
    public boolean enchantGlint() {
        return enchantGlint;
    }

    @Override
    public String rarity() {
        return rarity;
    }

    @Override
    public void markDirty() {
        this.dirty = true;
    }

    @Override
    public Optional<SerialItemData<ItemStack>> data() {
        if(data.isEmpty()) return Optional.empty();
        return Optional.ofNullable(data.entrySet().iterator().next().getValue());
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
        return similarStack((SpongeItemStack) compare);
    }

    public boolean similarStack(SpongeItemStack stack) {

        return SpongeItemPlatform.PLATFORM.check(this, stack);
    }

    @Override
    public ItemStack locale() {
        if(stack == null || dirty) {
            stack = ItemStack.builder().itemType((ItemType) ItemTypes.registry().value(fromString())).quantity(amount).build();

            stack = SpongeItemPlatform.PLATFORM.apply(this, stack);
        }
        return stack;
    }

    private ResourceKey fromString() {
        final String[] split = resource.split(":");

        final String namespace = (split.length >= 2)? split[0] : "minecraft";
        final String value = (split.length >= 2)? split[1] : split[0];
        return ResourceKey.of(namespace, value.toLowerCase());
    }
}
