package net.tnemc.item.sponge.data;

import net.tnemc.item.data.BannerData;
import net.tnemc.item.data.banner.PatternData;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.meta.BannerPatternLayer;
import org.spongepowered.api.data.type.BannerPatternShape;
import org.spongepowered.api.data.type.BannerPatternShapes;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SpongePatternData extends BannerData<ItemStack> {

    protected boolean applies = false;

    @Override
    public void of(ItemStack stack) {
        final Optional<List<BannerPatternLayer>> patterns = stack.get(Keys.BANNER_PATTERN_LAYERS);
        if(patterns.isPresent()) {
            applies = true;

            for(BannerPatternLayer pattern : patterns.get()) {
                this.patterns.add(new PatternData(pattern.color().key(RegistryTypes.DYE_COLOR).formatted(), pattern.shape().key(RegistryTypes.BANNER_PATTERN_SHAPE).formatted()));
            }
        }
    }

    @Override
    public ItemStack apply(ItemStack stack) {

        if(patterns.size() > 0) {
            final List<BannerPatternLayer> layers = new LinkedList<>();

            for(PatternData data : patterns) {
                layers.add(BannerPatternLayer.of((BannerPatternShape)BannerPatternShapes.registry().value(ResourceKey.resolve(data.getPattern())),
                           (DyeColor)DyeColors.registry().value(ResourceKey.resolve(data.getColor()))));
            }
            stack.offer(Keys.BANNER_PATTERN_LAYERS, layers);
        }
        return stack;
    }

    @Override
    public boolean applies() {
        return applies;
    }
}
