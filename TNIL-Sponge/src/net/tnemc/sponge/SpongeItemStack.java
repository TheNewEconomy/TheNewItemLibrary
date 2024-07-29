package net.tnemc.sponge;

import net.kyori.adventure.text.Component;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.attribute.SerialAttribute;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.providers.SkullProfile;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.attribute.AttributeModifier;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SpongeItemStack implements AbstractItemStack<ItemStack> {
    private final Map<String, SerialItemData<ItemStack>> data = new HashMap<>();
    private final Map<String, SerialComponent<ItemStack>> components = new HashMap<>();

    private final List<String> flags = new ArrayList<>();
    private final Map<String, AttributeModifier> attributes = new HashMap<>();
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

        this.resource = stack.type().toString();
        this.amount = stack.quantity();

        final Optional<Component> display = stack.get(Keys.CUSTOM_NAME);
        display.ifPresent(component -> this.display = component);

        final Optional<List<Enchantment>> enchants = stack.get(Keys.APPLIED_ENCHANTMENTS);

        if(enchants.isPresent()) {
            for(Enchantment enchant : enchants.get()) {
                enchantments.put(enchant.type().key(RegistryTypes.ENCHANTMENT_TYPE).formatted(), enchant.level());
            }
        }

        final Optional<Color> color = stack.get(Keys.COLOR);
        color.ifPresent(color1 -> this.color = color1.rgb());

        if(profile != null && profile.getUuid() != null) {
            final Optional<ServerPlayer> player = Sponge.server().player(profile.getUuid());
            player.ifPresent(serverPlayer -> stack.offer(Keys.GAME_PROFILE, serverPlayer.profile()));
        }

        // TODO: 1.21 compat sponge
        // Set maxStack to the maximum stack size from meta

        // Set rarity to meta's rarity name

        // Set enchantGlint to meta's enchantment glint override

        // Set fireResistant based on meta's fire-resistant status

        // Set hideTooltip based on meta's hide tooltip status

        // Add food component to components

        // Add tool component to components

        // Add jukebox component to components

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
    public SpongeItemStack lore(List<Component> lore) {
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
    public SpongeItemStack display(Component display) {
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
        this.damage = damage;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> profile(SkullProfile profile) {
        this.profile = profile;
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
    public AbstractItemStack<ItemStack> maxStack(int maxStack) {
        this.maxStack = maxStack;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> hideTooltip(boolean hideTooltip) {
        this.hideTooltip = hideTooltip;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> fireResistant(boolean fireResistant) {
        this.fireResistant = fireResistant;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> enchantGlint(boolean enchantGlint) {
        this.enchantGlint = enchantGlint;
        return this;
    }

    @Override
    public AbstractItemStack<ItemStack> rarity(String rarity) {
        this.rarity = rarity;
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
    public List<Component> lore() {
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
        if(!Component.EQUALS.test(display, stack.display)) return false;
        if(!Objects.equals(damage, stack.damage)) return false;
        if(!Objects.equals(customModelData, stack.customModelData)) return false;
        if(unbreakable != stack.unbreakable) return false;

        //1.21
        if(!Objects.equals(maxStack, stack.maxStack)) return false;
        if(!Objects.equals(rarity, stack.rarity)) return false;
        if(enchantGlint != stack.enchantGlint) return false;
        if(fireResistant != stack.fireResistant) return false;
        if(hideTooltip != stack.hideTooltip) return false;

        if(!textComponentsEqual(lore, stack.lore)) return false;
        if(!listsEquals(flags, stack.flags)) return false;
        if(!attributes.equals(stack.attributes)) return false;
        if(!enchantments.equals(stack.enchantments)) return false;
        if(color != stack.color) return false;
        if(!setsEquals(data.keySet(), stack.data.keySet())) return false;

        for(Map.Entry<String, SerialItemData<ItemStack>> entry : data.entrySet()) {
            final SerialItemData<ItemStack> compare = stack.data.get(entry.getKey());
            if(compare == null || !entry.getValue().similar(compare)) return false;
        }

        if(profile != null) {
            return stack.profile != null && profile.equals(stack.profile);
        }

        if(stack.profile != null) {
            return false;
        }

        return true;
    }

    @Override
    public ItemStack locale() {
        if(stack == null || dirty) {
            stack = ItemStack.builder().itemType((ItemType) ItemTypes.registry().value(fromString())).quantity(amount).build();

            if(display != null && !Component.EQUALS.test(display, Component.empty())) {
                stack.offer(Keys.CUSTOM_NAME, display);
            }

            if(customModelData > 0) {
                stack.offer(Keys.CUSTOM_MODEL_DATA, customModelData);
            }

            if(color != -1) {
                stack.offer(Keys.COLOR, Color.ofRgb(color));
            }

            //Flags
            if(flags.contains("HIDE_ATTRIBUTES")) {
                stack.offer(Keys.HIDE_ATTRIBUTES, true);
            }

            if(flags.contains("HIDE_DESTROYS")) {
                stack.offer(Keys.HIDE_CAN_DESTROY, true);
            }

            if(flags.contains("HIDE_ENCHANTS")) {
                stack.offer(Keys.HIDE_ENCHANTMENTS, true);
            }

            if(flags.contains("HIDE_MISCELLANEOUS")) {
                stack.offer(Keys.HIDE_MISCELLANEOUS, true);
            }

            if(flags.contains("HIDE_UNBREAKABLE")) {
                stack.offer(Keys.HIDE_UNBREAKABLE, true);
            }

            if(flags.contains("HIDE_PLACES")) {
                stack.offer(Keys.HIDE_CAN_PLACE, true);
            }

            //TODO: 1.21 compat

            final List<Enchantment> enchants = new ArrayList<>();
            for(final Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                enchants.add(Enchantment.of((EnchantmentType) EnchantmentTypes.registry().value(ResourceKey.resolve(entry.getKey())), entry.getValue()));
            }
            stack.offer(Keys.APPLIED_ENCHANTMENTS, enchants);

            for(SerialItemData<ItemStack> serialData : data.values()) {
                serialData.apply(stack);
            }

            if(profile != null && profile.getUuid() != null) {
                final Optional<ServerPlayer> player = Sponge.server().player(profile.getUuid());
                player.ifPresent(serverPlayer -> stack.offer(Keys.GAME_PROFILE, serverPlayer.profile()));
            }

            for(SerialComponent<ItemStack> component : components.values()) {
                component.apply(stack);
            }
        }
        return stack;
    }

    private ResourceKey fromString() {
        final String[] split = resource.split("\\:");

        final String namespace = (split.length >= 2)? split[0] : "minecraft";
        final String value = (split.length >= 2)? split[1] : split[0];
        return ResourceKey.of(namespace, value.toLowerCase());
    }
}
