package de.melanx.yellowsnow;

import de.melanx.yellowsnow.core.EventHandler;
import de.melanx.yellowsnow.core.registration.ModEntities;
import de.melanx.yellowsnow.core.registration.ModItems;
import de.melanx.yellowsnow.core.registration.YellowTab;
import de.melanx.yellowsnow.data.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.moddingx.libx.datagen.DatagenSystem;
import org.moddingx.libx.mod.ModXRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(YellowSnow.MODID)
public final class YellowSnow extends ModXRegistration {

    public static final String MODID = "yellowsnow";
    private static YellowSnow instance;
    private final Logger logger = LoggerFactory.getLogger(YellowSnow.class);
    private final YellowTab creativeTab;

    public YellowSnow() {
        instance = this;
        this.creativeTab = new YellowTab(this);

        NeoForge.EVENT_BUS.register(new EventHandler());

        DatagenSystem.create(this, system -> {
            system.addDataProvider(BlockStates::new);
            system.addDataProvider(ItemModels::new);
            system.addDataProvider(RecipesProvider::new);
            system.addDataProvider(TagsProvider::new);

            system.addRegistryProvider(DamageTypesProvider::new);
            system.addRegistryProvider(LootTables::new);
        });
    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        DispenserBlock.registerProjectileBehavior(ModItems.yellowSnowball);
    }

    @Override
    protected void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.yellowSnowball, ThrownItemRenderer::new);
    }

    public static YellowSnow getInstance() {
        return instance;
    }

    public static YellowTab getCreativeTab() {
        return instance.creativeTab;
    }

    public static Logger getLogger() {
        return instance.logger;
    }
}
