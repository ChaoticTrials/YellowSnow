package de.melanx.yellowsnow.core;

import de.melanx.yellowsnow.ModConfig;
import de.melanx.yellowsnow.core.registration.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.UUID;

public class EventHandler {

    private static final HashMap<UUID, Pair<BlockPos, Integer>> ENTITY_MAP = new HashMap<>();

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity entityLiving)) {
            return;
        }

        if (!ModConfig.forbiddenToPee.test(BuiltInRegistries.ENTITY_TYPE.getKey(entityLiving.getType()))) {
            return;
        }

        Level level = entityLiving.level();

        if (level.isClientSide) {
            return;
        }
        UUID uuid = entityLiving.getUUID();
        BlockPos pos = entityLiving.getOnPos();

        boolean cond1 = level.getBlockState(pos.above()).getBlock() != Blocks.SNOW;
        boolean cond2 = level.getBlockState(pos).getBlock() != Blocks.SNOW_BLOCK;
        if (cond1 && cond2) {
            ENTITY_MAP.remove(uuid);
            return;
        }

        this.handlePeeing(uuid, pos.above(), level, cond2);
    }

    private void handlePeeing(UUID uuid, BlockPos layerPos, Level level, boolean isSnowLayer) {
        if (!ENTITY_MAP.containsKey(uuid)) {
            this.initializeEntityTracking(uuid, layerPos);
            return;
        }

        Pair<BlockPos, Integer> entityData = ENTITY_MAP.get(uuid);
        BlockPos previousPos = entityData.getKey();

        if (!this.isSamePos(previousPos, layerPos)) {
            this.initializeEntityTracking(uuid, layerPos);
            return;
        }

        int peeTimer = entityData.getRight();

        if (peeTimer < ModConfig.peeTickDuration) {
            this.updatePeeTimer(uuid, layerPos, peeTimer);
            return;
        }

        this.makeCitrusSnow(level, layerPos, isSnowLayer);
    }

    private void initializeEntityTracking(UUID uuid, BlockPos pos) {
        ENTITY_MAP.put(uuid, Pair.of(pos, 0));
    }

    private void updatePeeTimer(UUID uuid, BlockPos pos, int currentTimer) {
        ENTITY_MAP.put(uuid, Pair.of(pos, currentTimer + 1));
    }

    private void makeCitrusSnow(Level level, BlockPos layerPos, boolean isSnowLayer) {
        if (isSnowLayer) {
            int snowLayers = level.getBlockState(layerPos).getValue(BlockStateProperties.LAYERS);
            BlockState yellowSnowState = ModBlocks.yellowSnow.defaultBlockState()
                    .setValue(BlockStateProperties.LAYERS, snowLayers);
            level.setBlock(layerPos, yellowSnowState, Block.UPDATE_ALL);
        } else {
            level.setBlock(layerPos.below(), ModBlocks.yellowSnowBlock.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    private boolean isSamePos(BlockPos oldPos, BlockPos currentPos) {
        return oldPos.getX() == currentPos.getX() && oldPos.getY() == currentPos.getY() && oldPos.getZ() == currentPos.getZ();
    }
}
