package de.melanx.yellowsnow.core.registration;

import de.melanx.yellowsnow.entities.YellowSnowball;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "ENTITY_TYPE")
public class ModEntities {

    public static final EntityType<YellowSnowball> yellowSnowball = EntityType.Builder.<YellowSnowball>of(YellowSnowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("yellow_snowball");
}
