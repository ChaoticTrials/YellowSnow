package de.melanx.yellowsnow.data;

import de.melanx.yellowsnow.core.registration.ModBlocks;
import de.melanx.yellowsnow.core.registration.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.tags.CommonTagsProviderBase;

public class TagsProvider extends CommonTagsProviderBase {

    public TagsProvider(DatagenContext ctx) {
        super(ctx);
    }

    public static final TagKey<Item> SNOWBALLS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "snow_balls"));
    public static final TagKey<Block> SLEDGE_SURFACE = BlockTags.create(ResourceLocation.fromNamespaceAndPath("next_christmas", "sledge_surface"));

    @Override
    public void setup() {
        this.item(SNOWBALLS)
                .add(ModItems.yellowSnowball);
        this.block(SLEDGE_SURFACE)
                .add(ModBlocks.yellowSnow)
                .add(ModBlocks.yellowSnowBlock);
        this.block(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.yellowSnow)
                .add(ModBlocks.yellowSnowBlock);
        this.block(BlockTags.SNOW)
                .add(ModBlocks.yellowSnow)
                .add(ModBlocks.yellowSnowBlock);
    }
}
