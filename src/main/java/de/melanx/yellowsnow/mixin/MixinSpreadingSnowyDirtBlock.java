package de.melanx.yellowsnow.mixin;

import de.melanx.yellowsnow.core.registration.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// [VanillaCopy]
@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class MixinSpreadingSnowyDirtBlock {

    @Unique
    private static boolean yellowSnow$isSnowyConditions(LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.above());
        return blockState.is(ModBlocks.yellowSnow) && blockState.getValue(SnowLayerBlock.LAYERS) == 1;
    }

    @Unique
    private static boolean yellowSnow$isSnowyAndNotUnderwater(LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return MixinSpreadingSnowyDirtBlock.yellowSnow$isSnowyConditions(level, pos) && !level.getFluidState(blockpos).is(FluidTags.WATER);
    }

    @Inject(at = @At("HEAD"), method = "randomTick", cancellable = true)
    private void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
        if (MixinSpreadingSnowyDirtBlock.yellowSnow$isSnowyConditions(level, pos)) {
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockstate = ((SpreadingSnowyDirtBlock) (Object) this).defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (level.getBlockState(blockpos).is(Blocks.DIRT) && MixinSpreadingSnowyDirtBlock.yellowSnow$isSnowyAndNotUnderwater(level, blockpos)) {
                        level.setBlock(blockpos, blockstate.setValue(BlockStateProperties.SNOWY, true), Block.UPDATE_ALL);
                    }
                }
                info.cancel();
            }
        }
    }
}
