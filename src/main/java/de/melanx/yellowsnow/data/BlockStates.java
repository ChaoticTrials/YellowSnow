package de.melanx.yellowsnow.data;

import de.melanx.yellowsnow.YellowSnow;
import de.melanx.yellowsnow.core.registration.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.model.BlockStateProviderBase;

import java.util.function.Supplier;

public class BlockStates extends BlockStateProviderBase {

    public BlockStates(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    protected void setup() {
        //noinspection ConstantConditions
        this.manualModel(ModBlocks.yellowSnowBlock, this.models().cubeAll(BuiltInRegistries.BLOCK.getKey(ModBlocks.yellowSnowBlock).getPath(), YellowSnow.getInstance().resource("block/yellow_snow")));
    }

    @Override
    protected void defaultState(ResourceLocation id, Block block, Supplier<ModelFile> model) {
        if (block == ModBlocks.yellowSnow) {
            VariantBlockStateBuilder builder = this.getVariantBuilder(block);
            for (int height = 1; height <= 8; height++) {
                if (height == 8) {
                    builder.partialState().with(BlockStateProperties.LAYERS, height).addModels(
                            new ConfiguredModel(this.models().cubeAll(id.getPath() + height, ResourceLocation.fromNamespaceAndPath(YellowSnow.getInstance().modid, "block/yellow_snow")))
                    );
                } else {
                    builder.partialState().with(BlockStateProperties.LAYERS, height).addModels(
                            new ConfiguredModel(this.models()
                                    .withExistingParent(id.getPath() + (height == 1 ? "" : Integer.toString(height)), ResourceLocation.fromNamespaceAndPath("minecraft", "snow_height" + 2 * height))
                                    .texture("texture", ResourceLocation.fromNamespaceAndPath(YellowSnow.getInstance().modid, "block/yellow_snow"))
                                    .texture("particle", ResourceLocation.fromNamespaceAndPath(YellowSnow.getInstance().modid, "block/yellow_snow"))
                            )
                    );
                }
            }
        } else {
            super.defaultState(id, block, model);
        }
    }

    @Override
    protected ModelFile defaultModel(ResourceLocation id, Block block) {
        if (block == ModBlocks.yellowSnow) {
            return null;
        }

        return super.defaultModel(id, block);
    }
}
