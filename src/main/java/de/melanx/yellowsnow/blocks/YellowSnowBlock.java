package de.melanx.yellowsnow.blocks;

import de.melanx.yellowsnow.ModConfig;
import de.melanx.yellowsnow.core.registration.ModBlocks;
import de.melanx.yellowsnow.items.YellowSnowballItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import java.util.List;

public class YellowSnowBlock extends BlockBase {

    public YellowSnowBlock(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (level.getLevelData().isRaining() && level.getBiome(pos).value().getPrecipitationAt(pos) == Biome.Precipitation.SNOW) {
            level.setBlock(pos, Blocks.SNOW_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
        }
        YellowSnowBlock.spreadYellowSnow(level, pos, random);
    }

    public static void spreadYellowSnow(ServerLevel level, BlockPos pos, RandomSource random) {
        if (!ModConfig.spreadable) return;
        BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW_BLOCK)) {
            level.setBlock(blockpos, ModBlocks.yellowSnowBlock.defaultBlockState(), Block.UPDATE_ALL);
        } else if (blockstate.is(Blocks.SNOW)) {
            int layers = blockstate.getValue(BlockStateProperties.LAYERS);
            level.setBlock(blockpos, ModBlocks.yellowSnow.defaultBlockState().setValue(BlockStateProperties.LAYERS, layers), Block.UPDATE_ALL);
        }
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltips, @Nonnull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltips, tooltipFlag);
        tooltips.add(YellowSnowballItem.DONT_EAT.withStyle(ChatFormatting.DARK_RED));
    }
}
