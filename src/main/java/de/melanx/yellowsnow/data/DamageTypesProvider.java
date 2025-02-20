package de.melanx.yellowsnow.data;

import de.melanx.yellowsnow.YellowSnow;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.DamageTypeProviderBase;

public class DamageTypesProvider extends DamageTypeProviderBase {

    public static final ResourceKey<DamageType> PEE = ResourceKey.create(Registries.DAMAGE_TYPE, YellowSnow.getInstance().resource("pee"));

    public final Holder<DamageType> pee = this.damageType("pee", 0.0f)
            .deathMessageType(DeathMessageType.INTENTIONAL_GAME_DESIGN)
            .build();

    public DamageTypesProvider(DatagenContext ctx) {
        super(ctx);
    }
}
