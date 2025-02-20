package de.melanx.yellowsnow.data;

import de.melanx.yellowsnow.core.registration.ModBlocks;
import de.melanx.yellowsnow.core.registration.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.loot.BlockLootProviderBase;

public class LootTables extends BlockLootProviderBase {

    public LootTables(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    protected void setup() {
        this.drops(ModBlocks.yellowSnowBlock, false, new ItemStack(ModItems.yellowSnowball, 4));
        this.customLootTable(ModBlocks.yellowSnow, block -> LootTable.lootTable().withPool(LootPool.lootPool()
                        .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
                        .add(AlternativesEntry.alternatives(
                                        AlternativesEntry.alternatives(SnowLayerBlock.LAYERS.getPossibleValues(), (amount) -> LootItem.lootTableItem(ModItems.yellowSnowball)
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(SnowLayerBlock.LAYERS, amount)))
                                                .apply(SetItemCountFunction
                                                        .setCount(ConstantValue.exactly(amount)))).when(this.silkCondition().invert()),
                                        AlternativesEntry.alternatives(SnowLayerBlock.LAYERS.getPossibleValues(), (amount) -> amount == 8
                                                ? LootItem.lootTableItem(ModBlocks.yellowSnowBlock)
                                                : LootItem.lootTableItem(ModBlocks.yellowSnow)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) amount)))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(SnowLayerBlock.LAYERS, amount)))
                                        )
                                )
                        )
                )
        );
    }
}
