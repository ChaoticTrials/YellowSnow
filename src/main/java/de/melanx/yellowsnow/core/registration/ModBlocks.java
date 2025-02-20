package de.melanx.yellowsnow.core.registration;

import de.melanx.yellowsnow.YellowSnow;
import de.melanx.yellowsnow.blocks.YellowSnowBlock;
import de.melanx.yellowsnow.blocks.YellowSnowLayerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "BLOCK")
public class ModBlocks {

    public static final Block yellowSnow = new YellowSnowLayerBlock(Block.Properties.ofFullCopy(Blocks.SNOW).randomTicks());
    public static final Block yellowSnowBlock = new YellowSnowBlock(YellowSnow.getInstance(), Block.Properties.ofFullCopy(Blocks.SNOW_BLOCK).randomTicks());
}
